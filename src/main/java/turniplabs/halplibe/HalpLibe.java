package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.Global;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.VanillaAchievementsPage;
import turniplabs.halplibe.util.network.PacketExtendedMobSpawn;
import turniplabs.halplibe.util.toml.Toml;
import turniplabs.halplibe.util.version.PacketModList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HalpLibe implements ModInitializer, PreLaunchEntrypoint {
    public static final String MOD_ID = "halplibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final TomlConfigHandler CONFIG;
    static {
        Toml toml = new Toml();
        toml.addCategory("Experimental");
        toml.addEntry("Experimental.AtlasWidth", "Dynamically resized the Terrain and Item atlases, value must be an integer greater than or equal to 32",32);

        CONFIG = new TomlConfigHandler(MOD_ID, toml);

        Global.TEXTURE_ATLAS_WIDTH_TILES = Math.max(32, CONFIG.getInt("Experimental.AtlasWidth"));

        // Initialize Block and Item static fields
        try {
            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {
        }
        ModVersionHelper.initialize();
    }
    public static final AchievementPage VANILLA_ACHIEVEMENTS = new VanillaAchievementsPage();
    public static String addModId(String modId, String name) {
        return modId + "." + name;
    }
    @Override
    public void onInitialize() {
        AchievementHelper.addPage(VANILLA_ACHIEVEMENTS);
        NetworkHelper.register(PacketExtendedMobSpawn.class, false, true);
        NetworkHelper.register(PacketModList.class, false, true);
        LOGGER.info("HalpLibe initialized.");
    }

    @Override
    public void onPreLaunch() {
        // Initializes halp statics first
    }
}
