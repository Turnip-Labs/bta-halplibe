package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = EntityDispatcher.class, remap = false)
public interface EntityListAccessor {

    @Invoker("addMapping")
    static void callAddMapping(Class<? extends Entity> clazz, String name, int id) {
        throw new AssertionError();
    }
}
