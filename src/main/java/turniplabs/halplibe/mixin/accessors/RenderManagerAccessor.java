package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = EntityRenderDispatcher.class, remap = false)
public interface RenderManagerAccessor {
    @Accessor("renderers")
    Map<Class<? extends Entity>, EntityRenderer<?>> getEntityRenderMap();
}
