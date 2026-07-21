package turniplabs.halplibe.mixin.models;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.util.dispatch.Dispatcher;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.ModelEntrypoint;

@Mixin(value = BlockColorDispatcher.class)
public abstract class BlockColorDispatcherMixin extends Dispatcher<Block<?>, BlockColor> {

    @Unique
    public BlockColorDispatcher thisAs = (BlockColorDispatcher) (Object)this;

    @Inject(method = "reload", at = @At("TAIL"))
    private void addQueuedModels(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initModels", ModelEntrypoint.class)
                .forEach(e -> e.initBlockColors(thisAs));
        ClientEvents.BLOCK_COLOR_RELOAD.emit(consumer -> consumer.accept(thisAs));
    }
}