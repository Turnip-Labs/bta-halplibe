package turniplabs.halplibe.util;

@Deprecated(since = "6.1.0")
public interface ItemInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterItemInit}.
     */
    void afterItemInit();
}