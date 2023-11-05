package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.enums.MipmapType;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.core.util.helper.Buffer;
import net.minecraft.core.util.helper.Textures;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.helper.TextureHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class RenderEngineMixin {
    @Shadow
    private List<DynamicTexture> dynamicTextures;

    @Shadow public abstract int getTexture(String name);

    @Shadow protected abstract void generateMipmaps(ByteBuffer buffer, BufferedImage image, int levels, boolean smooth);

    @Shadow @Final public Minecraft minecraft;
    @Shadow private boolean clampTexture;
    @Shadow private boolean blurTexture;
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
    private String textureName = "";
    @Inject(method = "getTexture(Ljava/lang/String;)I", at = @At("HEAD"))
    private void redirectAtlasTextures(String name, CallbackInfoReturnable<Integer> cir){
        textureName = name;
    }
    @Inject(method = "setupTexture(Ljava/lang/String;)V", at = @At("HEAD"))
    private void redirectAtlasTextures2(String name, CallbackInfo ci){
        textureName = name;
    }
    @Inject(method = "setupTexture(Ljava/awt/image/BufferedImage;IZ)V", at = @At("HEAD"), cancellable = true)
    private void adjustAtlasSize(BufferedImage img, int id, boolean mipmap, CallbackInfo ci){
        if (textureName.equals("/terrain.png") || textureName.equals("/gui/items.png")){
            textureName = "";
            BufferedImage testImage = new BufferedImage(img.getWidth()*2, img.getHeight()*2, img.getType());
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    testImage.setRGB(x, y, img.getRGB(x, y));
                }
            }

            GL11.glBindTexture(3553, id);
            if (this.blurTexture) {
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
            } else {
                GL11.glTexParameteri(3553, 10241, 9728);
                GL11.glTexParameteri(3553, 10240, 9728);
            }
            if (this.clampTexture) {
                GL11.glTexParameteri(3553, 10242, 10496);
                GL11.glTexParameteri(3553, 10243, 10496);
            } else {
                GL11.glTexParameteri(3553, 10242, 10497);
                GL11.glTexParameteri(3553, 10243, 10497);
            }
            int w = testImage.getWidth();
            int h = testImage.getHeight();
            Buffer.put(testImage);
            GL11.glTexImage2D(3553, 0, 6408, w, h, 0, 6408, 5121, Buffer.buffer);
            if (mipmap) {
                this.generateMipmaps(Buffer.buffer, testImage, (Integer)this.minecraft.gameSettings.mipmapLevels.value, this.minecraft.gameSettings.mipmapType.value == MipmapType.SMOOTH);
            }
            ci.cancel();
        }
        textureName = "";
    }
}