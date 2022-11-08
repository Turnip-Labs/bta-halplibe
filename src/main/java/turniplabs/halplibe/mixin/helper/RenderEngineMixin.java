package turniplabs.halplibe.mixin.helper;

import net.minecraft.src.RenderEngine;
import net.minecraft.src.dynamictexture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.List;

@Mixin(value = RenderEngine.class, remap = false, priority = 1100)
public class RenderEngineMixin {
    @Shadow
    private List<DynamicTexture> dynamicTextures;

    @Inject(method = "initDynamicTextures", at = @At("TAIL"))
    private void injectInitDynamicTextures(CallbackInfo ci) {
        dynamicTextures.addAll(TextureHelper.textureHandlers);
    }
}