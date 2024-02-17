package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import turniplabs.halplibe.helper.NetworkHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(value = Packet.class, remap = false)
public class PacketMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Ljava/io/DataOutputStream;write(I)V"), method = "writePacket")
    private static void preWrite(DataOutputStream instance, int b) throws IOException {
        if (NetworkHelper.useExtendedPacketID()) instance.writeInt(b);
        else instance.write(b);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/io/DataInputStream;read()I"), method = "readPacket")
    private static int preWrite(DataInputStream instance) throws IOException {
        if (NetworkHelper.useExtendedPacketID()) return instance.readInt();
        else return instance.read();
    }
}
