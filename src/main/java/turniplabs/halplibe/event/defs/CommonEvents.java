package turniplabs.halplibe.event.defs;

import turniplabs.halplibe.event.impl.SortedBaseEvent;
import turniplabs.halplibe.event.impl.SortedSingleEvent;

import java.awt.*;
import java.util.function.Consumer;

public final class CommonEvents {
    public static final SortedSingleEvent<Runnable> BEFORE_GAME_START = new SortedSingleEvent<>("BeforeGameStart");
    public static final SortedSingleEvent<Runnable> AFTER_GAME_START = new SortedSingleEvent<>("AfterGameStart");

    /**
     * Guarantees that all {@link net.minecraft.core.block.BlockLogic Block} instances created before this event are
     * non-null. Blocks created by BTA can always be referenced in this event. </br></br>
     * <b>NOTE:</b> This does not mean that any of its lazily initialized fields will be available
     * (such as the {@link net.minecraft.core.block.BlockLogic BlockLogic}), thus referencing them will lead to errors.
     * Only use the Block instance itself.
     * @see CommonEvents#AFTER_ITEM_INIT
     */
    public static final SortedSingleEvent<Runnable> AFTER_BLOCK_INIT = new SortedSingleEvent<>("AfterBlockInit");

    /**
     * Guarantees that all {@link net.minecraft.core.item.Item Item} instances created before this event are non-null.
     * Items created by BTA can always be referenced in this event. In addition, this event is called
     * after all {@link net.minecraft.core.block.Block Blocks} have been fully initialized, so you may freely refer to
     * any Block and its fields (including ones lazily initialized).
     * @see CommonEvents#AFTER_BLOCK_INIT
     */
    public static final SortedSingleEvent<Runnable> AFTER_ITEM_INIT = new SortedSingleEvent<>("AfterItemInit");

    /**
     * Guarantees that all BTA {@link net.minecraft.core.world.Dimension} are registered before any of the custom dimension are.
     * This prevents read out order error from occurring when BTA dimensions are read though list indexing.
     * */
    public static final SortedSingleEvent<Runnable> DIMENSION_REGISTRY = new SortedSingleEvent<>("DimensionRegistry");

    public static final SortedBaseEvent<Runnable> RECIPES_NAMESPACE_INIT = new SortedBaseEvent<>();
    public static final SortedSingleEvent<Runnable> RECIPES_READY = new SortedSingleEvent<>("RecipesReady");

    private CommonEvents() {}
}
