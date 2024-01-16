package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShapeless;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilderShapeless extends RecipeBuilderBase{
    private final List<RecipeSymbol> symbolShapelessList = new ArrayList<>();
    public RecipeBuilderShapeless(String modID) {
        super(modID);
    }
    public RecipeBuilderShapeless addInput(IItemConvertible stack){
        return addInput(stack, 0);
    }
    public RecipeBuilderShapeless addInput(IItemConvertible stack, int meta){
        ItemStack _stack = stack.getDefaultStack();
        _stack.setMetadata(meta);
        return addInput(_stack);
    }
    public RecipeBuilderShapeless addInput(String itemGroup){
        return addInput(new RecipeSymbol(itemGroup));
    }
    public RecipeBuilderShapeless addInput(ItemStack stack){
        return addInput(new RecipeSymbol(stack));
    }
    public RecipeBuilderShapeless addInput(RecipeSymbol symbol){
        RecipeBuilderShapeless builder = this.clone(this);
        symbolShapelessList.add(symbol);
        return builder;
    }
    @Override
    public void build(String recipeID, ItemStack outputStack) {
        ((RecipeGroup<RecipeEntryCrafting<?, ?>>) RecipeBuilder.getRecipeGroup(modID, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack())))
                .register(recipeID, new RecipeEntryCraftingShapeless(symbolShapelessList, outputStack));
    }
}
