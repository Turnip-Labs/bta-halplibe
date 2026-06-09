package turniplabs.halplibe.util.deathcause.vanilla;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import turniplabs.halplibe.mixin.accessors.LanguageAccessor;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.EntityName;

public class DeathCauseProjectile extends DeathCause {

    private String projectileType = "";
    private boolean killedByPlayer = false;
    private boolean killedBySelf = false;

    private EntityName attacker = EntityName.empty();

    public DeathCauseProjectile() { super(); }

    public DeathCauseProjectile(Player player, Projectile projectile) {
        super(player);

        var projectileDispatcherId = projectile.getDispatcherId();
        this.projectileType = projectileDispatcherId == null ? "arrow" : projectileDispatcherId.value();

        this.killedByPlayer = projectile.owner instanceof Player;

        if (killedByPlayer) {
            this.attacker = EntityName.fromEntity(projectile.owner);
            this.killedBySelf = player.uuid.equals(((Player) projectile.owner).uuid);
        }
    }

    @Override
    public void serialize(CompoundTag tag) {
        super.serialize(tag);

        var attackerTag = new CompoundTag();
        attacker.serialize(attackerTag);

        tag.put("halplibe:attacker", attackerTag);
        tag.putString("halplibe:projectile_id", this.projectileType);
        tag.putBoolean("halplibe:killed_by_player", this.killedByPlayer);
        tag.putBoolean("halplibe:killed_by_self", this.killedBySelf);
    }

    @Override
    public void deserialize(CompoundTag tag) {
        super.deserialize(tag);

        this.attacker = EntityName.deserialize(tag.getCompound("halplibe:attacker"));
        this.projectileType = tag.getString("halplibe:projectile_id");
        this.killedByPlayer = tag.getBoolean("halplibe:killed_by_player");
        this.killedBySelf = tag.getBoolean("halplibe:killed_by_self");

    }

    @Override
    protected String getTranslationKeyShard() {
        return "";
    }

    @Override
    public String getQualifiedTranslationKey() {
        final String base = "messages.death.player.";

        final var language = (LanguageAccessor) I18n.getInstance().getCurrentLanguage();
        if (language != null) {
            final var entries = language.getEntries();

            final String keyBase = base + this.projectileType;
            final String keyWithSuffix = keyBase + (this.killedBySelf ? "_suicide" : this.killedByPlayer ? "_player" : "");

            if (entries.containsKey(keyWithSuffix)) {
                return keyWithSuffix;
            }

            if (entries.containsKey(keyBase)) {
                return keyBase;
            }
        }

        return base + "arrow";
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
