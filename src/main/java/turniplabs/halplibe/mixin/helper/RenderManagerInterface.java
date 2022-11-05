package turniplabs.halplibe.mixin.helper;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = RenderManager.class, remap = false)
public interface RenderManagerInterface {

    @Accessor("entityRenderMap")
    Map<Class<? extends Entity>, Render> getEntityRenderMap();
}
