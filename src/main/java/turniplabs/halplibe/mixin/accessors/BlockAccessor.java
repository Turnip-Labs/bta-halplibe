package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Block.class, remap = false)
public interface BlockAccessor {

    @Invoker("withHardness")
    Block callSetHardness(float f);

    @Invoker("withBlastResistance")
    Block callSetResistance(float f);

    @Invoker("withLightOpacity")
    Block callSetLightOpacity(int i);

    @Invoker("withLightValue")
    Block callSetLightValue(float f);

    @Invoker("withLightValue")
    Block callSetLightValue(int i);

    @Invoker("withImmovableFlagSet")
    Block callSetImmovable();

    @Invoker("setDropOverride")
    Block callSetDropOverride(Block block);

    @Invoker("withLitInteriorSurface")
    Block callSetIsLitInteriorSurface(boolean isLit);

    @Invoker("withSetUnbreakable")
    Block callSetBlockUnbreakable();
}
