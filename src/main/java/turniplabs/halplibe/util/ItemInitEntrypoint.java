package turniplabs.halplibe.util;

@Deprecated
public interface ItemInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code afterItemInit}.
     */
    void afterItemInit();
}