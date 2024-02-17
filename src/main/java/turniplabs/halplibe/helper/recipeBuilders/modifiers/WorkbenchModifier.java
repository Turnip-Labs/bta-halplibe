package turniplabs.halplibe.helper.recipeBuilders.modifiers;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.IUnregister;

public class WorkbenchModifier {
    protected RecipeGroup<RecipeEntryCrafting<?, ?>> recipeGroup;
    public WorkbenchModifier(String namespace){
        recipeGroup = (RecipeGroup<RecipeEntryCrafting<?, ?>>) RecipeBuilder.getRecipeGroup(namespace, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack()));
    }
    public WorkbenchModifier removeRecipe(String recipeID){
        ((IUnregister<RecipeEntryCrafting<?, ?>>)recipeGroup).bta_halplibe$unregister(recipeID);
        return this;
    }
}
