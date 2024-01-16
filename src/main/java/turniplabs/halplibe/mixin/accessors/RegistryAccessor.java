package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.data.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = Registry.class, remap = false)
public interface RegistryAccessor {
    @Accessor
    List<?> getItems();
}
