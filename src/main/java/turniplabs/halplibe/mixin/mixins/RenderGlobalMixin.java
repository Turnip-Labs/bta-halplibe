package turniplabs.halplibe.mixin.mixins;

import turniplabs.halplibe.helper.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityFX;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderGlobalMixin {

    @Shadow
    private Minecraft mc;

    @Shadow
    private World worldObj;

    @Inject(method = "spawnParticle", at = @At(value = "TAIL"))
    private void halplibe_spawnParticle(String particle, double x, double y, double z, double motionX, double motionY, double motionZ, CallbackInfo ci) {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            double distanceX = this.mc.renderViewEntity.posX - x;
            double distanceY = this.mc.renderViewEntity.posY - y;
            double distanceZ = this.mc.renderViewEntity.posZ - z;
            double maxDistance = 16.0;

            if (!(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > maxDistance * maxDistance)) {
                Map<String, Class<? extends EntityFX>> particles = ParticleHelper.particles;
                for (String name : particles.keySet()) {
                    if (name.equals(particle)) {
                        Class<? extends EntityFX> clazz = particles.get(name);

                        try {
                            mc.effectRenderer.addEffect(clazz
                                    .getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class)
                                    .newInstance(worldObj, x, y, z, motionX, motionY, motionZ));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
        }
    }

}
