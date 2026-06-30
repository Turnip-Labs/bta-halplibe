package turniplabs.halplibe.util;

@Deprecated(since = "6.1.0")
public interface ClientStartEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code beforeClientStart}.
     */
    void beforeClientStart();

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterClientStart}.
     */
    void afterClientStart();
}
