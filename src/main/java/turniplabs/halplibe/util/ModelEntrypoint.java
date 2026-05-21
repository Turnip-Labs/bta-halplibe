package turniplabs.halplibe.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;

@Deprecated
@Environment(EnvType.CLIENT)
public interface ModelEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initModels}.
     */
    void initBlockModels(BlockModelDispatcher dispatcher);

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initModels}.
     */
    void initItemModels(ItemModelDispatcher dispatcher);

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initModels}.
     */
    void initEntityModels(EntityRendererDispatcher dispatcher);

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initModels}.
     */
    void initTileEntityModels(TileEntityRenderDispatcher dispatcher);

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code initModels}.
     */
    void initBlockColors(BlockColorDispatcher dispatcher);
}
