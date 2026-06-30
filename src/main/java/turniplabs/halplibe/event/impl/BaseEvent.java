package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.event.Emitter;
import turniplabs.halplibe.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class BaseEvent<L> implements Event<L>, Emitter<L> {
    protected final @NonNull List<L> listeners = new ArrayList<>();

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {
        listeners.forEach(consumer);
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
