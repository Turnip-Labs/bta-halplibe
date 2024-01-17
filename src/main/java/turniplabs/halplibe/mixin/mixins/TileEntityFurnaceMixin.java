package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = TileEntityFurnace.class, remap = false)
public class TileEntityFurnaceMixin {
    @Shadow protected ItemStack[] furnaceItemStacks;
    @Redirect(method = "canSmelt()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/data/registry/recipe/RecipeRegistry;getGroupFromKey(Ljava/lang/String;)Lnet/minecraft/core/data/registry/recipe/RecipeGroup;"))
    private RecipeGroup canSmeltFake(RecipeRegistry instance, String e){
        RecipeGroup group = groupEmulator();
        if (group != null) return groupEmulator();
        return instance.getGroupFromKey(e);
    }
    @Redirect(method = "smeltItem()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/data/registry/recipe/RecipeRegistry;getGroupFromKey(Ljava/lang/String;)Lnet/minecraft/core/data/registry/recipe/RecipeGroup;"))
    private RecipeGroup doSmeltFake(RecipeRegistry instance, String e){
        RecipeGroup group = groupEmulator();
        if (group != null) return groupEmulator();
        return instance.getGroupFromKey(e);
    }

    @Unique
    private RecipeGroup<?> groupEmulator(){
        RecipeGroup group = new RecipeGroup<>(null);
        RecipeEntryFurnace entryFurnace = getMatchingRecipe();
        if (entryFurnace == null) return null;
        group.register("don'tCareLol", getMatchingRecipe());
        return group;
    }
    @Unique
    private RecipeEntryFurnace getMatchingRecipe(){
        List<RecipeEntryFurnace> recipes = Registries.RECIPES.getAllFurnaceRecipes();
        for (RecipeEntryFurnace recipeEntryFurnace : recipes){
            if (recipeEntryFurnace.matches(furnaceItemStacks[0])){
                return recipeEntryFurnace;
            }
        }
        return null;
    }
}
