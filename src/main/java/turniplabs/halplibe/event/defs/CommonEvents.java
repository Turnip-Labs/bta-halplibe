package turniplabs.halplibe.event.defs;

import turniplabs.halplibe.event.impl.SortedSingleEvent;

public final class CommonEvents {
    public static final SortedSingleEvent<Runnable> BEFORE_GAME_START = new SortedSingleEvent<>("BeforeGameStart");
    public static final SortedSingleEvent<Runnable> AFTER_GAME_START = new SortedSingleEvent<>("AfterGameStart");

    public static final SortedSingleEvent<Runnable> AFTER_BLOCK_INIT = new SortedSingleEvent<>("AfterBlockInit");
    public static final SortedSingleEvent<Runnable> AFTER_ITEM_INIT = new SortedSingleEvent<>("AfterItemInit");

    public static final SortedSingleEvent<Runnable> RECIPES_NAMESPACE_INIT = new SortedSingleEvent<>("RecipesNamespaceInit");
    public static final SortedSingleEvent<Runnable> RECIPES_READY = new SortedSingleEvent<>("RecipesReady");

    private CommonEvents() {}
}
