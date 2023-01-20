package turniplabs.halplibe.helper;

import net.minecraft.src.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {

    public static Item createItem(String modId, Item item, String translationKey, String... texture) {
        if (texture.length > 0) {
            int[] mainCoords = TextureHelper.registerItemTexture(modId, texture[0]);
            item.setIconCoord(mainCoords[0], mainCoords[1]);
            for (int index = 1; index < texture.length; ++index) {
                TextureHelper.registerItemTexture(modId, texture[index]);
            }
        }
        return item.setItemName(HalpLibe.addModId(modId, translationKey));
    }
}
