package azurelmao.halplibe.helper;

import azurelmao.halplibe.mixin.helper.EntityListInterface;
import azurelmao.halplibe.mixin.helper.RenderManagerInterface;
import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;

import java.util.Map;

public class EntityHelper {

    public static void createEntity(Class<? extends Entity> clazz, Render render, int id, String name) {
        Map<Class<? extends Entity>, Render> entityRenderMap = ((RenderManagerInterface) RenderManager.instance).getEntityRenderMap();
        entityRenderMap.put(clazz, render);
        render.setRenderManager(RenderManager.instance);

        EntityListInterface.callAddMapping(clazz, name, id);
    }
}
