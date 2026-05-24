package turniplabs.halplibe.util;

@Deprecated
public interface BlockInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterBlockInit}.
     */
    void afterBlockInit();
}
