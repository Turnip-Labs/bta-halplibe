package turniplabs.halplibe.eventbus.impl;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.eventbus.BusListener;
import turniplabs.halplibe.eventbus.EventBus;
import turniplabs.halplibe.eventbus.Signal;
import turniplabs.halplibe.util.meta.MetaFactoryException;
import turniplabs.halplibe.util.meta.MetaUtils;
import turniplabs.halplibe.util.meta.MethodTypeMismatch;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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
public sealed class SimpleBus implements EventBus permits SynchronizedSimpleBus {
    private final Logger logger;
    private int mappingId = 0;
    private final Map<Class<? extends Signal>, Mapping<? extends Signal>> mappings = new HashMap<>();
    private final List<@NonNull List<ListenerData>> mappedListeners = new ArrayList<>();

    public SimpleBus(@NonNull final String name) {
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public void post(@NonNull final Signal event) {
        final Class<?> directClass = event.getClass();

        // Checked at post because this shouldn't be changed by listeners (unlike CANCELLED)
        final boolean isFinal = event.getState() == Signal.State.FINAL;

        forEachSuperUntil(event.getClass(), Signal.class, cls -> {
            final Mapping<Signal> mapping = getMapping(cls);
            final List<ListenerData> listeners = mappedListeners.get(mapping.id());

            if (cls == directClass && isFinal) {
                listeners.forEach(l -> l.accept(event));
                mappedListeners.set(mapping.id(), new ArrayList<>());
                return false;
            }

            return forEachUntil(listeners, l -> l.accept(event), l -> event.getState() == Signal.State.CANCELLED);
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

        if (event.getState() == Signal.State.FINAL) {
            listeners.forEach(l -> l.accept(event));
            mappedListeners.set(mapping.id(), new ArrayList<>());
        }else {
            forEachUntil(listeners, l -> l.accept(event), l -> event.getState() == Signal.State.CANCELLED);
        }
    }

    @Override
    public <T extends Signal> void postNoPropagate(@NonNull Mapping<T> mapping, @NonNull Supplier<T> eventSupplier) {
        postNoPropagate(mapping, eventSupplier.get());
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
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
    public void removeInstanceListeners(@NonNull final Object instance) {
        removeListenersInternal(instance.getClass(), instance, false);
    }

    @Override
    public <T> void removeListeners(@NonNull final Class<T> cls, @NonNull final T instance) {
        removeListenersInternal(cls, instance, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Signal> @NonNull Mapping<T> getMapping(@NonNull final Class<T> signalClass) {
        final Mapping<T> mapping = (Mapping<T>) mappings.get(signalClass);
        if (mapping != null) return mapping;

        final Mapping<T> newMapping = new Mapping<>(mappingId++);
        mappings.put(signalClass, newMapping);
        mappedListeners.add(new ArrayList<>());

        return newMapping;
    }

    @SuppressWarnings("unchecked")
    private void registerListenersInternal(@NonNull final Class<?> cls, @Nullable final Object instance, final boolean registerBoth) {
        final MethodType lambdaType = MethodType.methodType(void.class, Signal.class);
        final MethodHandles.Lookup lu = MethodHandles.lookup();

        for (final Method method : cls.getMethods()) {
            try {
                BusListener listener = method.getAnnotation(BusListener.class);
                if (listener == null) continue;
                if (method.getParameterCount() != 1) {
                    logger.error("Failed to register '{}' as listener in '{}': Incorrect parameter count ({}), expected 1", method, cls, method.getParameterCount());
                    continue;
                }

                final boolean isStatic = Modifier.isStatic(method.getModifiers());
                final Class<?> signalType = method.getParameterTypes()[0];

                final Consumer<Signal> lambda = MetaUtils.createLambda(
                        lu, method, instance, Consumer.class, "accept", lambdaType
                );
                if (lambda == null) {
                    logger.error("Failed to register '{}' as listener in '{}': Unknown error", method, cls);
                    continue;
                }

                final Class<? extends Signal> paramType = (Class<? extends Signal>) signalType;
                forEachSuper(paramType, Signal.class, this::getMapping);

                final Mapping<?> mapping = mappings.get(paramType);
                final List<ListenerData> listeners = mappedListeners.get(mapping.id());
                listeners.add(new ListenerData(lambda, cls, isStatic ? null : instance));
            } catch (MethodTypeMismatch | MetaFactoryException e) {
                logger.error("Failed to register '{}' as listener in '{}': {}", method, cls, e.getMessage());
            }
        }
    }

    private void removeListenersInternal(@NonNull final Class<?> cls, @Nullable final Object instance, final boolean removeBoth) {
        final ListenerData dataKey = new ListenerData(null, cls, instance);
        final MethodType lambdaType = MethodType.methodType(void.class, Signal.class);

        for (final Method method : cls.getMethods()) {
            BusListener listener = method.getAnnotation(BusListener.class);
            if (listener == null || method.getParameterCount() != 1) continue;

            final boolean isStatic = Modifier.isStatic(method.getModifiers());
            if (!removeBoth && ((isStatic && instance != null) || (!isStatic && instance == null))) continue;

            final MethodType type = MetaUtils.getMethodType(method);
            if(!MetaUtils.isTypeAssignableFrom(lambdaType, type)) continue;

            final Mapping<?> mapping = mappings.get(type.parameterType(0));
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
        }
    }

    private record ListenerData (Consumer<Signal> consumer, Class<?> cls, Object instance) {
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
