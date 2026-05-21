package turniplabs.halplibe.util;

@Deprecated
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
