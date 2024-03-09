package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.packet.Packet24MobSpawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Packet24MobSpawn.class, remap = false)
public class PacketMobSpawnMixin {
    @Shadow public short type;

    @Inject(method = "<init>(Lnet/minecraft/core/entity/EntityLiving;)V", at = @At("TAIL"))
    private void fixTypeValue(EntityLiving entityliving, CallbackInfo ci){
        this.type = (short) EntityDispatcher.getEntityID(entityliving);
    }
}
