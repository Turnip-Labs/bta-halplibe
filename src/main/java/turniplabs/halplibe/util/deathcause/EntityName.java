package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;

public sealed abstract class EntityName {

    public static EntityName empty() {
        return new EntityNameLiteral("");
    }

    public static EntityName fromEntity(Entity entity) {
        return fromEntity(entity, true);
    }

    public static EntityName fromEntity(Entity entity, boolean useNickname) {
        if (entity instanceof Mob mob && useNickname && !mob.nickname.isEmpty()) {
            return new EntityNameLiteral(mob.nickname);
        }

        if (entity instanceof Player player) {
            return new EntityNameLiteral(player.getDisplayName());
        }

        var entry = EntityDispatcher.getInstance().entryForClass(entity.getClass());
        if (entry != null) {
            if (entry.nameKey != null) return new EntityNameTranslatable(entry.nameKey);
            return new EntityNameLiteral(entry.namespaceID.value());
        }

        return new EntityNameLiteral(entity.getClass().getSimpleName());
    }

    public abstract String getValue();
    protected abstract String getStringRep();

    public void serialize(CompoundTag tag) {
        if (this instanceof EntityNameLiteral) {
            tag.putInt("type", EntityNameLiteral.TYPE);
        }

        else if (this instanceof EntityNameTranslatable) {
            tag.putInt("type", EntityNameTranslatable.TYPE);
        }

        tag.putString("value", this.getStringRep());
    }

    public static EntityName deserialize(CompoundTag tag) {
        var type = tag.getInteger("type");
        var value = tag.getString("value");

        if (type == EntityNameLiteral.TYPE) {
            return new EntityNameLiteral(value);
        }

        if (type == EntityNameTranslatable.TYPE) {
            return new EntityNameTranslatable(value);
        }

        throw new RuntimeException("Invalid type.");
    }

    public static final class EntityNameLiteral extends EntityName {
        private static final int TYPE = 0;

        private final @NotNull String name;

        private EntityNameLiteral(@NotNull String name) {
            this.name = name;
        }

        @Override
        public String getValue() {
            return name;
        }

        @Override
        protected String getStringRep() {
            return name;
        }
    }

    public static final class EntityNameTranslatable extends EntityName {
        private static final int TYPE = 1;

        private final @NotNull String key;

        private EntityNameTranslatable(@NotNull String key) {
            this.key = key;
        }

        @Override
        public String getValue() {
            return I18n.getInstance().translateKey(this.key);
        }

        @Override
        protected String getStringRep() {
            return key;
        }
    }

}
