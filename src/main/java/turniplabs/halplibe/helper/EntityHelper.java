package turniplabs.halplibe.helper;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import turniplabs.halplibe.mixin.accessors.EntityListAccessor;
import turniplabs.halplibe.mixin.accessors.RenderManagerAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityAccessor;
import turniplabs.halplibe.mixin.accessors.TileEntityRendererAccessor;

import java.util.Map;

public class EntityHelper {
    /**
     * @deprecated No longer works serverside as of BTA 7.1 due to the removal of "Minecraft.class" from the server jar
     * Replaced by {@link Core#createEntity(Class, int, String)} and {@link Client#assignEntityRenderer(Class, EntityRenderer)}
     */
    @Deprecated
    public static void createEntity(Class<? extends Entity> clazz, EntityRenderer<?> renderer, int id, String name) {
        Client.assignEntityRenderer(clazz, renderer);
        EntityListAccessor.callAddMapping(clazz, name, id);
    }

    /**
     * @deprecated Function is being moved to {@link Core#createTileEntity(Class, String)}
     */
    @Deprecated
    public static void createTileEntity(Class<? extends TileEntity> clazz, String name) {
        TileEntityAccessor.callAddMapping(clazz, name);
    }

    /**
     * @deprecated Function is being moved to {@link Core#createSpecialTileEntity(Class, TileEntityRenderer, String)} (Class, String)}
     */
    @Deprecated
    public static void createSpecialTileEntity(Class<? extends TileEntity> clazz, TileEntityRenderer<?> renderer, String name) {
        Map<Class<? extends TileEntity>, TileEntityRenderer<?>> specialRendererMap = ((TileEntityRendererAccessor) TileEntityRenderDispatcher.instance).getSpecialRendererMap();
        specialRendererMap.put(clazz, renderer);
        renderer.setRenderDispatcher(TileEntityRenderDispatcher.instance);

        TileEntityAccessor.callAddMapping(clazz, name);
    }
    /**
     * Functions to call from the client or server
     */
    public static class Core {
        public static void createEntity(Class<? extends Entity> clazz, int id, String name) {
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

    /**
     * Functions to only call from the client
     */
    public static class Client {
        public static void assignEntityRenderer(Class<? extends Entity> clazz, EntityRenderer<?> renderer){
            Map<Class<? extends Entity>, EntityRenderer<?>> entityRenderMap = ((RenderManagerAccessor) EntityRenderDispatcher.instance).getEntityRenderMap();
            entityRenderMap.put(clazz, renderer);
            renderer.setRenderDispatcher(EntityRenderDispatcher.instance);
        }
    }
}
