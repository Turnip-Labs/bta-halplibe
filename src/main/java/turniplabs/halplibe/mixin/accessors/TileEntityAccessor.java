package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.block.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = TileEntity.class, remap = false)
public interface TileEntityAccessor {

    @Invoker("addMapping")
    static void callAddMapping(Class<?> clazz, String s) {
        throw new AssertionError();
    }
}
