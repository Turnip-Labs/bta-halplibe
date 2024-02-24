package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.ParticleHelper;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderGlobalMixin {

    @Shadow
    private Minecraft mc;

    @Shadow
    private World worldObj;

    @Inject(
            method = "addParticle(Ljava/lang/String;DDDDDDD)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
                    ordinal = 0
            )
    )
    private void spawnParticle(String particleId, double x, double y, double z, double motionX, double motionY, double motionZ, double maxDistance, CallbackInfo ci) {
        Map<String, Class<? extends EntityFX>> particlesOld = ParticleHelper.particlesOld;
        Class<? extends EntityFX> clazz = particlesOld.get(particleId);
        if (clazz != null) {
            try {
                mc.effectRenderer.addEffect(clazz
                        .getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class)
                        .newInstance(worldObj, x, y, z, motionX, motionY, motionZ));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, ParticleHelper.ParticleLambda> particles = ParticleHelper.particles;
        ParticleHelper.ParticleLambda lambda = particles.get(particleId);
        if (lambda != null) {
            this.mc.effectRenderer.addEffect(lambda.run(worldObj, x, y, z, motionX, motionY, motionZ));
        }
    }

}
