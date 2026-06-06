package turniplabs.halplibe.eventbus;

@SuppressWarnings("unused")
public abstract class CancellableSignal {
    protected boolean isCancelled;

    public void cancel() {
        this.isCancelled = true;
    }
}
