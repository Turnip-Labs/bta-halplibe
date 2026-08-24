package turniplabs.halplibe.mixin.deathcause;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.*;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.deathcause.*;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseGeneric;

import static turniplabs.halplibe.util.deathcause.DeathCauseEvents.MOB_DEATH_HANDLER;

@Mixin(Mob.class)
public abstract class MobMixin implements DeathCauseMixinInterface {

    @Unique
    protected @Nullable DeathCause deathCause = null;

    @Override
    public void halplibe$setDeathCause(DeathCause deathCause) {
        this.deathCause = deathCause;
    }

    @Inject(method = "getDeathMessageKey", at = @At("HEAD"))
    private void resolveDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
        Mob asThis = (Mob) (Object) this;
        MOB_DEATH_HANDLER.emit(func -> {
            DeathCause petDeathCause = func.apply(asThis, entityKilledBy);
            petDeathCause.bind(asThis);
        });
        if (this.deathCause == null) {
            this.deathCause = this.emulateVanillaBehaivor2(entityKilledBy);
        }
    }

    @Unique
    private DeathCause emulateVanillaBehaivor2(Entity entityKilledBy) {
        Mob asThis = (Mob) (Object) this;
        if (entityKilledBy != null) {
            return new DeathCauseKilledBy(asThis, entityKilledBy);
        }
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
        return new DeathCauseGeneric(asThis);
    }


    @Definition(id = "world", field = "Lnet/minecraft/core/entity/Mob;world:Lnet/minecraft/core/world/World;")
    @Definition(id = "sendGlobalMessageTranslated", method = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V")
    @Expression("this.world.sendGlobalMessageTranslated(?, ?, ?)")
    @WrapOperation(method = "onDeath", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void hijackDeathMessage(
            World world,
            TextFormatting.Base format,
            String key,
            String[] args,
            Operation<Void> original
    ) {
        assert deathCause != null;
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
}
