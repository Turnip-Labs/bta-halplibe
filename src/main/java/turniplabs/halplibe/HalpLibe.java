package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.VanillaAchievementsPage;
import turniplabs.halplibe.util.network.PacketExtendedMobSpawn;

public class HalpLibe implements ModInitializer, PreLaunchEntrypoint {
    public static final String MOD_ID = "halplibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    static {
        Global.TEXTURE_ATLAS_WIDTH_TILES = 64;
        // this is here to possibly fix some class loading issues but might not work anyway, delete if it causes even more problems
        try {
            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {
        }

    }
    public static final AchievementPage VANILLA_ACHIEVEMENTS = new VanillaAchievementsPage();
    public static String addModId(String modId, String name) {
        return modId + "." + name;
    }

    @Override
    public void onInitialize() {
        AchievementHelper.addPage(VANILLA_ACHIEVEMENTS);
        NetworkHelper.register(PacketExtendedMobSpawn.class, false, true);
        LOGGER.info("HalpLibe initialized.");
    }

    @Override
    public void onPreLaunch() {
        // Initializes halp statics first
    }
}
