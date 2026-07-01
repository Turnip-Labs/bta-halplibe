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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SortedSingleEvent<L> implements EventSorted<L>, Emitter<L> {
    protected final String name;
    protected @Nullable List<L> listeners = new ArrayList<>();
    protected @Nullable Map<String, ModListeners<L>> listenerMap = new HashMap<>();

    public SortedSingleEvent(@NonNull final String name) {
        this.name = name;
    }

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {
        if (listeners == null || listenerMap == null) {
            HalpLibe.LOGGER.warn("Attempted to call '{}' SingleEvent multiple times.", name);
            return;
        }

        EventUtils.sortBFS(listeners, listenerMap, listenerMap.get("bta"));

        listeners.forEach(consumer);
        listeners = null;
        listenerMap = null;
    }

    @Override
    public void listen(@NonNull final Key key, @NonNull final L listener) {
        if (listeners == null || listenerMap == null) {
            HalpLibe.LOGGER.warn("Attempted to add listener to '{}' SingleEvent too late.", name);
            return;
        }

        final ModListeners<L> ml = listenerMap.computeIfAbsent(key.dependsOn(), ModListeners::new);
        ml.addListener(key.modId(), listener);
    }

    @Override
    public void remove(@NonNull final L listener) {
        if (listeners == null) return;

        listeners.remove(listener);
    }
}
