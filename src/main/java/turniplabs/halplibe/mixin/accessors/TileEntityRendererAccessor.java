package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = TileEntityRenderDispatcher.class, remap = false)
public interface TileEntityRendererAccessor {
    @Accessor("renderers")
    Map<Class<? extends TileEntity>, TileEntityRenderer<?>> getSpecialRendererMap();

}
