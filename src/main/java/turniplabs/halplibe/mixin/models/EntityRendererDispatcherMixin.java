package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.EntityRendererDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.eventbus.defs.ClientSignals;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = EntityRendererDispatcher.class)
public abstract class EntityRendererDispatcherMixin {

    @Unique
    public EntityRendererDispatcher thisAs = (EntityRendererDispatcher) (Object)this;

    @Inject(method = "reload", at = @At(value = "TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initEntityModels(thisAs));
        ClientEvents.ENTITY_RENDERER_RELOAD.emit(consumer -> consumer.accept(thisAs));
        HalpLibe.BUS.post(new ClientSignals.EntityRendererReload(thisAs));
    }
}