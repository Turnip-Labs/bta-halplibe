package turniplabs.halplibe.mixin.mixins;

import net.minecraft.src.RenderEngine;
import net.minecraft.src.dynamictexture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class RenderEngineMixin {
    @Shadow
    private List<DynamicTexture> dynamicTextures;

    @Inject(method = "initDynamicTextures", at = @At("TAIL"))
    private void halplibe_initDynamicTextures(CallbackInfo ci) {
        dynamicTextures.addAll(TextureHelper.textureHandlers);
    }
}