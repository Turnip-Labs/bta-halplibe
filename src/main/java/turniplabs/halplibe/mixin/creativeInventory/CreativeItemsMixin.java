package turniplabs.halplibe.mixin.creativeInventory;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeItems;
import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.util.creativeInventory.CreativeItemsEntrypoint;

import java.util.List;

@Mixin(value = CreativeItems.class)
public abstract class CreativeItemsMixin {

    @WrapMethod(method = "populate")
    private static void addMisc(List<ItemStack> out, Operation<Void> original) {
        original.call(out);
        FabricLoader.getInstance()
                .getEntrypointContainers("populateCreativeItems", CreativeItemsEntrypoint.class)
                .forEach(plugin -> plugin.getEntrypoint().populateItems(out));
    }

}
