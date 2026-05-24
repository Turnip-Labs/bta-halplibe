package turniplabs.halplibe.event.defs;

import turniplabs.halplibe.event.impl.SingleEvent;

public final class CommonEvents {
    public static SingleEvent<Runnable> BEFORE_GAME_START = new SingleEvent<>("BeforeGameStart");
    public static SingleEvent<Runnable> AFTER_GAME_START = new SingleEvent<>("AfterGameStart");

    public static SingleEvent<Runnable> AFTER_BLOCK_INIT = new SingleEvent<>("AfterBlockInit");
    public static SingleEvent<Runnable> AFTER_ITEM_INIT = new SingleEvent<>("AfterItemInit");

    public static SingleEvent<Runnable> RECIPES_NAMESPACE_INIT = new SingleEvent<>("RecipesNamespaceInit");
    public static SingleEvent<Runnable> RECIPES_READY = new SingleEvent<>("RecipesReady");

    private CommonEvents() {}
}
