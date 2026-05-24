package turniplabs.halplibe.event;

import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public interface Event<LISTENER> {
    void listen(@NonNull LISTENER listener);
}
