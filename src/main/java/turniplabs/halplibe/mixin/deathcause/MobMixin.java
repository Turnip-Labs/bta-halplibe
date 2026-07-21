package turniplabs.halplibe.mixin.deathcause;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.deathcause.*;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;

@Mixin(Mob.class)
public class MobMixin implements DeathCauseMixinInterface {

    @Unique
    @Nullable DeathCause deathCause = null;

    @Definition(id = "world", field = "Lnet/minecraft/core/entity/Mob;world:Lnet/minecraft/core/world/World;")
    @Definition(id = "sendGlobalMessageTranslated", method = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V")
    @Expression("this.world.sendGlobalMessageTranslated(?, ?, ?)")
    @WrapOperation(method = "onDeath", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void hijackDeathMessage(
            World world,
            TextFormatting.Base format,
            String key,
            String[] args,
            Operation<Void> original,
            @Local(name = "entityKilledBy", type = Entity.class) Entity entityKilledBy
    ) {
        final var thisAs = (Mob) (Object) this;

        // just emulate default behaviour for compatibility's sake.
        if (this.deathCause == null) {

            if (entityKilledBy != null) {

                // the projectile death message only triggers if one or both of the entities is a player.
                if (entityKilledBy instanceof Projectile projectile) {
                    if (thisAs instanceof Player) {
                        this.deathCause = new DeathCauseProjectile((Player) thisAs, projectile);
                    }

                    // so if a skeleton shoots down your dog, just mark it as a skelie.
                    else this.deathCause = new DeathCauseKilledBy(thisAs, projectile.owner);
                }

                else this.deathCause = new DeathCauseKilledBy(thisAs, entityKilledBy);
            }

            else if (thisAs.isInLava()) {
                this.deathCause = new DeathCauseRegistry.DeathCauseLava(thisAs);
            }

            else {
                TilePos tilePos = new TilePos(thisAs);

                if (thisAs.world.getBlockType(tilePos) == Blocks.SPIKES) {
                    this.deathCause = new DeathCauseRegistry.DeathCauseSpikes(thisAs);
                }

                else if (thisAs.fallDistance > 0.0F) {
                    this.deathCause = new DeathCauseRegistry.DeathCauseFall(thisAs);
                }

                else if (thisAs.airSupply <= 0) {
                    this.deathCause = new DeathCauseRegistry.DeathCauseDrown(thisAs);
                }

                else if (thisAs.remainingFireTicks > 0) {
                    this.deathCause = new DeathCauseRegistry.DeathCauseFire(thisAs);
                }

                else {
                    this.deathCause = new DeathCauseRegistry.DeathCauseGeneric(thisAs);
                }
            }
        }

        if (thisAs instanceof Player && thisAs.world.rand.nextInt(8000) == 666) {
            this.deathCause = new DeathCauseRegistry.DeathCauseHerobrine(thisAs);
        }

        if (!EnvironmentHelper.isMultiplayerClient()) {
            DeathCauseNetworkMessage deathMessage = new DeathCauseNetworkMessage(this.deathCause);
            for (Player player : world.players) {
                if (NetworkHandler.canReceiveNativePackets(player)) {
                    NetworkHandler.sendToPlayer(player, deathMessage);
                } else {
                    player.sendMessageTranslated(format, key, args);
                }
            }
        }
    }

    @Override
    public void halplibe$setDeathCause(DeathCause deathCause) {
        this.deathCause = deathCause;
    }
}
