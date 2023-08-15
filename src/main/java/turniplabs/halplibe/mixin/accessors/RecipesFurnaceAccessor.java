package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.crafting.recipe.RecipesFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = RecipesFurnace.class, remap = false)
public interface RecipesFurnaceAccessor {

    @Accessor("smeltingList")
    void setSmeltingList(Map<?, ?> map);
}
