package turniplabs.halplibe.helper;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShapeless;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RecipeBuilder {
    @Nonnull
    public static RecipeNamespace getRecipeNamespace(String modID){
        if (Registries.RECIPES.getItem(modID) != null){
            return Registries.RECIPES.getItem(modID);
        }
        RecipeNamespace modSpace = new RecipeNamespace();
        Registries.RECIPES.register(modID, modSpace);
        return Objects.requireNonNull(modSpace);
    }
    @Nonnull
    public static RecipeGroup<?> getRecipeGroup(String modID, String key, RecipeSymbol symbol){
        return getRecipeGroup(getRecipeNamespace(modID), key, symbol);
    }
    @Nonnull
    public static RecipeGroup<?> getRecipeGroup(RecipeNamespace namespace, String key, RecipeSymbol symbol){
        if (namespace.getItem(key) != null){
            return namespace.getItem(key);
        }
        RecipeGroup<?> group = new RecipeGroup<>(symbol);
        namespace.register(key, group);
        return Objects.requireNonNull(group);
    }
    public static RecipeBuilderShaped Shaped(String modID){
        return new RecipeBuilderShaped(modID);
    }
    public static RecipeBuilderShaped Shaped(String modID, String... shape){
        return new RecipeBuilderShaped(modID, shape);
    }
    public static RecipeBuilderShapeless Shapeless(String modID){
        return new RecipeBuilderShapeless(modID);
    }
    public static RecipeBuilderFurnace Furnace(String modID){
        return new RecipeBuilderFurnace(modID);
    }
}
