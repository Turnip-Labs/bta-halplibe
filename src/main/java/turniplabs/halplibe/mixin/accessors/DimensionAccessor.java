package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = Dimension.class, remap = false)
public interface DimensionAccessor {

    @Accessor("dimensionList")
    static void setDimensionList(Map<Integer, Dimension> map) {
        throw new AssertionError();
    }
}