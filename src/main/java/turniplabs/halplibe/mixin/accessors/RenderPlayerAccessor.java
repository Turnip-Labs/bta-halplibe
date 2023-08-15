package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.render.entity.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PlayerRenderer.class, remap = false)
public interface RenderPlayerAccessor {

    @Accessor("armorFilenamePrefix")
    static String[] getArmorFilenamePrefix() {
        throw new AssertionError();
    }

    @Accessor("armorFilenamePrefix")
    static void setArmorFilenamePrefix(String[] strings) {
        throw new AssertionError();
    }
}
