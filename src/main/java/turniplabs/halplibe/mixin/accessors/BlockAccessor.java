package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Block.class, remap = false)
public interface BlockAccessor {

    @Invoker("withHardness")
    Block callSetHardness(float f);

    @Invoker("withBlastResistance")
    Block callSetResistance(float f);

    @Invoker("withImmovableFlagSet")
    Block callSetImmovable();

    @Invoker("setDropOverride")
    Block callSetDropOverride(Block block);

    @Invoker("withLitInteriorSurface")
    Block callSetIsLitInteriorSurface(boolean isLit);
    @Invoker
    Block callWithDisabledNeighborNotifyOnMetadataChange();

    @Accessor("key")
    void halplibe$setKey(String key);
}
