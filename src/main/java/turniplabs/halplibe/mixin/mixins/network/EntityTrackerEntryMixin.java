package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.entity.animal.IAnimal;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.util.network.PacketExtendedMobSpawn;

@Mixin(value = EntityTrackerEntry.class, remap = false)
public class EntityTrackerEntryMixin {
    @Shadow public Entity trackedEntity;

    @Inject(method = "getSpawnPacket()Lnet/minecraft/core/net/packet/Packet;", at = @At(value = "HEAD"), cancellable = true)
    public void sendExtendedMobPacket(CallbackInfoReturnable<Packet> cir){
        if (trackedEntity instanceof IAnimal && EntityDispatcher.getEntityID(trackedEntity) > 255){
            cir.setReturnValue(new PacketExtendedMobSpawn((EntityLiving)trackedEntity));
        }
    }
}
