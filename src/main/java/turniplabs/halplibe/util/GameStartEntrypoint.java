package turniplabs.halplibe.util;

@Deprecated(since = "6.1.0", forRemoval = true)
public interface GameStartEntrypoint {

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code beforeGameStart}.
     */
    void beforeGameStart();

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterGameStart}.
     */
    void afterGameStart();
}
