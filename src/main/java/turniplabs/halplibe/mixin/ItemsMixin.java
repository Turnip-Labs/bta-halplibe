package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.ItemInitEntrypoint;

@Mixin(value = Items.class)
public abstract class ItemsMixin {
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Items;initStats()V"))
    private static void afterItemInit(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("afterItemInit", ItemInitEntrypoint.class).forEach(ItemInitEntrypoint::afterItemInit);
        CommonEvents.AFTER_ITEM_INIT.emit(Runnable::run);
    }
}
