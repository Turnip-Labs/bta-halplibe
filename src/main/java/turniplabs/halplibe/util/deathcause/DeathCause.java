package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.lang.I18n;

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
    public DeathCause() {};

    public abstract String format(I18n i18n);
    public abstract void serialize(CompoundTag tag);
    public abstract void deserialize(CompoundTag tag);

    public void bind(Mob victim) {
        ((DeathCauseMixinInterface) victim).halplibe$setDeathCause(this);
    }
}
