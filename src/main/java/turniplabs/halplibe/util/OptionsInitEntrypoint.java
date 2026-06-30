package turniplabs.halplibe.util;

@Deprecated(since = "6.1.0", forRemoval = true)
public interface OptionsInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initOptions}.
     */
    void initOptions();
}
