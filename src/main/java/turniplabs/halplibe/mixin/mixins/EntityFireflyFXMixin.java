package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = EntityFireflyFX.class, remap = false)
abstract public class EntityFireflyFXMixin extends EntityFX {
    @Shadow
    @Final
    float minR;

    @Shadow
    @Final
    float minG;

    public EntityFireflyFXMixin(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.3764706f))
    private float halplibe$modifyConstantR1(float constant) {
        return minR;
    }

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.21176471f))
    private float halplibe$modifyConstantG1(float constant) {
        return minG;
    }

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.0f))
    private float halplibe$modifyConstantB1(float constant) {
        return minG;
    }

    @ModifyConstant(method = "renderParticleInGUI", constant = @Constant(floatValue = 0.3764706f))
    private float halplibe$modifyConstantR2(float constant) {
        return minR;
    }

    @ModifyConstant(method = "renderParticleInGUI", constant = @Constant(floatValue = 0.21176471f))
    private float halplibe$modifyConstantG2(float constant) {
        return minG;
    }

    @ModifyConstant(method = "renderParticleInGUI", constant = @Constant(floatValue = 0.0f))
    private float halplibe$modifyConstantB2(float constant) {
        return minG;
    }
}
