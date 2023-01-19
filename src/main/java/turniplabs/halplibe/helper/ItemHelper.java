package turniplabs.halplibe.helper;

import net.minecraft.src.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {

    public static Item createItem(String modId, Item item, String translationKey, String texture) {
        int[] one = ItemCoords.nextCoords();
        TextureHelper.addTextureToItems(modId, texture, one[0], one[1]);
        return item.setIconCoord(one[0], one[1]).setItemName(HalpLibe.addModId(modId, translationKey));
    }
}
