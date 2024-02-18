package turniplabs.halplibe.util;

import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CreativeEntry implements Comparable<CreativeEntry> {
    public static final HashMap<String, CreativeEntry> entryMap = new HashMap<>();
    public static void addEntry(CreativeEntry entry){
        entryMap.put(entry.itemStack.toString(), entry);
    }
    public int priority;
    public final ItemStack itemStack;
    public CreativeEntry(ItemStack stack){
        this(stack, 1000);
    }
    public CreativeEntry(ItemStack stack, int priority){
        this.itemStack = stack.copy();
        this.itemStack.stackSize = 1;
        this.priority = priority;
        addEntry(this);
    }

    @Override
    public int compareTo(@NotNull CreativeEntry o) {
        return priority - o.priority;
    }
}
