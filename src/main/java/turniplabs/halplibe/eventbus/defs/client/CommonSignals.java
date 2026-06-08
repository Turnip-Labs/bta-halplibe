package turniplabs.halplibe.eventbus.defs.client;

import turniplabs.halplibe.eventbus.FinalSignal;

public final class CommonSignals {

    public static final class BeforeGameStart extends FinalSignal {}
    public static final class AfterGameStart extends FinalSignal {}

    public static final class AfterBlockInit extends FinalSignal {}
    public static final class AfterItemInit extends FinalSignal {}

    public static final class RecipesNamespaceInit extends FinalSignal {}
    public static final class RecipesReady extends FinalSignal {}

    private CommonSignals() {}
}
