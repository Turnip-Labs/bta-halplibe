package turniplabs.halplibe.eventbus;

import org.jspecify.annotations.NonNull;

public abstract class FinalSignal implements Signal {
    @Override
    public final @NonNull State getState() {
        return State.FINAL;
    }
}
