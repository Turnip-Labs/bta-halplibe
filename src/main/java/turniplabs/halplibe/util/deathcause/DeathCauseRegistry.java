package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.data.registry.Registry;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseGeneric;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeathCauseRegistry extends Registry<Supplier<DeathCause>> {
    private static final DeathCauseRegistry INSTANCE;
    private final Map<Class<? extends DeathCause>, String> classIDMap = new HashMap<>();

    static {
        INSTANCE = new DeathCauseRegistry();
        INSTANCE.register("halplibe", "killed_by", DeathCauseKilledBy::new);
        INSTANCE.register("halplibe", "projectile", DeathCauseProjectile::new);
        INSTANCE.register("halplibe", "generic", DeathCauseGeneric::new);

    }

    private DeathCauseRegistry(){}
    public static DeathCauseRegistry getInstance() {
        return INSTANCE;
    }


    public void register(String namespace, String key, Supplier<DeathCause> item) {
        NamespaceID namespaceID = NamespaceID.fromPool(namespace, key);
        this.classIDMap.put(item.get().getClass(), namespaceID.toString());
        super.register(namespaceID.toString(), item);
    }

    @Override
    public void register(String key, Supplier<DeathCause> item) {
        try {
            NamespaceID.fromPool(key);
        }
        catch (HardIllegalArgumentException e) {
            throw new RuntimeException("Attempted to define invalid namespaced id for \"%s\"".formatted(key));
        }
        this.classIDMap.put(item.get().getClass(), key);
        super.register(key, item);
    }

    @Override
    public @NotNull Supplier<DeathCause> getItem(@Nullable String key) {
        if(key == null){
            return DeathCauseGeneric::new;
        }
        Supplier<DeathCause> supplier = super.getItem(key);
        return supplier == null ? DeathCauseGeneric::new : supplier;
    }

    @Override
    public void unregister(String key) {
        var item = this.getItem(key);
        classIDMap.remove(item.get().getClass());
        super.unregister(key);
    }

    public String getKeyForClass(Class<? extends DeathCause> clazz) {
        return this.classIDMap.get(clazz);
    }

}
