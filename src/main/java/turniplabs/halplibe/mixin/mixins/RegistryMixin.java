package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.data.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.util.IUnregister;

import java.util.List;
import java.util.Map;

@Mixin(value = Registry.class, remap = false)
public abstract class RegistryMixin<T> implements IUnregister<T> {
    @Shadow @Final private Map<String, T> keyItemMap;

    @Shadow public abstract T getItem(String key);

    @Shadow @Final private List<T> items;

    @Shadow @Final private Map<T, String> itemKeyMap;

    @Unique
    public void unregister(String key){
        T object = getItem(key);
        items.remove(object);
        keyItemMap.remove(key);
        itemKeyMap.remove(object);
    }
    @Unique
    public void unregister(T object){
        unregister(itemKeyMap.get(object));
    }
}
