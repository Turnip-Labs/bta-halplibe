package turniplabs.halplibe.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeMenuContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;

import java.util.List;

@Mixin(value = CreativeMenuContents.class)
public abstract class CreativeBlocksMixin {

    @WrapMethod(method = "populate")
    private static void addMisc(List<ItemStack> list, Operation<Void> original) {
        original.call(list);

        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.MISCELLANEOUS));
        spliceList(list);
    }

    @Unique
    private static void spliceList(List<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            var inList = list.get(i);

            // :D
            if (inList == null) {
                list.remove(i);
                i--;
                continue;
            }

            var toAdd = CreativeInventoryRegistry.INSTANCE.getAllFor(inList.getItem().namespaceID);
            for (ItemStack stack : toAdd) {
                list.add(++i, stack);
            }
        }
    }

    @WrapMethod(method = "addWorkstationsAndGlass")
    private static void addWorkstationsAndGlass(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.WORKBENCHES));
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

    @WrapMethod(method = "addTools")
    private static void addTools(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.TOOLS));
    }

    @WrapMethod(method = "addMiscTools")
    private static void addMiscTools(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.MISC_TOOLS));
    }

    @WrapMethod(method = "addLogTypes")
    private static void addLogTypes(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.LOGS));
    }

    @WrapMethod(method = "addFood")
    private static void addFood(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.FOOD));
    }

    @WrapMethod(method = "addArmor")
    private static void addArmor(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.ARMOR));
    }

    @WrapMethod(method = "addOreProducts")
    private static void addOreProducts(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.ORE_PRODUCTS));
    }

    @WrapMethod(method = "addBasics")
    private static void addBasics(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.BASICS));
    }

    @WrapMethod(method = "addMobDrops")
    private static void addMobDrops(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.DROPS));
    }

    @WrapMethod(method = "addPlaceables")
    private static void addPlaceables(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.PLACEABLES));
    }

    @WrapMethod(method = "addRecords")
    private static void addRecords(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        list.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(CreativeInventoryCategory.RECORDS));
    }
}
