package turniplabs.halplibe.event.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.event.impl.SortedBaseEvent;
import turniplabs.halplibe.event.impl.SortedSingleEvent;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class ClientEvents {
    public static final SortedSingleEvent<Runnable> BEFORE_CLIENT_START = new SortedSingleEvent<>("BeforeClientStart");
    public static final SortedSingleEvent<Runnable> AFTER_CLIENT_START = new SortedSingleEvent<>("AfterClientStart");

    public static final SortedBaseEvent<Consumer<BlockModelDispatcher>> BLOCK_MODEL_RELOAD = new SortedBaseEvent<>();
    public static final SortedBaseEvent<Consumer<ItemModelDispatcher>> ITEM_MODEL_RELOAD = new SortedBaseEvent<>();
    public static final SortedBaseEvent<Consumer<EntityRendererDispatcher>> ENTITY_RENDERER_RELOAD = new SortedBaseEvent<>();
    public static final SortedBaseEvent<Consumer<TileEntityRenderDispatcher>> TILE_ENTITY_RENDERER_RELOAD = new SortedBaseEvent<>();
    public static final SortedBaseEvent<Consumer<BlockColorDispatcher>> BLOCK_COLOR_RELOAD = new SortedBaseEvent<>();

    private ClientEvents() {}
}
