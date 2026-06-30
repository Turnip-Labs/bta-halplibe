package turniplabs.halplibe.event.impl;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.event.Emitter;
import turniplabs.halplibe.event.EventSorted;
import turniplabs.halplibe.event.ModListeners;
import turniplabs.halplibe.event.utils.EventUtils;
import turniplabs.halplibe.util.dependency.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SortedBaseEvent<L> implements EventSorted<L>, Emitter<L> {
    protected final @NonNull List<ModListeners<L>> listeners = new ArrayList<>();
    protected boolean isSorted;

    public SortedBaseEvent() {
        this.listeners.add(new ModListeners<>("bta"));
    }

    @Override
    public void emit(@NonNull final Consumer<L> consumer) {

        if (!isSorted) {
            // The first element should always be BTA
            EventUtils.sortBFS(listeners, listeners.get(0));
            isSorted = true;
        }

        listeners.forEach(ml -> ml.listeners.forEach(l -> consumer.accept(l.listener())));
    }

    @Override
    public void listen(@NonNull final Key key, @NonNull final L listener) {
        final int prevSize = listeners.size();
        final ModListeners<L> ml = EventUtils.getListenersOf(listeners, key.dependsOn());
        if (prevSize != listeners.size()) isSorted = false;

        ml.addListener(key.modId(), listener);
    }

    @Override
    public void remove(@NonNull final L listener) {
        for (int i = 0; i < listeners.size(); ++i) {
            final ModListeners<L> ml = listeners.get(i);
            if (ml.listeners.removeIf(l -> l.listener() == listener)) return;
        }
    }
}
