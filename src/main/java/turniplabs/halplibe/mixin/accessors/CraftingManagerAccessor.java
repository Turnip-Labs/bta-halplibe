package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = CraftingManager.class, remap = false)
public interface CraftingManagerAccessor {

    @Invoker("addRecipe")
    void callAddRecipe(ItemStack itemstack, Object[] aobj);

    @Invoker("addShapelessRecipe")
    void callAddShapelessRecipe(ItemStack itemstack, Object[] aobj);

    @Accessor("recipes")
    void setRecipes(List<IRecipe> list);
}
