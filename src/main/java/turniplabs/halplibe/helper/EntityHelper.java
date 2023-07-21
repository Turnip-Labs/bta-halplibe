package turniplabs.halplibe.helper;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import turniplabs.halplibe.mixin.accessors.EntityListAccessor;
import turniplabs.halplibe.mixin.accessors.RenderManagerAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityRendererAccessor;

import java.util.Map;

public class EntityHelper {

    public static void createEntity(Class<? extends Entity> clazz, EntityRenderer<?> renderer, int id, String name) {
        Map<Class<? extends Entity>, EntityRenderer<?>> entityRenderMap = ((RenderManagerAccessor) EntityRenderDispatcher.instance).getEntityRenderMap();
        entityRenderMap.put(clazz, renderer);
        renderer.setRenderDispatcher(EntityRenderDispatcher.instance);

        EntityListAccessor.callAddMapping(clazz, name, id);
    }

    public static void createTileEntity(Class<? extends TileEntity> clazz, String name) {
        TileEntityAccessor.callAddMapping(clazz, name);
    }

    public static void createSpecialTileEntity(Class<? extends TileEntity> clazz, TileEntityRenderer<?> renderer, String name) {
        Map<Class<? extends TileEntity>, TileEntityRenderer<?>> specialRendererMap = ((TileEntityRendererAccessor) TileEntityRenderDispatcher.instance).getSpecialRendererMap();
        specialRendererMap.put(clazz, renderer);
        renderer.setRenderDispatcher(TileEntityRenderDispatcher.instance);

        TileEntityAccessor.callAddMapping(clazz, name);
    }
}
