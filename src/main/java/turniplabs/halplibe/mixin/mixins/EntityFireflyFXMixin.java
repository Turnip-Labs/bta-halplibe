package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityFireflyFX.class, remap = false)
abstract public class EntityFireflyFXMixin extends EntityFX {
    public EntityFireflyFXMixin(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }


}
