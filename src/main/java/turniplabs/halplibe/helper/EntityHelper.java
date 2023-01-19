package turniplabs.halplibe.helper;

import net.minecraft.src.*;
import turniplabs.halplibe.mixin.accessors.EntityListAccessor;
import turniplabs.halplibe.mixin.accessors.RenderManagerAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityRendererAccessor;

import java.util.Map;

public class EntityHelper {

    public static void createEntity(Class<? extends Entity> clazz, Render renderer, int id, String name) {
        Map<Class<? extends Entity>, Render> entityRenderMap = ((RenderManagerAccessor) RenderManager.instance).getEntityRenderMap();
        entityRenderMap.put(clazz, renderer);
        renderer.setRenderManager(RenderManager.instance);

        EntityListAccessor.callAddMapping(clazz, name, id);
    }

    public static void createTileEntity(Class<? extends TileEntity> clazz, String name) {
        TileEntityAccessor.callAddMapping(clazz, name);
    }

    public static void createSpecialTileEntity(Class<? extends TileEntity> clazz, TileEntitySpecialRenderer renderer, String name) {
        Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> specialRendererMap = ((TileEntityRendererAccessor) TileEntityRenderer.instance).getSpecialRendererMap();
        specialRendererMap.put(clazz, renderer);
        renderer.setTileEntityRenderer(TileEntityRenderer.instance);

        TileEntityAccessor.callAddMapping(clazz, name);
    }
}
