package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;

/**
 * <p><b>This Object represents a cause of death</b>. It is used to set custom death messages for players and entities.</p>
 * The purpose of this class is it to preserve localization over the network. Additionally {@link DeathCauseEvents}
 * offers a common entrypoint for all mods to use to resolve {@link Player} or {@link Mob} specific death messages
 *
 * <h5>The usage is as follows:</h3>
 * <ol>
 * <li>Create a custom {@link DeathCause}. Make sure all field nessesary are serializable
 * as they will be sent via the network by NBT (see {@link DeathCauseNetworkMessage}).
 * </li>
 *
 * <li>Serialize ({@code deserializeAdditional}) and deserialize ({@code serializeAdditional}) all the necessary fields you just defined in .</li>
 *
 * <li>You write a cute little function to output the translated death message using all fields previously acquired.</li>
 *
 * <li>Don't forget to register it to <code>DeathCauseRegistry</code>!</li>
 *
 * <li>To successfully send the {@link DeathCause} over the network bind before
 * the call {@link net.minecraft.core.world.World#sendGlobalMessageTranslated(String, String...)}
 * in {@link Mob#onDeath} to the victim entity via the <code>DeathCause.bind</code> method.
 * </li>
 *
 * <li>Profit!</li>
 *
 * </ul>
 *
 * <h6>- Kheppo, Redart</h4>
 */
public abstract class DeathCause {
    protected boolean isPlayer = false;
    protected EntityName victim = EntityName.empty();

    public DeathCause(){}

    public DeathCause(Mob victim) {
        this.isPlayer = victim instanceof Player;
        this.victim = EntityName.fromEntity(victim);
    }

    protected final void serialize(CompoundTag tag) {
        var victimTag = new CompoundTag();
        this.victim.serialize(victimTag);
        tag.put("halplibe:victim", victimTag);
        tag.putBoolean("halplibe:is_player", this.isPlayer);
        this.serializeAdditional(tag);
    }

    protected final void deserialize(CompoundTag tag) {
        this.victim = EntityName.deserialize(tag.getCompound("halplibe:victim"));
        this.isPlayer = tag.getBoolean("halplibe:is_player");
        this.deserializeAdditional(tag);
    }

    public final void sendMessage(Player player) {
        final String formatted = this.format(I18n.getInstance().translateKey(this.getQualifiedTranslationKey()));
        player.sendMessage(this.getTextFormattingBase(), formatted);
    }

    /**
     * Binds this {@link DeathCause} to the given mob so it can be sent over the network.
     *
     * <p>If the {@code PLAYER_DEATH_EVENT} is used, than the call is handled by
     * {@link turniplabs.halplibe.mixin.deathcause.PlayerMixin}/{@link turniplabs.halplibe.mixin.deathcause.MobMixin}. Otherwise, this method
     * must be called to set the death message.
     *
     * <p>Binding order matters. Binding a different {@link DeathCause} will overwrite
     * the currently bound {@link DeathCause}.
     *
     * <p>For more information, see
     * {@link turniplabs.halplibe.mixin.deathcause.MobMixin#halplibe$setDeathCause(DeathCause)}.
     *
     * @param victim the mob to which this death cause should be bound
     */
    public final void bind(Mob victim) {
        ((DeathCauseMixinInterface) victim).halplibe$setDeathCause(this);
    }


    /**
     * Returns the complete translation key.
     *
     * <p>Unless this method is overridden, the translation key will use the
     * {@code messages.death.} prefix. The client uses the returned translation key
     * to localize the {@link DeathCause}.
     *
     * @return the fully formatted translation key
     */
    public String getQualifiedTranslationKey() {
        return "messages.death.%s.%s".formatted(this.isPlayer ? "player" : "mob", this.getTranslationKeyShard());
    }

    /**
     * Returns the suffix used to complete the default translation key.
     *
     * <p>Unless this method overrides the default implementation of
     * {@link #getQualifiedTranslationKey()}, the returned value should follow the
     * format {@code MOD_ID.NAME}, for example {@code aether.poisoned} or
     * {@code paxels.paxel_ed}.
     *
     * @return the translation key suffix
     */
    protected abstract String getTranslationKeyShard();


    /**
     * Deserializes data received over the network to reconstruct a {@link DeathCause}.
     *
     * <p>Override this method to read any additional data required to reconstruct the
     * {@link DeathCause} on the client. Make sure that every field required on the
     * client is deserialized here.
     *
     * @param tag {@link CompoundTag} to read stored information from.
     */
    protected void deserializeAdditional(CompoundTag tag){}

    /**
     * Serializes additional data required to send a {@link DeathCause} over the network.
     *
     * <p>Override this method to store any additional data required to reconstruct the
     * {@link DeathCause} on the client. Make sure that every field required on the
     * client is serialized here.
     *
     * @param tag {@link CompoundTag} to store additional information in.
     */
    protected void serializeAdditional(CompoundTag tag){}

    /**
     * Formats the translated key.
     *
     * <p>Override this method for custom formatting behavior.
     *
     * @param translatedKey the fully translated key
     * @return the formatted translation key
     */
    protected String format(String translatedKey) {
        return translatedKey.formatted(TextFormatting.scoped(victim.getValue()));
    }

    /**
     * Returns the base formatting applied to death cause messages.
     *
     * <p>This formatting is prepended to every message sent by
     * {@link Player#sendMessage(String)}.
     *
     * @return the base {@link TextFormatting.Base} formatting
     */
    public TextFormatting.Base getTextFormattingBase() {
        return TextFormatting.Base.RED;
    }
}
