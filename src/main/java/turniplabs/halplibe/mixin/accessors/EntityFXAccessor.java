package turniplabs.halplibe.mixin.accessors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(value = Particle.class)
public interface EntityFXAccessor {
    @Accessor
    void setRCol(float particleRed);

    @Accessor
    void setGCol(float particleGreen);

    @Accessor
    void setBCol(float particleBlue);
}
