package turniplabs.halplibe.mixin.accessors;

import net.minecraft.src.Block;
import net.minecraft.src.StepSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Block.class, remap = false)
public interface BlockInterface {

    @Invoker("setHardness")
    Block callSetHardness(float f);

    @Invoker("setResistance")
    Block callSetResistance(float f);

    @Invoker("setStepSound")
    Block callSetStepSound(StepSound stepSound);

    @Invoker("setLightOpacity")
    Block callSetLightOpacity(int i);

    @Invoker("setLightValue")
    Block callSetLightValue(float f);

    @Invoker("setLightValue")
    Block callSetLightValue(int i);

    @Invoker("setImmovable")
    Block callSetImmovable();

    @Invoker("setDropOverride")
    Block callSetDropOverride(Block block);

    @Invoker("setIsLitInteriorSurface")
    Block callSetIsLitInteriorSurface(boolean isLit);

    @Invoker("setBlockUnbreakable")
    Block callSetBlockUnbreakable();
}
