package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.Emitter;
import turniplabs.halplibe.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SingleEvent<L> implements Event<L>, Emitter<L> {
    protected final String name;
    protected @Nullable List<L> listeners;

    public SingleEvent(@NonNull final String name) {
        this.name = name;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {
        if (listeners == null) {
            HalpLibe.LOGGER.warn("Attempted to call '{}' SingleEvent, but it was already consumed.", name);
            return;
        }

        listeners.forEach(consumer);
        listeners = null;
    }

    @Override
    public void listen(@NonNull final L listener) {
        if (listeners == null) {
            HalpLibe.LOGGER.warn("Attempted to add listener to '{}' SingleEvent too late.", name);
            return;
        }

        listeners.add(listener);
    }
}
