package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.core.net.packet.PacketLogin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.network.UniversalPacket;
import turniplabs.halplibe.util.RecipeEntrypoint;

@Mixin(value = PacketHandlerClient.class,remap = false)
public abstract class PacketHandlerClientMixin {
    @Inject(method = "handleLogin", at = @At(value = "TAIL"))
    public void handleLogin(PacketLogin packet1login, CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::initNamespaces);
    }

    @Inject(method = "handleCustomPayload", at = @At(value = "TAIL"))
    public void handleCustomPayload(PacketCustomPayload customPayloadPacket, CallbackInfo ci) {
        if ("HALPLIBE".equals(customPayloadPacket.channel)) {
            new UniversalPacket(customPayloadPacket).handlePacket((PacketHandlerClient) ((Object) this));
        }
    }
}
