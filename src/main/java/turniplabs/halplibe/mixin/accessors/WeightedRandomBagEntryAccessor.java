package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.WeightedRandomBag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedRandomBag.Entry.class)
public interface WeightedRandomBagEntryAccessor {
    @Accessor("weight")
    void setWeight(double weight);
}
