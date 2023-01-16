package turniplabs.halplibe.helper;

import net.minecraft.src.*;
import turniplabs.halplibe.mixin.helper.EntityListInterface;
import turniplabs.halplibe.mixin.helper.RenderManagerInterface;
import turniplabs.halplibe.mixin.helper.TileEntityInterface;
import turniplabs.halplibe.mixin.helper.TileEntityRendererInterface;

import java.util.Map;

public class EntityHelper {

    public static void createEntity(Class<? extends Entity> clazz, Render renderer, int id, String name) {
        Map<Class<? extends Entity>, Render> entityRenderMap = ((RenderManagerInterface) RenderManager.instance).getEntityRenderMap();
        entityRenderMap.put(clazz, renderer);
        renderer.setRenderManager(RenderManager.instance);

        EntityListInterface.callAddMapping(clazz, name, id);
    }

    public static void createTileEntity(Class<? extends TileEntity> clazz, String name) {
        TileEntityInterface.callAddMapping(clazz, name);
    }

    public static void createSpecialTileEntity(Class<? extends TileEntity> clazz, TileEntitySpecialRenderer renderer, String name) {
        Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> specialRendererMap = ((TileEntityRendererInterface) TileEntityRenderer.instance).getSpecialRendererMap();
        specialRendererMap.put(clazz, renderer);
        renderer.setTileEntityRenderer(TileEntityRenderer.instance);

        TileEntityInterface.callAddMapping(clazz, name);
    }
}
