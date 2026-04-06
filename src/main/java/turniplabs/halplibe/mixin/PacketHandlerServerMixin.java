package turniplabs.halplibe.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.network.UniversalPacket;

@Environment(EnvType.SERVER)
@Mixin(value = PacketHandlerServer.class)
public abstract class PacketHandlerServerMixin {
    @Inject(method = "handleCustomPayload", at = @At(value = "TAIL"))
    public void handleCustomPayload(PacketCustomPayload packetCustomPayload, CallbackInfo ci) {
        if ("HALPLIBE".equals(packetCustomPayload.channel)) {
            new UniversalPacket(packetCustomPayload).handlePacket((PacketHandlerServer) ((Object) this));
        }
    }
}