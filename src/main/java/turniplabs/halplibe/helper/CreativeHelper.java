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
    public static void setParent(IItemConvertible itemToAdd, IItemConvertible itemParent){
        setParent(itemToAdd.getDefaultStack(), itemParent.getDefaultStack());
    }
    public static void setParent(IItemConvertible itemToAdd, int metaToAdd, IItemConvertible itemParent, int metaParent){
        setParent(new ItemStack(itemToAdd, 1, metaToAdd), new ItemStack(itemParent, metaParent));
    }
    public static void setParent(ItemStack itemToAdd, ItemStack parentStack){
        CreativeEntry.addEntry(new CreativeEntry(itemToAdd, parentStack));
    }
}
