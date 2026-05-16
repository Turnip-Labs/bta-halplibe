package turniplabs.halplibe.mixin.creativeInventory;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeBlocks;
import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;

import java.util.List;

@Mixin(value = CreativeBlocks.class)
public abstract class CreativeBlocksMixin {

    @WrapMethod(method = "populate")
    private static void addMisc(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.MISCELLANEOUS));
    }

    @WrapMethod(method = "addStoneTypes")
    private static void addStone(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.STONE));
    }

    @WrapMethod(method = "addWoodTypes")
    private static void addWood(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.WOOD));
    }

    @WrapMethod(method = "addNaturalTypes")
    private static void addNatural(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.NATURAL));
    }

    @WrapMethod(method = "addOrganicTypes")
    private static void addOrganic(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.ORGANIC));
    }

    @WrapMethod(method = "addRedstoneTypes")
    private static void addRestone(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.REDSTONE));
    }

    @WrapMethod(method = "addOreTypes")
    private static void addOre(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.ORE));
    }

    @WrapMethod(method = "addStorageTypes")
    private static void addStorage(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.STORAGE));
    }

    @WrapMethod(method = "add")
    private static void add(List<ItemStack> list, Block<?>[] blocks, Operation<Void> original) {
        original.call(list, blocks);
        for (Block<?> block : blocks) {
            list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(block.namespaceId()));
            if (block == Blocks.TROMMEL_IDLE) {
                list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.WORKBENCHES));
            }
        }
    }
}
