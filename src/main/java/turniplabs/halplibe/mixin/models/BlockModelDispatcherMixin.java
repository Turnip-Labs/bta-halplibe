package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.eventbus.defs.client.ClientSignals;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = BlockModelDispatcher.class)
public abstract class BlockModelDispatcherMixin {

    @Shadow
    private static BlockModelDispatcher instance;

    @Inject(method = "reload", at = @At("TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initBlockModels(instance));
        ClientEvents.BLOCK_MODEL_RELOAD.emit(consumer -> consumer.accept(instance));
        HalpLibe.BUS.post(new ClientSignals.BlockModelReload());
    }
}