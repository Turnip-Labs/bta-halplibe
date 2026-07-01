package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.eventbus.defs.ClientSignals;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = TileEntityRenderDispatcher.class)
public abstract class TileEntityRendererDispatcherMixin {

    @Unique
    public TileEntityRenderDispatcher thisAs = (TileEntityRenderDispatcher) (Object)this;

    @Inject(method = "reload", at = @At(value = "TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initTileEntityModels(thisAs));
        ClientEvents.TILE_ENTITY_RENDERER_RELOAD.emit(consumer -> consumer.accept(thisAs));
        HalpLibe.BUS.post(new ClientSignals.TileEntityRendererReload(thisAs));
    }
}