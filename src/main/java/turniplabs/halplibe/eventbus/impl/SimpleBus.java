package turniplabs.halplibe.eventbus.impl;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.eventbus.BusListener;
import turniplabs.halplibe.eventbus.EventBus;
import turniplabs.halplibe.eventbus.Signal;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public final class SimpleBus implements EventBus {
    private final Map<Class<?>, List<ListenerData>> listenerMap = new HashMap<>();
    private final Logger logger;

    public SimpleBus(@NonNull final String name) {
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public void post(@NonNull final Signal event) {
        Class<?> currentClass = event.getClass();
        while (Signal.class.isAssignableFrom(currentClass)) {
            final List<ListenerData> consumerList = listenerMap.get(currentClass);

            final boolean cancelled = forEachUntil(consumerList, s -> s.accept(event), s -> event.isCancelled());
            if (cancelled) return;

            currentClass = currentClass.getSuperclass();
        }

        final List<ListenerData> consumerList = listenerMap.get(Signal.class);
        if (consumerList != null) consumerList.forEach(consumer -> consumer.accept(event));
    }

    @Override
    public void post(@NonNull final Supplier<Signal> eventSupplier) {
        final Signal event = eventSupplier.get();

        Class<?> currentClass = event.getClass();
        while (Signal.class.isAssignableFrom(currentClass)) {
            final List<ListenerData> consumerList = listenerMap.get(currentClass);

            final boolean cancelled = forEachUntil(consumerList, s -> s.accept(event), s -> event.isCancelled());
            if (cancelled) return;

            currentClass = currentClass.getSuperclass();
        }

        final List<ListenerData> consumerList = listenerMap.get(Signal.class);
        if (consumerList != null) consumerList.forEach(consumer -> consumer.accept(event));
    }

    @Override
    public void postNoPropagate(@NonNull Signal event) {
        final List<ListenerData> consumerList = listenerMap.get(event.getClass());
        forEachUntil(consumerList, s -> s.accept(event), s -> event.isCancelled());
    }

    @Override
    public void postNoPropagate(@NonNull Supplier<Signal> eventSupplier) {
        final Signal event = eventSupplier.get();
        final List<ListenerData> consumerList = listenerMap.get(event.getClass());
        forEachUntil(consumerList, s -> s.accept(event), s -> event.isCancelled());
    }

    private static <T> boolean forEachUntil(
            @Nullable final List<T> list,
            @NonNull final Consumer<T> consumer,
            @NonNull final Predicate<T> exitCondition
    ) {
        if (list == null) return false;

        final int size = list.size();
        for (int i = 0; i < size; ++i) {
            final T element = list.get(i);
            consumer.accept(element);
            if (exitCondition.test(element)) return true;
        }

        return false;
    }

    @Override
    public void registerStaticListeners(@NonNull final Class<?> cls) {
        registerListenersInternal(cls, null, false);
    }

    @Override
    public void registerInstanceListeners(@NonNull final Object instance) {
        registerListenersInternal(instance.getClass(), instance, false);
    }

    @Override
    public <T> void registerListeners(@NonNull final Class<T> cls, @NonNull final T instance) {
        registerListenersInternal(cls, instance, true);
    }

    @Override
    public void removeStaticListeners(@NonNull final Class<?> cls) {
        removeListenersInternal(cls, null, false);

    }

    @Override
    public void removeInstanceListeners(@NonNull Object instance) {
        removeListenersInternal(instance.getClass(), instance, false);
    }

    @Override
    public <T> void removeListeners(@NonNull Class<T> cls, @NonNull T instance) {
        removeListenersInternal(cls, instance, true);
    }

    @SuppressWarnings("unchecked")
    private void registerListenersInternal(@NonNull final Class<?> cls, @Nullable final Object instance, boolean registerBoth) {
        for (final Method method : cls.getMethods()) {
            try {
                BusListener listener = method.getAnnotation(BusListener.class);
                if (listener == null) continue;

                final boolean isStatic = Modifier.isStatic(method.getModifiers());
                if (!registerBoth && ((isStatic && instance != null) || (!isStatic && instance == null))) continue;

                final MethodHandles.Lookup lu = MethodHandles.lookup();
                final MethodHandle handle = lu.unreflect(method);

                final MethodType handleType = isStatic ? handle.type() : handle.type().dropParameterTypes(0, 1);
                if (handleType.parameterCount() != 1 || !Signal.class.isAssignableFrom(handleType.parameterType(0))) {
                    logger.error("Incorrect BusListener {} method signature '{}' in {}\n\t- Method: {}", isStatic ? "static" : "instance", handleType, cls, method);
                    continue;
                }

                final MethodType templateType = MethodType.methodType(void.class, Object.class);

                final Class<?> declaring = Consumer.class;
                final MethodType factoryType = isStatic ? MethodType.methodType(declaring) : MethodType.methodType(declaring, method.getDeclaringClass());

                final CallSite cs = LambdaMetafactory.metafactory(lu, "accept", factoryType, templateType, handle, handleType);
                final Object lambda = isStatic ? cs.dynamicInvoker().invoke() : cs.dynamicInvoker().invoke(instance);

                final Class<?> paramType = handleType.parameterType(0);
                final List<ListenerData> consumerList = listenerMap.computeIfAbsent(paramType, key -> new ArrayList<>());
                consumerList.add(new ListenerData((Consumer<Signal>) lambda, cls, isStatic ? null : instance));

            } catch (Throwable e) {
                logger.error("Failed to register listener:\n{}", e.getMessage());
            }
        }
    }

    private void removeListenersInternal(@NonNull final Class<?> cls, @Nullable final Object instance, boolean removeBoth) {
        final ListenerData dataKey = new ListenerData(null, cls, instance);

        for (final Method method : cls.getMethods()) {
            try {
                BusListener listener = method.getAnnotation(BusListener.class);
                if (listener == null) continue;

                final boolean isStatic = Modifier.isStatic(method.getModifiers());
                if (!removeBoth && ((isStatic && instance != null) || (!isStatic && instance == null))) continue;

                final MethodHandles.Lookup lu = MethodHandles.lookup();
                final MethodHandle handle = lu.unreflect(method);

                final MethodType handleType = isStatic ? handle.type() : handle.type().dropParameterTypes(0, 1);
                if (handleType.parameterCount() != 1 || !Signal.class.isAssignableFrom(handleType.parameterType(0))) {
                    continue;
                }

                final List<ListenerData> listenerData = listenerMap.get(handleType.parameterType(0));
                final int size = listenerData.size();
                for (int i = size - 1; i >= 0; --i) {
                    final ListenerData data = listenerData.get(i);

                    if (dataKey.equals(data) || dataKey.isSameStatic(data)) {
                        final int lastIdx = listenerData.size() - 1;
                        final ListenerData last = listenerData.get(lastIdx);
                        listenerData.set(i, last);
                        listenerData.remove(lastIdx);
                    }
                }

            } catch (IllegalAccessException ignored) {}
        }
    }

    public record ListenerData (Consumer<Signal> consumer, Class<?> cls, Object instance) {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof ListenerData data && data.cls == this.cls && data.instance == this.instance;
        }

        public boolean isSameStatic(ListenerData other) {
            return instance == null && other.instance == null && this.cls == other.cls;
        }

        public void accept(final Signal signal) {
            consumer.accept(signal);
        }
    }
}
