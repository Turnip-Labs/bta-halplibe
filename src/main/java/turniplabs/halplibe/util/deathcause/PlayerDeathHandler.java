package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;

/**
 *Provides a common entry point for mods that want to define custom ways for a
 *player to die and provide a corresponding death message.
 *
 * <p>
 *     The {@code resolvePlayerDeathCause} method provides the information necessary
 *     to construct a {@link DeathCause} for most, if not all, player death scenarios.
 *     The resulting {@link DeathCause} is automatically bound to the player, so
 *     calling {@link DeathCause#bind(Mob)} is not necessary.
 *</p>
 *
 *<p>
 *     For more information on how the handler gets resolved see {@link turniplabs.halplibe.mixin.deathcause.PlayerMixin}.
 *</p
 *
 * <p>Implementation example:
 * <pre>{@code
 * public class MyPlayerDeathHandler implements PlayerDeathHandler {
 *
 *      @Override
 *      public DeathCause resolvePlayerDeathCause(
 *          Player player,
 *          Entity killedBy
 *      ) {
 *          return player.gotWizard()
 *                  ? new DeathCauseWizard (player, killedBy)
 *                  : new DeathCauseGeneric();
 *      }
 * }
 * </pre>
 *
 */
public interface PlayerDeathHandler {

    /**
     * Resolves the {@link DeathCause} for a player's death.
     *
     * @param player the player who died
     * @param killedBy the entity, if any, responsible for the player's death
     * @return the resolved death cause
     */
    DeathCause resolvePlayerDeathCause(Player player, Entity killedBy);
}
