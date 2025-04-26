package turniplabs.halplibe.util;

import net.minecraft.client.option.GameSettings;

public interface OptionsInitEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initOptions}.
     */
    void initOptions(GameSettings settings);
}
