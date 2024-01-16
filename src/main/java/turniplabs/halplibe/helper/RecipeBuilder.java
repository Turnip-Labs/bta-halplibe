package turniplabs.halplibe.helper;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBlastFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShapeless;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderTrommel;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.BlastFurnaceModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.FurnaceModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.TrommelModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.WorkbenchModifier;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class RecipeBuilder {
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
    public static RecipeBuilderBlastFurnace BlastFurnace(String modID){
        return new RecipeBuilderBlastFurnace(modID);
    }
    public static RecipeBuilderTrommel Trommel(String modID){
        return new RecipeBuilderTrommel(modID);
    }
    public static TrommelModifier ModifyTrommel(String namespace, String key){
        return new TrommelModifier(namespace, key);
    }
    public static WorkbenchModifier ModifyWorkbench(String namespace){
        return new WorkbenchModifier(namespace);
    }
    public static FurnaceModifier ModifyFurnace(String namespace){
        return new FurnaceModifier(namespace);
    }
    public static BlastFurnaceModifier ModifyBlastFurnace(String namespace){
        return new BlastFurnaceModifier(namespace);
    }
}
