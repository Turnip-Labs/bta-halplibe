package turniplabs.halplibe.mixin.mixins.network;

import com.b100.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.packet.Packet24MobSpawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.network.PacketExtendedMobSpawn;

import java.util.List;

@Mixin(value = NetClientHandler.class, remap = false)
public class NetClientHandlerMixin {
    @Shadow private WorldClient worldClient;

    @Shadow private Minecraft mc;

    @Inject(method = "handleMobSpawn(Lnet/minecraft/core/net/packet/Packet24MobSpawn;)V",
            at = @At(value = "HEAD", target = "Lnet/minecraft/core/entity/EntityDispatcher;createEntity(ILnet/minecraft/core/world/World;)Lnet/minecraft/core/entity/Entity;"),
            cancellable = true)
    public void createEntity(Packet24MobSpawn packet24mobspawn, CallbackInfo ci){
        if (packet24mobspawn instanceof PacketExtendedMobSpawn){
            PacketExtendedMobSpawn extendedMobSpawn = (PacketExtendedMobSpawn)packet24mobspawn;
            double d = (double)extendedMobSpawn.xPosition / 32.0;
            double d1 = (double)extendedMobSpawn.yPosition / 32.0;
            double d2 = (double)extendedMobSpawn.zPosition / 32.0;
            float f = (float)(extendedMobSpawn.yaw * 360) / 256.0f;
            float f1 = (float)(extendedMobSpawn.pitch * 360) / 256.0f;
            EntityLiving entityliving = (EntityLiving) EntityDispatcher.createEntity(extendedMobSpawn.type, this.mc.theWorld);
            entityliving.serverPosX = extendedMobSpawn.xPosition;
            entityliving.serverPosY = extendedMobSpawn.yPosition;
            entityliving.serverPosZ = extendedMobSpawn.zPosition;
            entityliving.id = extendedMobSpawn.entityId;
            entityliving.absMoveTo(d, d1, d2, f, f1);
            entityliving.isMultiplayerEntity = true;
            this.worldClient.func_712_a(extendedMobSpawn.entityId, entityliving);
            List list = extendedMobSpawn.getMetadata();
            if (list != null) {
                entityliving.getEntityData().assignValues(list);
            }
            entityliving.nickname = StringUtils.substring(extendedMobSpawn.nickname, 0, 32);
            entityliving.chatColor = extendedMobSpawn.chatColor;
            ci.cancel();
        }
    }
}
