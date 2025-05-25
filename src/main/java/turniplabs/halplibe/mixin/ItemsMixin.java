package turniplabs.halplibe.mixin;

import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Items.class ,remap = false)
public abstract class ItemsMixin {
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Items;initStats()V"))
    private static void delayInitStats() {}
}
