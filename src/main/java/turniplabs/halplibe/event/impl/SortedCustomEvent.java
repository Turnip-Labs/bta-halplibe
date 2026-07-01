package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.event.EventSorted;
import turniplabs.halplibe.event.IndirectEmitter;
import turniplabs.halplibe.event.ModListeners;
import turniplabs.halplibe.event.utils.EventUtils;
import turniplabs.halplibe.util.dependency.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unused")
public class SortedCustomEvent<L, E> implements EventSorted<L>, IndirectEmitter<E> {
    protected final @NonNull E emitter;
    protected final @NonNull List<L> listeners = new ArrayList<>();
    protected final @NonNull Map<String, ModListeners<L>> listenerMap = new HashMap<>();
    protected boolean isSorted;

    public SortedCustomEvent(@NonNull final Function<List<L>, E> emitter) {
        this.emitter = emitter.apply(this.listeners);
    }

    @Override
    public @NonNull E getEmitter() {
        if (!isSorted) {
            EventUtils.sortBFS(listeners, listenerMap, listenerMap.get("bta"));
            isSorted = true;
        }
        return emitter;
    }

    @Override
    public void listen(@NonNull final Key key, @NonNull final L listener) {
        final ModListeners<L> ml = listenerMap.computeIfAbsent(key.dependsOn(),  k -> {
            isSorted = false;
            return new ModListeners<>(k);
        });

        ml.addListener(key.modId(), listener);
    }

    @Override
    public void remove(@NonNull final L listener) {
        listeners.remove(listener);
    }
}
