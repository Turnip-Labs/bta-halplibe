package turniplabs.halplibe.helper;

import net.minecraft.src.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {

    public static Item createItem(String modId, Item item, String translationKey, String texture) {
        int[] mainCoords = TextureHelper.registerItemTexture(modId, texture);
        item.setIconCoord(mainCoords[0], mainCoords[1]);

        return createItem(modId, item, translationKey);
    }

    public static Item createItem(String modId, Item item, String translationKey) {
        return item.setItemName(HalpLibe.addModId(modId, translationKey));
    }
}
