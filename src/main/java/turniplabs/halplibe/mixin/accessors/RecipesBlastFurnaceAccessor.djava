package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.crafting.recipe.RecipesBlastFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = RecipesBlastFurnace.class, remap = false)
public interface RecipesBlastFurnaceAccessor {

    @Accessor("smeltingList")
    void setSmeltingList(Map<?, ?> map);
}
