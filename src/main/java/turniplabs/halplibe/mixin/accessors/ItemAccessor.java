package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Item.class,remap = false)
public interface ItemAccessor {

    @Accessor("key")
    void setKey(String key);

}
