package turniplabs.halplibe.util.deathcause;

import net.minecraft.core.data.registry.Registry;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;

public class DeathCauseRegistry extends Registry<Class<? extends DeathCause>> {
    private DeathCauseRegistry(){};

    private static final DeathCauseRegistry INSTANCE = new DeathCauseRegistry();

    public static DeathCauseRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(String key, Class<? extends DeathCause> item) {
        try { NamespaceID.fromPool(key); }
        catch (HardIllegalArgumentException e) {
            throw new RuntimeException("Attempted to define invalid namespaced id for \"%s\"".formatted(item.getCanonicalName()));
        }
        super.register(key, item);
    }
}
