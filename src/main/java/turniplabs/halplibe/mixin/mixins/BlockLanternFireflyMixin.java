package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLanternFirefly;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.enums.EnumFireflyColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.FireflyColor;
import turniplabs.halplibe.util.IFireflyColor;

@Debug(export = true)
@Mixin(value = BlockLanternFirefly.class, remap = false)
abstract public class BlockLanternFireflyMixin extends Block implements IFireflyColor {
    @Mutable
    @Shadow
    @Final
    private EnumFireflyColor color;

    public BlockLanternFireflyMixin(String key, int id, Material material) {
        super(key, id, material);
    }

    @Unique
    private FireflyColor halplibe$color;

    @Unique
    public void halplibe$setColor(FireflyColor color) {
        halplibe$color = color;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void constructorInject(String key, int id, EnumFireflyColor colour, CallbackInfo ci) {
        color = null;
    }

    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/enums/EnumFireflyColor;getParticleName()Ljava/lang/String;"))
    private String redirectParticleName(EnumFireflyColor instance) {
        return halplibe$color.getParticleName();
    }

    @Redirect(
            method = "onBlockDestroyedByPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/animal/EntityFireflyCluster;setColor(Lnet/minecraft/core/enums/EnumFireflyColor;)V"
            )
    )
    private void redirectColor(EntityFireflyCluster instance, EnumFireflyColor colour) {
        ((IFireflyColor) instance).halplibe$setColor(halplibe$color);
    }
}
