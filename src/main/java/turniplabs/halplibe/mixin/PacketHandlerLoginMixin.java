package turniplabs.halplibe.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.PacketHandshake;
import net.minecraft.server.net.handler.PacketHandlerLogin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Environment(EnvType.SERVER)
@Mixin(value = PacketHandlerLogin.class)
public abstract class PacketHandlerLoginMixin {
	@Shadow
	public NetworkManager netManager;

	@Inject(method = "handleHandshake", at = @At(value = "HEAD"))
	public void sendMessagesConfiguration(PacketHandshake packetHandshake, CallbackInfo ci) {
		this.netManager.addToSendQueue(NetworkHandler.getMessagesConfigurationPacket());
	}
}