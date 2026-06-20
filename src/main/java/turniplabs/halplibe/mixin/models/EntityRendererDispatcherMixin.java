package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.event.defs.ClientEvents;
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
    }
}