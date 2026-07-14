package turniplabs.halplibe.eventbus;

import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public abstract class CancellableSignal implements Signal {
    protected Signal.State state = State.VALID;

    public void cancel() {
        this.state = Signal.State.CANCELLED;
    }

    @Override
    public @NonNull State getState() {
        return state;
    }
}
