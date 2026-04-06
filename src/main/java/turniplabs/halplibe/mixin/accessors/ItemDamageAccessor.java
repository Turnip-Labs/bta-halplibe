package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Item.class)
public interface ItemDamageAccessor {
    @Invoker
    Item callSetMaxDamage(int i);
}
