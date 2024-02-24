package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.entity.fx.EntityFX;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityFX.class, remap = false)
public interface EntityFXAccessor {
    @Accessor
    void setParticleRed(float particleRed);

    @Accessor
    void setParticleGreen(float particleGreen);

    @Accessor
    void setParticleBlue(float particleBlue);
}
