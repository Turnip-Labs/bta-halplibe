package turniplabs.halplibe.eventbus;

public interface Signal {
    default boolean isCancelled() {
        return false;
    }
}
