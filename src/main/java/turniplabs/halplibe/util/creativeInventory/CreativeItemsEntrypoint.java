package turniplabs.halplibe.util.creativeInventory;

import net.minecraft.core.item.ItemStack;

import java.util.List;

public interface CreativeItemsEntrypoint {

    // blame melon!
    void populateItems(List<ItemStack> list);
}
