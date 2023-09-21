package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.SpecialItemBlock;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.TestAchievementsPage;
import turniplabs.halplibe.util.achievements.VanillaAchievementsPage;

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
        
        RegistryHelper.scheduleRegistry(false, () -> {
            int open = BlockHelper.findOpenIds(20) + 19;
            int openItem = ItemHelper.findOpenIds(21) + 19;
            for (int i1 = 0; i1 < 20; i1++) {
                int i = i1;
                Block bk = new BlockBuilder("lol" ).noItemBlock().build(new Block("lul" + i, i + open, Material.stone));
                ItemHelper.createItem(
                        "lol", new SpecialItemBlock(i + openItem, bk),
                        "lul" + i
                );
            }
            ItemHelper.createItem(
                    "lol",
                    new Item(openItem + 20),
                    "lul" + 20
            );
        });
        RegistryHelper.scheduleRegistry(false, () -> {
            int open = BlockHelper.findOpenIds(20);
            int openItem = ItemHelper.findOpenIds(20);
            for (int i = 0; i < 20; i++) {
                Block bk = new BlockBuilder("lol" ).noItemBlock().build(new Block("lul" + (i + 20), i + open, Material.stone));
                ItemHelper.createItem(
                        "lol", new SpecialItemBlock(i + openItem, bk),
                        "lul" + (i + 21)
                );
            }
        });
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
