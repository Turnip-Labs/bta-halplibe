package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.HalpLibe;

public class ItemHelper {
	
	public static int highestVanilla;
	
	/**
	 * Should be called in a runnable scheduled with {@link RegistryHelper#scheduleRegistry(boolean, Runnable)}
	 * @param count the amount of needed blocks for the mod
	 * @return the first available slot to register in
	 */
	public static int findOpenIds(int count) {
		int run = 0;
		for (int i = highestVanilla; i < Item.itemsList.length; i++) {
			if (Item.itemsList[i] == null) {
				if (run >= count)
					return (i - run);
				run++;
			} else {
				run = 0;
			}
		}
		return -1;
	}
	
	public static Item createItem(String modId, Item item, String translationKey, String texture) {
        int[] mainCoords = TextureHelper.getOrCreateItemTexture(modId, texture);
        item.setIconCoord(mainCoords[0], mainCoords[1]);

        return createItem(modId, item, translationKey);
    }

    public static Item createItem(String modId, Item item, String translationKey) {
        return item.setKey(HalpLibe.addModId(modId, translationKey));
    }
}
