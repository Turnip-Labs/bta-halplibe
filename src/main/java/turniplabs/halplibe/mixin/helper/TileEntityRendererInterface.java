package turniplabs.halplibe.mixin.helper;

import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = TileEntityRenderer.class, remap = false)
public interface TileEntityRendererInterface {

    @Accessor("specialRendererMap")
    Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> getSpecialRendererMap();

}
