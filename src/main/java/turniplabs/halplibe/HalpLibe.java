package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.src.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.TextureHelper;

public class HalpLibe implements ModInitializer {
    public static final String MOD_ID = "halplibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return MOD_ID + "." + name;
    }

    public static final Item testItem = new Item(200).setItemName("testitem");

    @Override
    public void onInitialize() {
        LOGGER.info("HalpLibe initialized.");
        TextureHelper.addTextureToItems(MOD_ID, "test.png", 16, 0);
    }
}
