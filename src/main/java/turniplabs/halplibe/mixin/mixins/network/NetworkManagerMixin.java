package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.NetHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}
