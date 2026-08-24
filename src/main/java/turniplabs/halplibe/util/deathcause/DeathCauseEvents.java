package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

import java.util.function.BiFunction;

public class DeathCauseEvents {
    private DeathCauseEvents(){}

    /**
     * Provides a common entry point for mods that want to define custom ways for a
     * player to die and provide a corresponding death message.
     *
     * <p>
     *     The resulting {@link DeathCause} is automatically bound to the player, so
     *     calling {@link DeathCause#bind(Mob)} is not necessary.
     *     For more information on how the handler gets resolved see {@link turniplabs.halplibe.mixin.deathcause.PlayerMixin}
     *     and {@link DeathCause}.
     *</p>
     * <p>Implementation example:
     * <pre>{@code
     * public class PlayerDeathHandler {
     *      private static boolean init = false;
     *
     *
     *      public static void init(){
     *          if(init){
     *              return;
     *          }
     *          init = true;
     *          MOB_DEATH_HANDLER.listen(
     *              Key.of(MOD_ID),
     *              PlayerDeathHandler::resolveMobDeathCause
     *          );
     *      }
     *
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
    public static final SortedBaseEvent<BiFunction<Player, Entity, DeathCause>> PLAYER_DEATH_HANDLER = new SortedBaseEvent<>();

    /**
     * Provides a common entry point for mods that want to define custom ways for non-player
     * to die and provide a corresponding death message. This is especially for those that want
     * custom pet death messages(yes you sick fucks).
     * <p>
     *      The resulting {@link DeathCause} is automatically bound to the player, so
     *      calling {@link DeathCause#bind(Mob)} is not necessary.
     *      For more information on how the handler gets resolved see {@link turniplabs.halplibe.mixin.deathcause.MobMixin}
     *      and {@link DeathCause}.
     * </p>
     *
     * <p>Implementation example:
     * <pre>{@code
     * public class MobDeathHandler {
     *      private static boolean init = false;
     *
     *      public static void init(){
     *          if(init){
     *              return;
     *          }
     *          init = true;
     *          MOB_DEATH_HANDLER.listen(
     *              Key.of(MOD_ID),
     *              MobDeathHandler::resolveMobDeathCause
     *          );
     *      }
     *
     *      public static DeathCause resolveMobDeathCause(
     *          Mob player,
     *          Entity killedBy
     *      ) {
     *          return (killerBy instanceof Player player && player.isEvil())
     *                  ? new DeathCausePeta (player, killedBy)
     *                  : new DeathCauseGeneric();
     *      }
     * }
     * </pre>
     */
    public static final SortedBaseEvent<BiFunction<Mob, Entity, DeathCause>> MOB_DEATH_HANDLER = new SortedBaseEvent<>();
}
