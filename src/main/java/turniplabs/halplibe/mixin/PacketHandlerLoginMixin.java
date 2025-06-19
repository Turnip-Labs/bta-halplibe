package turniplabs.halplibe.mixin;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.PacketLogin;
import net.minecraft.core.net.packet.PacketPreLogin;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerLogin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = PacketHandlerLogin.class, remap = false)
public abstract class PacketHandlerLoginMixin {
	@Shadow
	public NetworkManager netManager;

	@Inject(method = "handleHandshake", at = @At(value = "HEAD"))
	public void sendMessagesConfiguration(PacketPreLogin preLoginPacket, CallbackInfo ci) {
		this.netManager.addToSendQueue(NetworkHandler.getMessagesConfigurationPacket());
	}
}
