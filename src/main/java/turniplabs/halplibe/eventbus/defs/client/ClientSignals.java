package turniplabs.halplibe.eventbus.defs.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import turniplabs.halplibe.eventbus.FinalSignal;
import turniplabs.halplibe.eventbus.Signal;

@Environment(EnvType.CLIENT)
public final class ClientSignals {

    public static final class BeforeClientStart extends FinalSignal {}
    public static final class AfterClientStart extends FinalSignal {}

    public static final class BlockModelReload implements Signal {}
    public static final class ItemModelReload implements Signal {}
    public static final class EntityRendererReload implements Signal {}
    public static final class TileEntityRendererReload implements Signal {}
    public static final class BlockColorReload implements Signal {}

    private ClientSignals() {}
}
