package turniplabs.halplibe.eventbus.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import turniplabs.halplibe.eventbus.FinalSignal;

@Environment(EnvType.SERVER)
public final class ServerSignals {

    public static final class BeforeServerStart extends FinalSignal {}
    public static final class AfterServerStart extends FinalSignal {}

    private ServerSignals() {}
}
