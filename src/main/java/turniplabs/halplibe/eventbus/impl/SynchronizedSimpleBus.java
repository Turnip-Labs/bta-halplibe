package turniplabs.halplibe.eventbus.impl;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.eventbus.Signal;

import java.util.function.Supplier;

public class SynchronizedSimpleBus extends SimpleBus {
    public SynchronizedSimpleBus(@NonNull String name) {
        super(name);
    }

    @Override
    public synchronized void post(@NonNull Signal event) {
        super.post(event);
    }

    @Override
    public synchronized void post(@NonNull Supplier<Signal> eventSupplier) {
        super.post(eventSupplier);
    }

    @Override
    public synchronized void postNoPropagate(@NonNull Signal event) {
        super.postNoPropagate(event);
    }

    @Override
    public synchronized void postNoPropagate(@NonNull Supplier<Signal> eventSupplier) {
        super.postNoPropagate(eventSupplier);
    }

    @Override
    public synchronized <T extends Signal> void postNoPropagate(@NonNull Mapping<T> mapping, @NonNull T event) {
        super.postNoPropagate(mapping, event);
    }

    @Override
    public synchronized <T extends Signal> void postNoPropagate(@NonNull Mapping<T> mapping, @NonNull Supplier<T> eventSupplier) {
        super.postNoPropagate(mapping, eventSupplier);
    }

    @Override
    public synchronized void registerStaticListeners(@NonNull Class<?> cls) {
        super.registerStaticListeners(cls);
    }

    @Override
    public synchronized void registerInstanceListeners(@NonNull Object instance) {
        super.registerInstanceListeners(instance);
    }

    @Override
    public synchronized <T> void registerListeners(@NonNull Class<T> cls, @NonNull T instance) {
        super.registerListeners(cls, instance);
    }

    @Override
    public synchronized void removeStaticListeners(@NonNull Class<?> cls) {
        super.removeStaticListeners(cls);
    }

    @Override
    public synchronized void removeInstanceListeners(@NonNull Object instance) {
        super.removeInstanceListeners(instance);
    }

    @Override
    public synchronized <T> void removeListeners(@NonNull Class<T> cls, @NonNull T instance) {
        super.removeListeners(cls, instance);
    }

    @Override
    public synchronized @NonNull <T extends Signal> Mapping<T> getMapping(@NonNull Class<T> signalClass) {
        return super.getMapping(signalClass);
    }
}
