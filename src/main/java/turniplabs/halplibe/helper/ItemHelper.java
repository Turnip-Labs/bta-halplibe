package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.mixin.mixins.registry.BlockMixin;
import turniplabs.halplibe.util.registry.IdSupplier;
import turniplabs.halplibe.util.registry.RunLengthConfig;
import turniplabs.halplibe.util.registry.RunReserves;
import turniplabs.halplibe.util.toml.Toml;

import java.util.function.Consumer;

public class ItemHelper {
	
	public static int highestVanilla;

	private static final RunReserves reserves = new RunReserves(
			ItemHelper::findOpenIds,
			ItemHelper::findLength
	);

	/**
	 * Should be called in a runnable scheduled with {@link RegistryHelper#scheduleRegistry(boolean, Runnable)}
	 * @param count the amount of needed blocks for the mod
	 * @return the first available slot to register in
	 */
	public static int findOpenIds(int count) {
		int run = 0;
//		for (int i = highestVanilla; i < Item.itemsList.length; i++) {
		// block ids should always match the id of their corresponding item
		// therefor, start registering items one after the max block id
		for (int i = Block.blocksList.length + 1; i < Item.itemsList.length; i++) {
			if (Item.itemsList[i] == null && !reserves.isReserved(i)) {
				if (run >= count)
					return (i - run);
				run++;
			} else {
				run = 0;
			}
		}
		return -1;
	}

	public static int findLength(int id, int terminate) {
		int run = 0;
		for (int i = id; i < Item.itemsList.length; i++) {
			if (Item.itemsList[i] == null && !reserves.isReserved(i)) {
				run++;
				if (run >= terminate) return terminate;
			} else {
				return run;
			}
		}
		return run;
	}

	/**
	 * Allows halplibe to automatically figure out where to insert the runs
	 * @param modid     an identifier for the mod, can be anything, but should be something the user can identify
	 * @param runs      a toml object representing configured registry runs
	 * @param neededIds the number of needed ids
	 *                  if this changes after the mod has been configured (i.e. mod updated and now has more items) it'll find new, valid runs to put those items into
	 * @param function  the function to run for registering items
	 */
	public static void reserveRuns(String modid, Toml runs, int neededIds, Consumer<IdSupplier> function) {
		RunLengthConfig cfg = new RunLengthConfig(runs, neededIds);
		cfg.register(reserves);
		RegistryHelper.scheduleSmartRegistry(
				() -> {
					IdSupplier supplier = new IdSupplier(modid, reserves, cfg, neededIds);
					function.accept(supplier);
					supplier.validate();
				}
		);
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
