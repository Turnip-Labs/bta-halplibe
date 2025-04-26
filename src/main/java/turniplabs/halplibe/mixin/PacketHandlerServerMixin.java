package turniplabs.halplibe.mixin;

import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.network.UniversalPacket;

@Mixin(value = PacketHandlerServer.class,remap = false)
public abstract class PacketHandlerServerMixin {
    @Inject(method = "handleCustomPayload", at = @At(value = "TAIL"))
    public void handleCustomPayload(PacketCustomPayload customPayloadPacket, CallbackInfo ci) {
        if ("HALPLIBE".equals(customPayloadPacket.channel)) {
            new UniversalPacket(customPayloadPacket).handlePacket((PacketHandlerServer) ((Object) this));
        }
    }
}
