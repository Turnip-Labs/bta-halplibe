package turniplabs.halplibe.helper;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.CreativeEntry;

public class CreativeHelper {
    public static void setPriority(IItemConvertible item, int priority){
        setPriority(item.getDefaultStack(), priority);
    }
    public static void setPriority(IItemConvertible item, int meta, int priority){
        setPriority(new ItemStack(item, 1, meta), priority);
    }
    public static void setPriority(ItemStack item, int priority){
        CreativeEntry.addEntry(new CreativeEntry(item, priority));
    }
}
