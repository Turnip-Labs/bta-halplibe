package turniplabs.halplibe.mixin.deathcause;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.core.Global;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.DeathCauseMixinInterface;
import turniplabs.halplibe.util.deathcause.DeathCauseNetworkMessage;

@Mixin(Mob.class)
public class MobMixin implements DeathCauseMixinInterface {

    @Unique
    @Nullable DeathCause deathCause = null;

    @Definition(id = "world", field = "Lnet/minecraft/core/entity/Mob;world:Lnet/minecraft/core/world/World;")
    @Definition(id = "sendGlobalMessageTranslated", method = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V")
    @Expression("this.world.sendGlobalMessageTranslated(?, ?, ?)")
    @WrapOperation(method = "onDeath", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void hijackDeathMessage(World world, TextFormatting.Base format, String key, String[] args, Operation<Void> original) {
        if (this.deathCause == null) {
            original.call(world, format, key, args);
            return;
        }

        if (!EnvironmentHelper.isClientWorld()) {
            NetworkHandler.sendToAllPlayers(new DeathCauseNetworkMessage(this.deathCause));
        }
    }

    @Override
    public void halplibe$setDeathCause(DeathCause deathCause) {
        this.deathCause = deathCause;
    }
}
