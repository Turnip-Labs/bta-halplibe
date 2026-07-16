package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Blocks.class)
public interface BlocksAccessor {
    @Invoker("cacheBlock")
    static void cacheBlock(Block<?> container) {
        throw new AssertionError();
    }
}
