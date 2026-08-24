package turniplabs.halplibe.mixin.deathcause;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.EntityPrimedTNT;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.*;
import net.minecraft.core.world.pos.TilePos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseGeneric;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;

import static turniplabs.halplibe.util.deathcause.DeathCauseEvents.PLAYER_DEATH_HANDLER;

@Mixin(value = Player.class)
public abstract class PlayerMixin extends MobMixin{

    @Inject(method = "getDeathMessageKey", at = @At("HEAD"))
    private void resolveDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
        Player asThis = (Player) (Object) this;
        PLAYER_DEATH_HANDLER.emit(func -> {
                    DeathCause deathCause = func.apply(asThis, entityKilledBy);
                    deathCause.bind(asThis);
                }
        );
        if(this.deathCause == null){
            this.deathCause = this.emulateVanillaBehavior(entityKilledBy);
        }
    }

    @Unique
    private DeathCause emulateVanillaBehavior(Entity entityKilledBy) {
        Player asThis = (Player) (Object) this;
        if (asThis.world.rand.nextInt(8000) == 666) {
            return new DeathCauseGeneric(asThis, "herobrine");
        }
        if (entityKilledBy == null) {
            if (asThis.isInLava()) {
                return new DeathCauseGeneric(asThis, "lava");
            }
            if (asThis.isInAcid()) {
                return new DeathCauseGeneric(asThis, "acid");
            }
            TilePos tilePos = new TilePos(asThis);
            if (asThis.world.getBlockType(tilePos) == Blocks.SPIKES) {
                return new DeathCauseGeneric(asThis, "spikes");
            }
            if (asThis.fallDistance > 0.0F) {
                return new DeathCauseGeneric(asThis, "fall");
            }
            if (asThis.airSupply <= 0) {
                return new DeathCauseGeneric(asThis, "drowned");
            }
            if (asThis.remainingFireTicks > 0) {
                return new DeathCauseGeneric(asThis, "fire");
            }
        } else {
            if (entityKilledBy instanceof EntityPrimedTNT) {
                return new DeathCauseGeneric(asThis, "tnt");
            }
            if (entityKilledBy instanceof EntityLightning) {
                return new DeathCauseGeneric(asThis, "lightning");
            }
            if (entityKilledBy instanceof Mob) {
                return new DeathCauseKilledBy(asThis, entityKilledBy);
            }
            if (entityKilledBy instanceof Projectile projectile) {
                return new DeathCauseProjectile(asThis, projectile);
            }
        }
        return new DeathCauseGeneric(asThis);
    }
}
