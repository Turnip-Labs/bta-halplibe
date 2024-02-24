package turniplabs.halplibe.mixin.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.EntityFlying;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.entity.animal.IAnimal;
import net.minecraft.core.enums.EnumFireflyColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.FireflyHelper;
import turniplabs.halplibe.util.FireflyColor;
import turniplabs.halplibe.util.IFireflyColor;

import java.util.List;

@Debug(export = true)
@Mixin(value = EntityFireflyCluster.class, remap = false)
abstract public class EntityFireflyClusterMixin extends EntityFlying implements IAnimal, IFireflyColor {
    public EntityFireflyClusterMixin(World world) {
        super(world);
    }

    /**
     * @author azurelmao
     * @reason bypass unhackable enum EnumFireflyColor
     */
    @Overwrite
    public void spawnInit() {
        super.init();
        Biome currentBiome = this.world.getBlockBiome((int)this.x, (int)this.y, (int)this.z);
        FireflyColor finalColor = null;
        List<FireflyColor> colors = FireflyHelper.registeredColors;

        for (FireflyColor color : colors) {
            Biome[] spawnBiomes = color.getSpawnBiomes();
            if (spawnBiomes != null) {
                for (Biome spawnBiome : spawnBiomes) {
                    if (spawnBiome == currentBiome) {
                        finalColor = color;
                        break;
                    }
                }

                if (finalColor != null) {
                    break;
                }
            }
        }

        if (finalColor == null) {
            finalColor = HalpLibe.fireflyGreen;
        }

        halplibe$setColor(finalColor);
    }

    @Inject(
            method = "slowUpdates",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/animal/EntityFireflyCluster;getColourForBiome(Lnet/minecraft/core/world/biome/Biome;)Lnet/minecraft/core/enums/EnumFireflyColor;"
            ),
            cancellable = true
    )
    private void halplibe$injectSlowUpdates(CallbackInfo ci, @Local(ordinal = 0) int blockX, @Local(ordinal = 1) int blockY, @Local(ordinal = 2) int blockZ) {
        FireflyColor colorForBiome = halplibe$getColourForBiome(world.getBlockBiome(blockX, blockY, blockZ));
        if (colorForBiome != halplibe$getColor()) {
            halplibe$setColor(colorForBiome);
        }
        ci.cancel();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/enums/EnumFireflyColor;getParticleName()Ljava/lang/String;"))
    private String halplibe$redirectParticleName(EnumFireflyColor instance) {
        return halplibe$getColor().getParticleName();
    }

    @WrapOperation(
            method = "merge()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/animal/EntityFireflyCluster;getColor()Lnet/minecraft/core/enums/EnumFireflyColor;",
                    ordinal = 0
            )
    )
    private EnumFireflyColor halplibe$modifyColorExpression(EntityFireflyCluster instance, Operation<EnumFireflyColor> original) {
        if (((IFireflyColor) instance).halplibe$getColor().getId() == halplibe$getColor().getId()) {
            return null;
        } else {
            return EnumFireflyColor.BLUE;
        }
    }

    @Unique
    public void halplibe$setColor(FireflyColor color) {
        this.entityData.set(16, (byte) color.getId());
    }

    @Unique
    public FireflyColor halplibe$getColor() {
        int id = this.entityData.getByte(16);
        List<FireflyColor> colors = FireflyHelper.registeredColors;

        for (FireflyColor color : colors) {
            if (color.getId() == id) {
                return color;
            }
        }

        this.remove();
        new RuntimeException("No FireflyColor found for EntityFireflyCluster with id " + id).printStackTrace();
        return HalpLibe.fireflyGreen;
    }

    @Unique
    private FireflyColor halplibe$getColourForBiome(Biome currentBiome) {
        List<FireflyColor> colors = FireflyHelper.registeredColors;

        for (FireflyColor color : colors) {
            Biome[] spawnBiomes = color.getSpawnBiomes();

            if (spawnBiomes != null) {
                for (Biome biome : spawnBiomes) {
                    if (biome == currentBiome) {
                        return color;
                    }
                }
            }
        }

        return HalpLibe.fireflyGreen;
    }
}
