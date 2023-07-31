package turniplabs.halplibe.mixin.accessors;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = EntityList.class, remap = false)
public interface EntityListAccessor {

	@Invoker("addMapping")
	static void callAddMapping(Class<? extends Entity> clazz, String name, int id) {
		throw new AssertionError();
	}
}
