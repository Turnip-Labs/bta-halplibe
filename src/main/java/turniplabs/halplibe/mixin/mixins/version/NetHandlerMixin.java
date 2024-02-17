package turniplabs.halplibe.mixin.mixins.version;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.util.version.IVersionPackets;
import turniplabs.halplibe.util.version.PacketModList;
@Mixin(value = NetHandler.class, remap = false)
public abstract class NetHandlerMixin implements IVersionPackets {
    @Shadow public abstract void handleInvalidPacket(Packet packet);

    @Unique
    public void halplibe$handleModList(PacketModList packetModList) {
        handleInvalidPacket(packetModList);
    }
}
