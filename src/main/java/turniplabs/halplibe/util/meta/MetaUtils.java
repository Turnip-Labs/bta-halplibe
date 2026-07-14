package turniplabs.halplibe.util.meta;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class MetaUtils {

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T createLambda(
            final MethodHandles.@NonNull Lookup lu,
            @NonNull final Method instanceMethod,
            @Nullable final Object instance,
            @NonNull final Class<?> funcInterface,
            @NonNull final String lambdaName,
            @NonNull final MethodType coercedType
    ) throws MethodTypeMismatch, MetaFactoryException {
        final Method data = getFunctionalInterfaceMethod(funcInterface);
        if (data == null) return null;

        final MethodHandle instanceHandle = unreflect(lu, instanceMethod);
        if (instanceHandle == null) return null;

        final MethodType interfaceType = MethodType.methodType(data.getReturnType(), data.getParameterTypes());
        final MethodType instanceType = MethodType.methodType(instanceMethod.getReturnType(), instanceMethod.getParameterTypes());
        if (!isTypeAssignableFrom(coercedType, instanceType)) {
            throw new MethodTypeMismatch("Type " + coercedType + " is not assignable from " + instanceType);
        };

        final boolean isStatic = Modifier.isStatic(instanceMethod.getModifiers());
        if (!isStatic && instance == null) throw new NullPointerException("Received null instance for non-static method");
        final MethodType factoryType = isStatic ? MethodType.methodType(funcInterface) : MethodType.methodType(funcInterface, instance.getClass());

        try {
            final CallSite cs = LambdaMetafactory.metafactory(lu, lambdaName, factoryType, interfaceType, instanceHandle, instanceType);
            final MethodHandle invoker = cs.dynamicInvoker();
            return (T) (isStatic ? invoker.invoke() : invoker.invoke(instance));
        } catch (Throwable e) {
            throw new MetaFactoryException(e);
        }
    }

    public static @Nullable Method getFunctionalInterfaceMethod(@NonNull final Class<?> cls) {
        if (!cls.isInterface()) return null;

        int count = 0;
        Method candidate = null;

        for (final Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isAbstract(method.getModifiers())) continue;

            ++count;
            candidate = method;
        }
        if (count != 1) return null;

        return candidate;
    }

    public static boolean isTypeAssignableFrom(@NonNull final MethodType type, @NonNull final MethodType from) {
        if (
                type.parameterCount() != from.parameterCount()
                        || !type.returnType().isAssignableFrom(from.returnType())
        ) return false;

        final Class<?>[] typeParams = type.parameterArray();
        final Class<?>[] fromParams = from.parameterArray();

        for (int i = 0; i < fromParams.length; ++i) {
            if (!typeParams[i].isAssignableFrom(fromParams[i])) return false;
        }

        return true;
    }

    public static @Nullable MethodHandle unreflect(final MethodHandles.@NonNull Lookup lu, @NonNull final Method method) {
        try {
            return lu.unreflect(method);
        } catch (IllegalAccessException ignored) {}

        return null;
    }

    public static @NonNull MethodType getMethodType(@NonNull final Method method) {
        return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    }

    private MetaUtils() {};
}
