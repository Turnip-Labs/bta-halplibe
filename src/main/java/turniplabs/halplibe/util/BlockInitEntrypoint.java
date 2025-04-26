package turniplabs.halplibe.util;

public interface BlockInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterBlockInit}.
     */
    void afterBlockInit();
}
