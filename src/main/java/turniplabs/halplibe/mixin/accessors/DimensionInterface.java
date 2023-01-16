package turniplabs.halplibe.mixin.accessors;

import net.minecraft.src.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Dimension.class, remap = false)
public interface DimensionInterface {

    @Accessor("dimensionList")
    static void setDimensionList(Dimension[] list) {
        throw new AssertionError();
    }
}