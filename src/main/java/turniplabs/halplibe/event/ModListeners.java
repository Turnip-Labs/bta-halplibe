package turniplabs.halplibe.event;

import java.util.ArrayList;
import java.util.List;

public final class ModListeners<T> {
    public final String modId;
    public final List<Listener<T>> listeners;

    public ModListeners(final String modId) {
        this.modId = modId;
        this.listeners = new ArrayList<>();
    }

    public void addListener(final String modId, final T listener) {
        listeners.add(new Listener<>(modId, listener));
    }

    public record Listener<T>(String modId, T listener) {}
}
