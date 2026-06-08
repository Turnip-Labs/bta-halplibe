package turniplabs.halplibe.event.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import turniplabs.halplibe.event.impl.SingleEvent;

@Environment(EnvType.SERVER)
public final class ServerEvents {
    public static final SingleEvent<Runnable> BEFORE_SERVER_START = new SingleEvent<>("BeforeServerStart");
    public static final SingleEvent<Runnable> AFTER_SERVER_START = new SingleEvent<>("AfterServerStart");

    private ServerEvents() {}
}
