package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import turniplabs.halplibe.helper.NetworkHelper;

@Mixin(value = NetworkManager.class, remap = false)
public class NetworkManagerMixin {

    // Increase id size when needed

    @Unique
    public int extendedPacketSize(Packet packet){
        // Add on the additional 3 bytes of id
        return packet.getPacketSize() + (NetworkHelper.useExtendedPacketID() ? 3 : 0);
    }
    @Redirect(
            method = "addToSendQueue(Lnet/minecraft/core/net/packet/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/packet/Packet;getPacketSize()I"))
    private int addToSendQueue_SizeIncrease(Packet instance){
        return extendedPacketSize(instance);
    }
    @Redirect(
            method = "sendPacket()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/packet/Packet;getPacketSize()I"))
    private int sendPacket_SizeIncrease(Packet instance){
        return extendedPacketSize(instance);
    }
}
