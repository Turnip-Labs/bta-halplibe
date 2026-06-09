package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import turniplabs.halplibe.mixin.accessors.LanguageAccessor;

public class DeathCauseKilledBy extends DeathCause {

    private EntityName attacker = EntityName.empty();
    private String attackerMobId = "";

    public DeathCauseKilledBy() { super(); }
    public DeathCauseKilledBy(Mob victim, Entity attacker) {
        super(victim);

        this.attacker = EntityName.fromEntity(attacker);
        var dispatcherID = attacker.getDispatcherId();
        this.attackerMobId = dispatcherID != null ? dispatcherID.value() : "";
    }

    @Override
    public void serialize(CompoundTag tag) {
        super.serialize(tag);

        var attackerTag = new CompoundTag();
        attacker.serialize(attackerTag);

        tag.put("halplibe:attacker", attackerTag);
        tag.putString("halplibe:attacker_mob_id", this.attackerMobId);
    }

    @Override
    public void deserialize(CompoundTag tag) {
        super.deserialize(tag);

        this.attacker = EntityName.deserialize(tag.getCompound("halplibe:attacker"));
        this.attackerMobId = tag.getString("halplibe:attacker_mob_id");
    }

    @Override
    protected String getTranslationKeyShard() {
        return "killed_by";
    }

    @Override
    public String getQualifiedTranslationKey() {
        final String original = super.getQualifiedTranslationKey();

        // This *jank* is needed because mod ids aren't totally consistent.
        var modified = original + "." + switch (this.attackerMobId) {
            case "zombie_pigman" -> "zombie_pig";
            case "zombie_armored" -> "zombie";

            default -> this.attackerMobId;
        };

        final var language = (LanguageAccessor) I18n.getInstance().getCurrentLanguage();
        if (language != null && language.getEntries().containsKey(modified)) {
            return modified;
        }

        return original;
    }

    @Override
    protected String format(String translatedKey) {
        final int fieldCount = translatedKey.split("%s", -1).length-1;

        if (fieldCount == 0) {
            return translatedKey;
        }

        if (fieldCount == 1) {
            return translatedKey.formatted(TextFormatting.scoped(this.victim.getValue()));
        }

        return translatedKey.formatted(
            TextFormatting.scoped(this.victim.getValue()),
            TextFormatting.scoped(this.attacker.getValue())
        );
    }
}