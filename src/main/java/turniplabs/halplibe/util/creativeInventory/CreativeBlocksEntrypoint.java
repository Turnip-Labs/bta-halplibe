package turniplabs.halplibe.util.creativeInventory;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

import java.util.List;

public interface CreativeBlocksEntrypoint {

    void populateMisc(List<ItemStack> list);
    void populateStone(List<ItemStack> list);
    void populateWood(List<ItemStack> list);

    // plants
    void populateOrganic(List<ItemStack> list);

    // dirt, glowstone, gravel clay, ice
    void populateNatural(List<ItemStack> list);

    void populateRedstone(List<ItemStack> list);
    void populateOre(List<ItemStack> list);

    // ore blocks. Yeah. ...sorry.
    void populateStorage(List<ItemStack> list);
}
