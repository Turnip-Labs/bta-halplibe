package turniplabs.halplibe.mixin;

import net.minecraft.core.net.packet.PacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInputStream;
import java.io.IOException;

import static net.minecraft.core.net.packet.Packet.readStringUTF8;

@Mixin(value = PacketCustomPayload.class, remap = false)
public class PacketCustomPayloadMixin {
    @Shadow
    public String channel;

    @Shadow
    public byte[] data;

    /**
     * @author gungun974
     * @reason Make BTA read every byte of the PacketCustomPayload to prevent read misalignment
     */
    @Overwrite
    public void read(DataInputStream in) throws IOException {
        this.channel = readStringUTF8(in, 128);
        int length = in.readInt();
        if (length > 0 && length < 32768) {
            this.data = new byte[length];
            in.readFully(this.data, 0, length);
        }
    }
}