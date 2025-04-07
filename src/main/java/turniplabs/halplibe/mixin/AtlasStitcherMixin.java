package turniplabs.halplibe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.util.collection.NamespaceID;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.HalpLibe;

import java.awt.image.BufferedImage;
import java.util.Map;

@SuppressWarnings("SuspiciousNameCombination")
@Mixin(value = AtlasStitcher.class,remap = false)
public class AtlasStitcherMixin extends Texture {

    @WrapOperation(method = "init", at = @At(value = "NEW", target = "(III)Ljava/awt/image/BufferedImage;"))
    public BufferedImage init(int width, int height, int imageType, Operation<BufferedImage> original){
        if(height > width) {
            width = height;
            return new BufferedImage(width, width, 2);
        } else {
            height = width;
            return new BufferedImage(height, height, 2);
        }
    }
}
