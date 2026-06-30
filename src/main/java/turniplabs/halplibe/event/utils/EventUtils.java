package turniplabs.halplibe.event.utils;

import org.jspecify.annotations.NullMarked;
import turniplabs.halplibe.event.ModListeners;
import turniplabs.halplibe.util.collections.CollectionUtils;

import java.util.*;

@NullMarked
public final class EventUtils {

    /**
     * Executes a breadth-first search on a list of listeners, dropping any unreachable nodes.
     * The provided list is assumed to contain no duplicate ModListener entries.
     * <br><br>
     * Dev Note: Circular dependencies are currently impossible, if multiple dependencies are ever allowed
     * this method will have to be replaced.
     */
    public static <T> void sortBFS(final List<ModListeners<T>> listeners, final ModListeners<T> root) {
        if (listeners.isEmpty()) return;

        final Map<String, ModListeners<T>> map = CollectionUtils.mapCollection(listeners, new HashMap<>(), l -> l.modId);
        listeners.clear();

        final Deque<String> modIdQueue = new ArrayDeque<>();
        modIdQueue.add(root.modId);

        while (!modIdQueue.isEmpty()) {
            final String nextId = modIdQueue.poll();

            final ModListeners<T> ml = map.remove(nextId);
            if (ml == null) continue;

            listeners.add(ml);
            ml.listeners.forEach(l -> modIdQueue.add(l.modId()));
        }
    }

    /**
     * Finds the first listener array matching modId, or adds a new listener array to the list and returns it.
     */
    public static <T> ModListeners<T> getListenersOf(final List<ModListeners<T>> listeners, final String modId) {
        for (int i = 0; i < listeners.size(); ++i) {
            final ModListeners<T> ml = listeners.get(i);
            if (ml.modId.equals(modId)) return ml;
        }

        final ModListeners<T> newListeners = new ModListeners<>(modId);
        listeners.add(newListeners);

        return newListeners;
    }

    private EventUtils() {}
}
