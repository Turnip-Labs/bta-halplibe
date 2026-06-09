package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;

/**
 * <p><b>This Object represents a cause of death</b>. It is used to set custom death messages for players and entities.</p>
 *
 * <h3>The usage is as follows:</h3>
 * <ol>
 *
 * <li>
 *     You create a custom constructor that acquires all fields from the entity you just attacked.
 * It is very important that all fields are serializable as they will be sent via the network by NBT.
 * </li>
 * <li>Write serialization and deserialization functions for the fields you just defined.</li>
 *
 * <li>You write a cute little function to output the translated death message using all fields previously acquired.</li>
 *
 * <li>Don't forget to register it to <code>DeathCauseRegistry</code>!</li>
 *
 * <li>To display the message: bind it to the victim entity via the <code>DeathCause.bind</code> method. </li>
 *
 * <li>Profit!</li>
 *
 * </ul>
 *
 * <h4>- Kheppo</h4>
 */
public abstract class DeathCause {

    protected boolean isPlayer = false;
    protected EntityName victim = EntityName.empty();

    public DeathCause() {};

    public DeathCause(Mob victim) {
        this.isPlayer = victim instanceof Player;
        this.victim = EntityName.fromEntity(victim);
    };

    public void serialize(CompoundTag tag) {
        var victimTag = new CompoundTag();
        this.victim.serialize(victimTag);

        tag.put("halplibe:victim", victimTag);
        tag.putBoolean("halplibe:is_player", this.isPlayer);
    }

    public void deserialize(CompoundTag tag) {
        this.victim = EntityName.deserialize(tag.getCompound("halplibe:victim"));
        this.isPlayer = tag.getBoolean("halplibe:is_player");
    }

    /**
     * This method binds the death cause to the victim. See: class doc.
     */
    public void bind(Mob victim) {
        ((DeathCauseMixinInterface) victim).halplibe$setDeathCause(this);
    }

    public String getQualifiedTranslationKey() {
        return "messages.death.%s.%s".formatted(this.isPlayer ? "player" : "mob", this.getTranslationKeyShard());
    }

    /**
     * This method should return your death message.
     * It should be formatted as `{MOD_ID}.{NAME}`, so: `aether.poisoned` or `paxels.paxel_ed`
     */
    protected abstract String getTranslationKeyShard();

    /**
     * Override this method for custom formating behaviour.
     */
    protected String format(String translatedKey) {
        return translatedKey.formatted(TextFormatting.scoped(victim.getValue()));
    }

    public TextFormatting.Base getTextFormattingBase() {
        return TextFormatting.Base.RED;
    }

    public void sendMessage(Player player) {
        final String formatted = this.format(I18n.getInstance().translateKey(this.getQualifiedTranslationKey()));
        player.sendMessage(this.getTextFormattingBase(), formatted);
    }
}
