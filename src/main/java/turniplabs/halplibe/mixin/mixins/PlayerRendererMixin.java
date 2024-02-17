package turniplabs.halplibe.mixin.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.util.ArmorMaterialMixinInterface;

@Mixin(value = PlayerRenderer.class, remap = false)
abstract public class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
    public PlayerRendererMixin(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @ModifyArg(method = "setArmorModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;loadTexture(Ljava/lang/String;)V", ordinal = 3))
    private String changeTexturePath(String vanillaTexturePath, @Local(ordinal = 0) ItemArmor itemArmor, @Local(ordinal = 0, argsOnly = true) int renderPass) {
        String modId = ((ArmorMaterialMixinInterface) itemArmor.material).halplibe$getModId();
        if (modId == null) {
            return vanillaTexturePath;
        }

        String path = "/assets/" + modId + "/armor/" + itemArmor.material.name + "_" + (renderPass != 2 ? 1 : 2) + ".png";
        HalpLibe.LOGGER.info(path);
        return path;
    }
}
