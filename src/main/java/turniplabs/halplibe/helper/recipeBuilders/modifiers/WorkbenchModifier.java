package turniplabs.halplibe.helper.recipeBuilders.modifiers;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.Objects;

public class WorkbenchModifier {
    protected RecipeGroup<RecipeEntryCrafting<?, ?>> recipeGroup;
    public WorkbenchModifier(String namespace, String key){
        recipeGroup = (RecipeGroup<RecipeEntryCrafting<?, ?>>) RecipeBuilder.getRecipeGroup(namespace, key, new RecipeSymbol(Block.workbench.getDefaultStack()));
    }
}
