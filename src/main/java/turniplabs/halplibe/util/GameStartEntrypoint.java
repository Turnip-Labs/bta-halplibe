package turniplabs.halplibe.util;

@Deprecated
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
