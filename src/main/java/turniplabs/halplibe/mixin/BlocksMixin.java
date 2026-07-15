package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

@Mixin(value = Blocks.class, remap = false)
public abstract class BlocksMixin {
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Blocks;resetCaches()V", shift = At.Shift.AFTER))
    private static void afterBlockInit(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("afterBlockInit", BlockInitEntrypoint.class).forEach(BlockInitEntrypoint::afterBlockInit);
        CommonEvents.AFTER_BLOCK_INIT.emit(Runnable::run);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private static void completeInit(CallbackInfo ci) {
        BlockBuilder.Internal.markBlocksInitComplete();
    }
}
