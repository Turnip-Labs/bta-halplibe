package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
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
public class SortedBaseEvent<L> implements EventSorted<L>, Emitter<L> {
    protected final @NonNull List<L> listeners = new ArrayList<>();
    protected @NonNull Map<String, ModListeners<L>> listenerMap = new HashMap<>();
    protected boolean isSorted;

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {

        if (!isSorted) {
            EventUtils.sortBFS(listeners, listenerMap, listenerMap.get("bta"));
            isSorted = true;
        }

        listeners.forEach(consumer);
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
