package turniplabs.halplibe.mixin.creativeInventory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;

import java.util.List;

@Mixin(value = CreativeItems.class)
public abstract class CreativeItemsMixin {

    @WrapOperation(method = "populate", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private static <E> boolean add(List<ItemStack> instance, E e, Operation<Boolean> original) {
        var value = original.call(instance, e);

        if (e instanceof ItemStack stack) {
            instance.addAll(CreativeInventoryRegistry.INSTANCE.getAllFor(stack.getItem().namespaceID));
        }

        return value;
    }
}
