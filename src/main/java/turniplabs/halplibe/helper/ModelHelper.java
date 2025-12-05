package turniplabs.halplibe.helper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ModelHelper {

    public static Map<Class<?>, EntityRenderer<?>> entityRenderers;
    public static Map<Class<?>, TileEntityRenderer<?>> tileEntityRenderers;

    public static BlockModelDispatcher blockModelDispatcher;
    public static ItemModelDispatcher itemModelDispatcher;
    public static EntityRenderDispatcher entityRenderDispatcher;
    public static TileEntityRenderDispatcher tileEntityRenderDispatcher;
    public static BlockColorDispatcher blockColorDispatcher;

    public static void setBlockModel(@NonNull Block<? extends BlockLogic> block, @NonNull Supplier<BlockModel<?>> func) {
        blockModelDispatcher.addDispatch(block,func.get());
    }

    public static void setBlockColor(@NonNull Block<? extends BlockLogic> block, @NonNull Supplier<BlockColor> func) {
        blockColorDispatcher.addDispatch(block,func.get());
    }

    public static void setItemModel(@NonNull Item item, @NonNull Supplier<ItemModel> func) {
        itemModelDispatcher.addDispatch(item,func.get());
    }

    public static void setEntityModel(@NonNull Class<? extends Entity> entity, @NonNull Supplier<EntityRenderer<?>> func) {
        entityRenderers.put(entity, func.get());
    }

    public static void setTileEntityModel(@NonNull Class<? extends TileEntity> tileEntity, @NonNull Supplier<TileEntityRenderer<?>> func) {
        tileEntityRenderers.put(tileEntity, func.get());
    }

}
