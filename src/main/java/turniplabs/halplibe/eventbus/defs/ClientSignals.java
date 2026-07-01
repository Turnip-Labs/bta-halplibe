package turniplabs.halplibe.eventbus.defs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.eventbus.FinalSignal;
import turniplabs.halplibe.eventbus.Signal;

@Environment(EnvType.CLIENT)
public final class ClientSignals {

    public static final class BeforeClientStart extends FinalSignal {}
    public static final class AfterClientStart extends FinalSignal {}

    public record BlockModelReload (BlockModelDispatcher dispatcher) implements Signal {}
    public record ItemModelReload (ItemModelDispatcher dispatcher) implements Signal {}
    public record EntityRendererReload (EntityRendererDispatcher dispatcher) implements Signal {}
    public record TileEntityRendererReload (TileEntityRenderDispatcher dispatcher) implements Signal {}
    public record BlockColorReload (BlockColorDispatcher dispatcher) implements Signal {}

    private ClientSignals() {}
}
