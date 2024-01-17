package turniplabs.halplibe.util;

import org.spongepowered.asm.mixin.Unique;

public interface IUnregister<T> {
    void unregister(String key);
    void unregister(T object);
}
