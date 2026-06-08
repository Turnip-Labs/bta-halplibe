package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.eventbus.defs.client.ClientSignals;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = TileEntityRenderDispatcher.class)
public abstract class TileEntityRendererDispatcherMixin {

    @Shadow
    @Final
    @NotNull
    public static TileEntityRenderDispatcher instance;

    @Inject(method = "reload", at = @At(value = "TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initTileEntityModels(instance));
        ClientEvents.TILE_ENTITY_RENDERER_RELOAD.emit(consumer -> consumer.accept(instance));
        HalpLibe.BUS.post(new ClientSignals.TileEntityRendererReload());
    }
}