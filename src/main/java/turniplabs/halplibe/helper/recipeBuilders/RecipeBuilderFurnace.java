package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.Objects;

public class RecipeBuilderFurnace extends RecipeBuilderBase{
    protected RecipeSymbol input;
    public RecipeBuilderFurnace(String modID) {
        super(modID);
    }
    public RecipeBuilderFurnace setInput(IItemConvertible item){
        return setInput(item, 0);
    }
    public RecipeBuilderFurnace setInput(IItemConvertible item, int meta){
        return setInput(new ItemStack(item, 1, meta));
    }
    public RecipeBuilderFurnace setInput(ItemStack input){
        return setInput(new RecipeSymbol(input));
    }
    public RecipeBuilderFurnace setInput(String itemGroup){
        return setInput(new RecipeSymbol(itemGroup));
    }
    public RecipeBuilderFurnace setInput(RecipeSymbol input){
        RecipeBuilderFurnace builder = this.clone(this);
        builder.input = Objects.requireNonNull(input, "Input symbol must not be null!");
        return builder;
    }

    @Override
    public void create(String recipeID, ItemStack outputStack) {
        Objects.requireNonNull(input, "Input symbol must not be null!");
        ((RecipeGroup<RecipeEntryFurnace>) RecipeBuilder.getRecipeGroup(modID, "furnace", new RecipeSymbol(Block.furnaceStoneActive.getDefaultStack())))
                .register(recipeID, new RecipeEntryFurnace(input, outputStack));
    }
}
