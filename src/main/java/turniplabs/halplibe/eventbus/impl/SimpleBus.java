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
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public final class SimpleBus implements EventBus {
    private final Logger logger;
    private int mappingId = 0;
    private final Map<Class<? extends Signal>, Mapping<? extends Signal>> mappings = new HashMap<>();
    private final List<@NonNull List<ListenerData>> mappedListeners = new ArrayList<>();

    public SimpleBus(@NonNull final String name) {
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public void post(@NonNull final Signal event) {
        forEachSuperUntil(event.getClass(), Signal.class, cls -> {
            final Mapping<Signal> mapping = getMapping(cls);
            final List<ListenerData> listeners = mappedListeners.get(mapping.id());

            return forEachUntil(listeners, l -> l.accept(event), l -> event.isCancelled());
        });
    }

    @Override
    public void post(@NonNull final Supplier<Signal> eventSupplier) {
        post(eventSupplier.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void postNoPropagate(@NonNull Signal event) {
        final Mapping<Signal> mapping = (Mapping<Signal>) getMapping(event.getClass());
        postNoPropagate(mapping, event);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void postNoPropagate(@NonNull Supplier<Signal> eventSupplier) {
        final Signal event = eventSupplier.get();
        final Mapping<Signal> mapping = (Mapping<Signal>) getMapping(event.getClass());
        postNoPropagate(mapping, event);
    }

    @Override
    public <T extends Signal> void postNoPropagate(@NonNull Mapping<T> mapping, @NonNull T event) {
        final List<ListenerData> listeners = mappedListeners.get(mapping.id());
        forEachUntil(listeners, l -> l.accept(event), l -> event.isCancelled());
    }

    @Override
    public <T extends Signal> void postNoPropagate(@NonNull Mapping<T> mapping, @NonNull Supplier<T> eventSupplier) {
        postNoPropagate(mapping, eventSupplier.get());
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

    @SuppressWarnings("unchecked")
    private static <C extends S, S> void forEachSuper(
            @NonNull final Class<C> start,
            @NonNull final Class<S> end,
            @NonNull final Consumer<Class<S>> consumer
    ) {
        Class<S> currentClass = (Class<S>) start;

        while (currentClass != null && end.isAssignableFrom(currentClass)) {
            consumer.accept(currentClass);
            currentClass = (Class<S>) currentClass.getSuperclass();
        }

        if (end.isInterface()) consumer.accept(end);
    }

    @SuppressWarnings("unchecked")
    private static <C extends S, S> void forEachSuperUntil(
            @NonNull final Class<C> start,
            @NonNull final Class<S> end,
            @NonNull final Predicate<Class<S>> exitCondition
    ) {
        Class<S> currentClass = (Class<S>) start;

        while (end.isAssignableFrom(currentClass)) {
            if (exitCondition.test(currentClass)) return;
            currentClass = (Class<S>) currentClass.getSuperclass();
        }

        if (end.isInterface()) exitCondition.test(end);
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

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Signal> @NonNull Mapping<T> getMapping(@NonNull Class<T> signalClass) {
        final Mapping<T> mapping = (Mapping<T>) mappings.get(signalClass);
        if (mapping != null) return mapping;

        final Mapping<T> newMapping = new Mapping<>(mappingId++);
        mappings.put(signalClass, newMapping);
        mappedListeners.add(new ArrayList<>());

        return newMapping;
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

                final Class<? extends Signal> paramType = (Class<? extends Signal>) handleType.parameterType(0);

                forEachSuper(paramType, Signal.class, this::getMapping);

                final Mapping<?> mapping = mappings.get(paramType);
                final List<ListenerData> listeners = mappedListeners.get(mapping.id());
                listeners.add(new ListenerData((Consumer<Signal>) lambda, cls, isStatic ? null : instance));

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

                final Mapping<?> mapping = mappings.get(handleType.parameterType(0));
                final List<ListenerData> listenerData = mappedListeners.get(mapping.id());
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
