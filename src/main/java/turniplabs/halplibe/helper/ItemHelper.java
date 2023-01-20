package turniplabs.halplibe.helper;

import net.minecraft.src.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {

    public static Item createItem(String modId, Item item, String translationKey, String texture) {
        int[] one = TextureHelper.registerItemTexture(modId, texture);

        return item.setIconCoord(one[0], one[1]).setItemName(HalpLibe.addModId(modId, translationKey));
    }
}
