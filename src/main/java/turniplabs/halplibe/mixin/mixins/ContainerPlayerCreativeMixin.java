package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerPlayerCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.CreativeEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = ContainerPlayerCreative.class, remap = false, priority = 900)
public class ContainerPlayerCreativeMixin {
    @Shadow public static int creativeItemsCount;

    @Shadow public static List<ItemStack> creativeItems;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void test(CallbackInfo ci){
        List<ItemStack> trimmedList = new ArrayList<>(creativeItems);
        List<ItemStack> newList = new ArrayList<>();
        for (ItemStack stack : creativeItems){
            if (CreativeEntry.entryMap.containsKey(stack.toString())){
                trimmedList.remove(stack);
            }
        }

        List<ItemStack> trimmedBlocks = new ArrayList<>();
        List<ItemStack> trimmedItems = new ArrayList<>();

        for (ItemStack stack : trimmedList){
            if (stack.itemID < Block.blocksList.length){
                trimmedBlocks.add(stack);
            } else {
                trimmedItems.add(stack);
            }
        }

        List<CreativeEntry> entries = CreativeEntry.entryMap.values().stream().sorted().collect(Collectors.toList());
        boolean hasAddedVanillaBlocks = false;
        for (CreativeEntry entry : entries){
            if (entry.itemStack.itemID < Block.blocksList.length){
                if (entry.priority > 1000 && !hasAddedVanillaBlocks){
                    hasAddedVanillaBlocks = true;
                    newList.addAll(trimmedBlocks);
                }
                newList.add(entry.itemStack);
            }
        }
        if (!hasAddedVanillaBlocks){
            newList.addAll(trimmedBlocks);
        }

        boolean hasAddedVanillaItems = false;
        for (CreativeEntry entry : entries){
            if (entry.itemStack.itemID >= Block.blocksList.length){
                if (entry.priority > 1000 && !hasAddedVanillaItems){
                    hasAddedVanillaItems = true;
                    newList.addAll(trimmedItems);
                }
                newList.add(entry.itemStack);
            }
        }
        if (!hasAddedVanillaItems){
            newList.addAll(trimmedItems);
        }

        creativeItems = newList;
        creativeItemsCount = creativeItems.size();
    }
}
