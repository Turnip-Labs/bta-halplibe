package turniplabs.halplibe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.data.registry.Registries;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;

public class HalpLibe implements ModInitializer {
    public static final boolean isClient = FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);
    public static final String MOD_ID = HalpLibe.registerMod("halplibe", false);
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final TomlConfigHandler CONFIG;

    static {
        Toml defaultConfig = new Toml("Halplibe configuration file.");
        defaultConfig.addEntry("recoveryMode",false);
        CONFIG = new TomlConfigHandler(MOD_ID, new Toml("Halplibe configuration file."), false);
        File configFile = CONFIG.getConfigFile();

        boolean changed = false;
        if (CONFIG.getConfigFile().exists()) {
            CONFIG.loadConfig();
            Toml rawConfig = CONFIG.getRawParsed();
            CONFIG.setDefaults(rawConfig);

            if(!rawConfig.contains("recoveryMode")) {
                rawConfig.addEntry("recoveryMode",false);
                changed = true;
            }

            if(changed){
                CONFIG.setDefaults(rawConfig);
                CONFIG.writeConfig();
                CONFIG.loadConfig();
            }
        } else {
            CONFIG.setDefaults(defaultConfig);
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                CONFIG.writeConfig();
                CONFIG.loadConfig();
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate config!", e);
            }

        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("HalpLibe initialized.");
    }

    @SuppressWarnings("unused")
    public static @NonNull String registerMod(@NonNull String modId) {
        return registerMod(modId, true);
    }

    public static @NonNull String registerMod(@NonNull String modId, boolean preloadAssets) {
        if (!preloadAssets && isClient) {
            TextureRegistry.excludedNamespaces.add(modId);
        }
        Registries.NAMESPACES.register(modId, modId);
        return modId;
    }
}
