package turniplabs.halplibe.event;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.util.dependency.Key;

@SuppressWarnings("unused")
public interface EventSorted<LISTENER> {
    void listen(@NonNull Key key, @NonNull LISTENER listener);
    void remove(@NonNull LISTENER listener);
}
