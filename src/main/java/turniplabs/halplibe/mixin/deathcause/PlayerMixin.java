package turniplabs.halplibe.mixin.deathcause;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.PlayerDeathHandler;

@Mixin(value = Player.class)
public abstract class PlayerMixin {

    @Inject(method = "getDeathMessageKey", at = @At("HEAD"))
    private void resolveDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir){
        Player asThis = (Player) (Object) this;
        FabricLoader.getInstance()
                .getEntrypointContainers("halplibe", PlayerDeathHandler.class)
                .forEach(plugin -> this.setDeathCause(plugin, asThis, entityKilledBy));
    }

    @Unique
    private void setDeathCause(EntrypointContainer<PlayerDeathHandler> plugin, Player player, Entity killer){
        PlayerDeathHandler deathCauseResolver = plugin.getEntrypoint();
        DeathCause deathCause = deathCauseResolver.resolvePlayerDeathCause(player, killer);
        deathCause.bind(player);
    }
}
