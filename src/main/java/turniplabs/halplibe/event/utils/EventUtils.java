package turniplabs.halplibe.event.utils;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import turniplabs.halplibe.event.ModListeners;

import java.util.*;

@NullMarked
public final class EventUtils {

    /**
     * Executes a breadth-first search on a map of listeners starting from {@code root}, dropping any unreachable nodes.
     * The provided {@code listeners} list is cleared before being repopulated with the listeners
     * present in {@code listenerMap}. The elements in the list will be ordered based on their dependencies.
     * <br><br>
     * Dev Note: Circular dependencies are currently impossible, if multiple dependencies are ever allowed
     * this method will have to be replaced.
     */
    public static <T> void sortBFS(final List<T> listeners, final Map<String, ModListeners<T>> listenerMap, @Nullable final ModListeners<T> root) {
        if (listenerMap.isEmpty() || root == null) return;
        listeners.clear();

        final Set<String> closed = new HashSet<>();
        final Deque<String> modIdQueue = new ArrayDeque<>();
        modIdQueue.add(root.modId);

        while (!modIdQueue.isEmpty()) {
            final String nextId = modIdQueue.poll();

            final ModListeners<T> ml = listenerMap.get(nextId);
            if (ml == null || !closed.add(ml.modId)) continue;

            ml.listeners.forEach(l -> {
                listeners.add(l.listener());
                modIdQueue.add(l.modId());
            });
        }
    }

    private EventUtils() {}
}
