package turniplabs.halplibe.mixin.creativeInventory;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeBlocks;
import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.util.creativeInventory.CreativeBlocksEntrypoint;

import java.util.List;

@Mixin(value = CreativeBlocks.class)
public abstract class CreativeBlocksMixin {

    @WrapMethod(method = "populate")
    private static void addMisc(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
            .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
            .forEach(plugin -> plugin.getEntrypoint().populateMisc(list));
    }

    @WrapMethod(method = "addStoneTypes")
    private static void addStone(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateStone(list));
    }

    @WrapMethod(method = "addWoodTypes")
    private static void addWood(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateWood(list));
    }

    @WrapMethod(method = "addNaturalTypes")
    private static void addNatural(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateNatural(list));
    }

    @WrapMethod(method = "addOrganicTypes")
    private static void addOrganic(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateOrganic(list));
    }

    @WrapMethod(method = "addRedstoneTypes")
    private static void addRestone(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateRedstone(list));
    }

    @WrapMethod(method = "addOreTypes")
    private static void addOre(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateOre(list));
    }

    @WrapMethod(method = "addStorageTypes")
    private static void addStorage(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeBlocks", CreativeBlocksEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateStorage(list));
    }
}
