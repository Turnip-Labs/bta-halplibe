package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.event.Event;
import turniplabs.halplibe.event.IndirectEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class CustomEvent<L, E> implements IndirectEmitter<E>, Event<L> {
    protected final @NonNull E emitter;
    protected final @NonNull List<L> listeners;

    public CustomEvent(@NonNull final Function<List<L>, E> emitter) {
        this.listeners = new ArrayList<>();
        this.emitter = emitter.apply(this.listeners);
    }

    @Override
    public @NonNull E getEmitter() {
        return emitter;
    }

    @Override
    public void listen(@NonNull final L listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(@NonNull final L listener) {
        listeners.remove(listener);
    }
}
