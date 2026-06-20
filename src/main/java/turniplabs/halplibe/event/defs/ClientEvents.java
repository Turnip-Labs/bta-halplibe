package turniplabs.halplibe.event.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.event.impl.BaseEvent;
import turniplabs.halplibe.event.impl.SingleEvent;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class ClientEvents {
    public static final SingleEvent<Runnable> BEFORE_CLIENT_START = new SingleEvent<>("BeforeClientStart");
    public static final SingleEvent<Runnable> AFTER_CLIENT_START = new SingleEvent<>("AfterClientStart");

    public static final BaseEvent<Consumer<BlockModelDispatcher>> BLOCK_MODEL_RELOAD = new BaseEvent<>();
    public static final BaseEvent<Consumer<ItemModelDispatcher>> ITEM_MODEL_RELOAD = new BaseEvent<>();
    public static final BaseEvent<Consumer<EntityRendererDispatcher>> ENTITY_RENDERER_RELOAD = new BaseEvent<>();
    public static final BaseEvent<Consumer<TileEntityRenderDispatcher>> TILE_ENTITY_RENDERER_RELOAD = new BaseEvent<>();
    public static final BaseEvent<Consumer<BlockColorDispatcher>> BLOCK_COLOR_RELOAD = new BaseEvent<>();

    private ClientEvents() {}
}
