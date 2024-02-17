package turniplabs.halplibe.helper.recipeBuilders.modifiers;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryBlastFurnace;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.IUnregister;

public class BlastFurnaceModifier {
    protected RecipeGroup<RecipeEntryBlastFurnace> recipeGroup;
    @SuppressWarnings("unchecked")
    public BlastFurnaceModifier(String namespace){
        recipeGroup = (RecipeGroup<RecipeEntryBlastFurnace>) RecipeBuilder.getRecipeGroup(namespace, "blast_furnace", new RecipeSymbol(Block.furnaceBlastActive.getDefaultStack()));
    }
    @SuppressWarnings({"unchecked", "unused"})
    public BlastFurnaceModifier removeRecipe(String recipeID){
        ((IUnregister<RecipeEntryBlastFurnace>)recipeGroup).unregister(recipeID);
        return this;
    }
}
