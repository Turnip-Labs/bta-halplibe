package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.NetworkHelper;

import java.net.Socket;

@Mixin(value = NetworkManager.class, remap = false)
public class NetworkManagerMixin {
    @Shadow public static int[] field_28144_e;

    @Shadow public static int[] field_28145_d;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void postInit(Socket socket, String s, NetHandler nethandler, CallbackInfo ci) {
        field_28144_e = new int[NetworkHelper.getLastPacketId()];
        field_28145_d = new int[NetworkHelper.getLastPacketId()];
    }

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
    @Redirect(
            method = "readPacket()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/packet/Packet;getPacketSize()I"))
    private int readPacket_SizeIncrease(Packet instance){
        return extendedPacketSize(instance);
    }
}
