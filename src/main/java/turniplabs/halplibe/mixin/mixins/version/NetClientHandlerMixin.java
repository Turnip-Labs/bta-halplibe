package turniplabs.halplibe.mixin.mixins.version;

import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.handler.NetHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.util.version.IVersionPackets;
import turniplabs.halplibe.util.version.PacketModList;

import java.lang.reflect.Method;

@Mixin(value = NetClientHandler.class, remap = false)
public abstract class NetClientHandlerMixin extends NetHandler implements IVersionPackets {
    @Unique
    public void handleModList(PacketModList packetModList) {
        try {
            Method m = ModVersionHelper.class.getDeclaredMethod("handleModListPacket", PacketModList.class);
            m.setAccessible(true);
            m.invoke(null, packetModList);
            m.setAccessible(false);
        } catch (Throwable err) {
            err.printStackTrace();
        }
    }
}
