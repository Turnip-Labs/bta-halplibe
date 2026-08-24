package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;

/**
 * Provides a common entry point for mods that want to define custom ways for non-player
 * to die and provide a corresponding death message. This is especially for those that want
 * custom pet death messages(yes you sick fucks).
 *
 * <p>
 *      The {@code resolveMobDeathCause} method provides the information necessary to
 *      construct a {@link DeathCause} for most, if not all, mob death scenarios.
 *
 * <p>
 *     ⚠️ Importantly messages will only be bound when the mob is nicknamed.
 *     As such binding {@link DeathCause} is ill-advised.
 *     This handler cannot be used to resolve player death messages, since the corresponding
 *     function is only called for {@code Mob} entities.
 * </p>
 *
 * <p>
 *     For more information on how the handler gets resolved see {@link turniplabs.halplibe.mixin.deathcause.MobMixin}.
 * </p
 *
 * <p>Implementation example:
 * <pre>{@code
 * public class MyMobDeathHandler implements MobDeathHandler {
 *
 *      @Override
 *      public DeathCause resolveMobDeathCause(
 *          Mob player,
 *          Entity killedBy
 *      ) {
 *          return (killerBy instanceof Player player && player.isEvil())
 *                  ? new DeathCausePeta (player, killedBy)
 *                  : new DeathCauseGeneric();
 *      }
 * }
 * </pre>
 *
 */
public interface MobDeathHandler {

    /**
     * Resolves the {@link DeathCause} for a mob death.
     *
     * @param mob the mob or pet that has died
     * @param killedBy the entity, if any, responsible for the mob's death
     * @return the resolved death cause
     */
    DeathCause resolveMobDeathCause(Mob mob, Entity killedBy);
}
