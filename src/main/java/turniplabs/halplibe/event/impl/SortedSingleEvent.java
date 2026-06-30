package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.Emitter;
import turniplabs.halplibe.event.EventSorted;
import turniplabs.halplibe.event.ModListeners;
import turniplabs.halplibe.event.utils.EventUtils;
import turniplabs.halplibe.util.dependency.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SortedSingleEvent<L> implements EventSorted<L>, Emitter<L> {
    protected final String name;
    protected @Nullable List<ModListeners<L>> listeners;

    public SortedSingleEvent(@NonNull final String name) {
        this.name = name;
        this.listeners = new ArrayList<>();
        this.listeners.add(new ModListeners<>("bta"));
    }

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {
        if (listeners == null) {
            HalpLibe.LOGGER.warn("Attempted to call '{}' SingleEvent multiple times.", name);
            return;
        }

        // The first element should always be BTA
        EventUtils.sortBFS(listeners, listeners.get(0));

        listeners.forEach(ml -> ml.listeners.forEach(l -> consumer.accept(l.listener())));
        listeners = null;
    }

    @Override
    public void listen(@NonNull final Key key, @NonNull final L listener) {
        if (listeners == null) {
            HalpLibe.LOGGER.warn("Attempted to add listener to '{}' SingleEvent too late.", name);
            return;
        }

        final ModListeners<L> ml = EventUtils.getListenersOf(listeners, key.dependsOn());
        ml.addListener(key.modId(), listener);
    }

    @Override
    public void remove(@NonNull final L listener) {
        if (listeners == null) return;

        for (int i = 0; i < listeners.size(); ++i) {
            final ModListeners<L> ml = listeners.get(i);
            if (ml.listeners.removeIf(l -> l.listener() == listener)) return;
        }
    }
}
