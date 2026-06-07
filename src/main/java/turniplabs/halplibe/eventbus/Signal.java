package turniplabs.halplibe.eventbus;

import org.jspecify.annotations.NonNull;

public interface Signal {

    default @NonNull State getState() {
        return State.VALID;
    }

    enum State {
        VALID,
        CANCELLED,
        FINAL,
    }
}
