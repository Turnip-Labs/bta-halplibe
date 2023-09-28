package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.VanillaAchievementsPage;

import java.io.File;
import java.util.Properties;

public class HalpLibe implements ModInitializer {
    public static final String MOD_ID = "halplibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final AchievementPage VANILLA_ACHIEVEMENTS = new VanillaAchievementsPage();

    static {
        // this is here to possibly fix some class loading issues but might not work anyway, delete if it causes even more problems
        try {

            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {
        }
        
    }

    public static String addModId(String modId, String name) {
        return modId + "." + name;
    }

    @Override
    public void onInitialize() {
        AchievementHelper.addPage(VANILLA_ACHIEVEMENTS);
        LOGGER.info("HalpLibe initialized.");
    }
}
