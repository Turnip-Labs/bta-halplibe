package turniplabs.halplibe.mixin.mixins.version;

import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet2Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.util.version.IVersionPackets;
import turniplabs.halplibe.util.version.PacketModList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mixin(value = NetClientHandler.class, remap = false)
public abstract class NetClientHandlerMixin extends NetHandler implements IVersionPackets {
    @Unique
    public void halplibe$handleModList(PacketModList packetModList) {
        try {
            Method m = ModVersionHelper.class.getDeclaredMethod("handleModListPacket", PacketModList.class);
            m.setAccessible(true);
            m.invoke(null, packetModList);
            m.setAccessible(false);
        } catch (Throwable err) {
            err.printStackTrace();
        }
    }
    @Inject(method = "handleHandshake(Lnet/minecraft/core/net/packet/Packet2Handshake;)V", at = @At("HEAD"))
    private void resetServerModlist(Packet2Handshake packet2handshake, CallbackInfo ci){
        try {
            Field m = ModVersionHelper.class.getDeclaredField("serverMods");
            m.setAccessible(true);
            m.set(null, null);
            m.setAccessible(false);
        } catch (Throwable err) {
            err.printStackTrace();
        }
        HalpLibe.LOGGER.info(String.valueOf(ModVersionHelper.getServerModlist() == null));
    }
}
