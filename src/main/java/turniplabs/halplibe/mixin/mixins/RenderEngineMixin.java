package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texturepack.TexturePackBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.TextureHandler;

import java.awt.image.BufferedImage;
import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class RenderEngineMixin {
    @Shadow
    private List<DynamicTexture> dynamicTextures;
    @Inject(method = "initDynamicTextures", at = @At("TAIL"))
    private void initDynamicTextures(CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        TextureHelper.textureHandlers.forEach((handler) -> dynamicTextures.add(handler.newHandler(mc)));
    }
}