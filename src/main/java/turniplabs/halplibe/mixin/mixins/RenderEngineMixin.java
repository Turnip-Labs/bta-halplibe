package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class RenderEngineMixin {
    @Shadow
    private List<DynamicTexture> dynamicTextures;
    @Unique
    private final RenderEngine thisAsRenderEngine = (RenderEngine)(Object)this;
    @Inject(method = "initDynamicTextures", at = @At("TAIL"))
    private void initDynamicTextures(CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        for (String key: TextureHelper.textureDestinationResolutions.keySet()) {
            // Updates atlas resolution references
            GL11.glBindTexture(3553, thisAsRenderEngine.getTexture(key));
            int destinationResolution = GL11.glGetTexLevelParameteri(3553, 0, 4096) / TextureHelper.textureAtlasWidths.get(key);
            TextureHelper.textureDestinationResolutions.put(key, destinationResolution);
        }
        TextureHelper.textureHandlers.forEach((handler) -> dynamicTextures.add(handler.newHandler(mc)));
    }
}