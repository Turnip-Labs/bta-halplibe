package turniplabs.halplibe.helper;

import net.minecraft.src.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {

    public static Item createItem(String modId, Item item, String translationKey, String... texture) {
        int[] one = TextureHelper.registerItemTexture(modId, texture[0]);
        for (int index = 1; index < texture.length; ++index) {
            TextureHelper.registerItemTexture(modId, texture[index]);
        }

        return item.setIconCoord(one[0], one[1]).setItemName(HalpLibe.addModId(modId, translationKey));
    }
}
