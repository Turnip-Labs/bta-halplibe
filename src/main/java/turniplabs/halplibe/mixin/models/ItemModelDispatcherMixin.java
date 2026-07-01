package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.eventbus.defs.client.ClientSignals;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = ItemModelDispatcher.class)
public abstract class ItemModelDispatcherMixin {

    @Unique
    public ItemModelDispatcher thisAs = (ItemModelDispatcher) (Object)this;

    @Inject(method = "reload", at = @At("TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initItemModels(thisAs));
        ClientEvents.ITEM_MODEL_RELOAD.emit(consumer -> consumer.accept(thisAs));
        HalpLibe.BUS.post(new ClientSignals.ItemModelReload(thisAs));
    }
}