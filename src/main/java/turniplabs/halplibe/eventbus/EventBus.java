package turniplabs.halplibe.eventbus;

import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface EventBus {
    void post(@NonNull Signal event);
    void post(@NonNull Supplier<Signal> eventSupplier);

    void postNoPropagate(@NonNull Signal event);
    void postNoPropagate(@NonNull Supplier<Signal> eventSupplier);

    <T extends Signal> void postMapped(@NonNull Mapping<T> mapping, @NonNull T event);
    <T extends Signal> void postMapped(@NonNull Mapping<T> mapping, @NonNull Supplier<T> eventSupplier);

    void registerStaticListeners(@NonNull Class<?> cls);
    void registerInstanceListeners(@NonNull Object instance);
    <T> void registerListeners(@NonNull Class<T> cls, @NonNull T instance);

    void removeStaticListeners(@NonNull Class<?> cls);
    void removeInstanceListeners(@NonNull Object instance);
    <T> void removeListeners(@NonNull Class<T> cls, @NonNull T instance);

    <T extends Signal> @NonNull Mapping<T> getMapping(@NonNull Class<T> signalClass);

    record Mapping<T extends Signal>(int id) {}
}
