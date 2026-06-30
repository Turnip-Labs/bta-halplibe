package turniplabs.halplibe.event.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import turniplabs.halplibe.event.impl.SortedSingleEvent;

@Environment(EnvType.SERVER)
public final class ServerEvents {
    public static final SortedSingleEvent<Runnable> BEFORE_SERVER_START = new SortedSingleEvent<>("BeforeServerStart");
    public static final SortedSingleEvent<Runnable> AFTER_SERVER_START = new SortedSingleEvent<>("AfterServerStart");

    private ServerEvents() {}
}
