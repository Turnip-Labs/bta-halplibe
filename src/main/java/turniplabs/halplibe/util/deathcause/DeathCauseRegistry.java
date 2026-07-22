package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.data.registry.Registry;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeathCauseRegistry extends Registry<Supplier<DeathCause>> {
    private DeathCauseRegistry(){};

    private final Map<Class<? extends DeathCause>, String> classIDMap = new HashMap<>();

    private static final DeathCauseRegistry INSTANCE = new DeathCauseRegistry();

    public static DeathCauseRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(String key, Supplier<DeathCause> item) {
        try { NamespaceID.fromPool(key); }
        catch (HardIllegalArgumentException e) {
            throw new RuntimeException("Attempted to define invalid namespaced id for \"%s\"".formatted(key));
        }

        this.classIDMap.put(item.get().getClass(), key);
        super.register(key, item);
    }

    @Override
    public void unregister(String key) {
        var item = this.getItem(key);
        if (item != null) classIDMap.remove(item.get().getClass());

        super.unregister(key);
    }

    public String getKeyForClass(Class<? extends DeathCause> clazz) {
        return this.classIDMap.get(clazz);
    }

    static {
        var inst = getInstance();

        inst.register("halplibe:drowned", DeathCauseDrown::new);
        inst.register("halplibe:fall", DeathCauseFall::new);
        inst.register("halplibe:fire", DeathCauseFire::new);
        inst.register("halplibe:generic", DeathCauseGeneric::new);
        inst.register("halplibe:killed_by", DeathCauseKilledBy::new);
        inst.register("halplibe:projectile", DeathCauseProjectile::new);
        inst.register("halplibe:lava", DeathCauseLava::new);
        inst.register("halplibe:acid", DeathCauseAcid::new);
        inst.register("halplibe:spikes", DeathCauseSpikes::new);
        inst.register("halplibe:herobrine", DeathCauseHerobrine::new);
    }

    public static class DeathCauseDrown extends DeathCause {
        public DeathCauseDrown(Mob mob) { super(mob); }
        public DeathCauseDrown() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "drowned";
        }
    }

    public static class DeathCauseFall extends DeathCause {
        public DeathCauseFall(Mob mob) { super(mob); }
        public DeathCauseFall() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "fall";
        }
    }

    public static class DeathCauseFire extends DeathCause {
        public DeathCauseFire(Mob mob) { super(mob); }
        public DeathCauseFire() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "fire";
        }
    }

    public static class DeathCauseGeneric extends DeathCause {
        public DeathCauseGeneric(Mob mob) { super(mob); }
        public DeathCauseGeneric() { super(); }


        @Override
        protected String getTranslationKeyShard() {
            return "generic";
        }
    }

    public static class DeathCauseLava extends DeathCause {
        public DeathCauseLava(Mob mob) { super(mob); }
        public DeathCauseLava() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "lava";
        }
    }

    public static class DeathCauseAcid extends DeathCause {
        public DeathCauseAcid(Mob mob) { super(mob); }
        public DeathCauseAcid() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "acid";
        }
    }

    public static class DeathCauseSpikes extends DeathCause {
        public DeathCauseSpikes(Mob mob) { super(mob); }
        public DeathCauseSpikes() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "spikes";
        }
    }

    public static class DeathCauseHerobrine extends DeathCause {
        public DeathCauseHerobrine(Mob mob) { super(mob); }
        public DeathCauseHerobrine() { super(); }

        @Override
        protected String getTranslationKeyShard() {
            return "herobrine";
        }
    }
}
