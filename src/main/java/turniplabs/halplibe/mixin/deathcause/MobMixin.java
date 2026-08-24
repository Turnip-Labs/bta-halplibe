package turniplabs.halplibe.mixin.deathcause;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.EntityPrimedTNT;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.deathcause.*;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseGeneric;

@Mixin(Mob.class)
public abstract class MobMixin implements DeathCauseMixinInterface {

    @Unique
    @Nullable DeathCause deathCause = null;

    @Override
    public void halplibe$setDeathCause(DeathCause deathCause) {
        this.deathCause = deathCause;
    }

    @Inject(method = "getDeathMessageKey", at = @At("HEAD"))
    private void resolveDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir){
        Mob asThis = (Mob) (Object) this;
        if(!asThis.sendsDeathMessage(entityKilledBy)){
            return;
        }
        FabricLoader.getInstance()
                .getEntrypointContainers("halplibe", MobDeathHandler.class)
                .forEach(plugin -> this.setDeathCause(plugin, asThis, entityKilledBy));
    }

    @Unique
    private void setDeathCause(EntrypointContainer<MobDeathHandler> plugin, Mob mob, Entity killer){
        MobDeathHandler deathCauseResolver = plugin.getEntrypoint();
        DeathCause petDeathCause = deathCauseResolver.resolveMobDeathCause(mob, killer);
        petDeathCause.bind(mob);
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
            Operation<Void> original,
            @Local(name = "entityKilledBy", type = Entity.class) Entity entityKilledBy
    ) {
        // just emulate default behaviour for compatibility's sake.
        if (this.deathCause == null) {
            this.deathCause = emulateVanillaBehaivor(entityKilledBy);
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

    @Unique
    private @NotNull DeathCause emulateVanillaBehaivor(Entity entityKilledBy) {
        final var thisAs = (Mob) (Object) this;
        if (thisAs instanceof Player && thisAs.world.rand.nextInt(8000) == 666) {
            return new DeathCauseGeneric(thisAs, "herobrine");
        }
        if (entityKilledBy == null) {
            if (thisAs.isInLava()) {
                return new DeathCauseGeneric(thisAs, "lava");
            }
            if (thisAs.isInAcid()) {
                return new DeathCauseGeneric(thisAs, "acid");
            }
            TilePos tilePos = new TilePos(thisAs);
            if (thisAs.world.getBlockType(tilePos) == Blocks.SPIKES) {
                return new DeathCauseGeneric(thisAs, "spikes");
            }
            if (thisAs.fallDistance > 0.0F) {
                return new DeathCauseGeneric(thisAs, "fall");
            }
            if (thisAs.airSupply <= 0) {
                return new DeathCauseGeneric(thisAs, "drowned");
            }
            if (thisAs.remainingFireTicks > 0) {
                return new DeathCauseGeneric(thisAs, "fire");
            }
        } else {
            if(!(thisAs instanceof Player)){
                return new DeathCauseKilledBy(thisAs, entityKilledBy);
            }
            if (entityKilledBy instanceof EntityPrimedTNT) {
                return new DeathCauseGeneric(thisAs, "tnt");
            }
            if (entityKilledBy instanceof EntityLightning) {
                return new DeathCauseGeneric(thisAs, "lightning");
            }
            if (entityKilledBy instanceof Mob) {
                return new DeathCauseKilledBy(thisAs, entityKilledBy);
            }
            if (entityKilledBy instanceof Projectile projectile) {
                if (thisAs instanceof Player player) {
                    return new DeathCauseProjectile(player, projectile);
                }
                return new DeathCauseKilledBy(thisAs, projectile.owner);
            }
            return new DeathCauseGeneric(thisAs);
        }
        return new DeathCauseGeneric(thisAs);
    }
}
