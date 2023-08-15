package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BlockFire.class, remap = false)
public interface BlockFireAccessor {
    @Invoker("setBurnRate")
    void callSetBurnRate(int id, int chanceToCatchFire, int chanceToDegrade);
}
