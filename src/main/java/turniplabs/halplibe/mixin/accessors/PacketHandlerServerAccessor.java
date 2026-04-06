package turniplabs.halplibe.mixin.accessors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.SERVER)
@Mixin(value = PacketHandlerServer.class)
public interface PacketHandlerServerAccessor {
    @Accessor
    PlayerServer getPlayerEntity();
}
