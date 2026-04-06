package turniplabs.halplibe.mixin.recovery;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(value = TileEntityRenderDispatcher.class,remap = false)
public class TileEntityRenderDispatcherMixin {

    @Shadow
    @Final
    private Map<Class<?>, TileEntityRenderer<?>> renderers;

    @Inject(method = "<init>()V", at = @At("TAIL"))
    private void addQueuedModels(CallbackInfo ci){
        if(HalpLibe.CONFIG.getBoolean("recoveryMode")){
            renderers.clear();
        }
    }

}
