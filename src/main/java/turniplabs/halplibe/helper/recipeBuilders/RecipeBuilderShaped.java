package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShaped;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.RecipeHelper;

import java.util.Arrays;
import java.util.HashMap;

public class RecipeBuilderShaped extends RecipeBuilderBase{
    protected String[] shape; // Only used for shaped recipes
    protected int width;
    protected int height;
    protected boolean consumeContainer = false; // Only used for shapedRecipes
    protected final HashMap<Character, RecipeSymbol> symbolShapedMap = new HashMap<>();
    public RecipeBuilderShaped(String modID){
        super(modID);
    }
    public RecipeBuilderShaped(String modID, String... shape) {
        super(modID);
        setShapeLocal(shape);
    }
    public RecipeBuilderShaped setShape(String... shapeTemplate){
        RecipeBuilderShaped builder = this.clone(this);
        builder.setShapeLocal(shapeTemplate);
        return builder;
    }
    protected void setShapeLocal(String... shape){
        if (shape == null){
            throw new IllegalArgumentException("Shape Template cannot be set to null!");
        }
        if (shape.length == 0){
            throw new IllegalArgumentException("Shape Template cannot have a size of 0!");
        }
        if (shape.length > 3){
            throw new IllegalArgumentException("Shape Template height cannot exceed 3!\n" + Arrays.toString(shape));
        }
        if (shape[0].length() > 3){
            throw new IllegalArgumentException("Shape Template width cannot exceed 3!\n" + Arrays.toString(shape));
        }
        this.height = shape.length;
        this.width = shape[0].length();
        this.shape = shape;
    }
    public RecipeBuilderShaped setConsumeContainer(boolean consumeContainer){
        RecipeBuilderShaped builder = this.clone(this);
        builder.consumeContainer = consumeContainer;
        return builder;
    }
    public RecipeBuilderShaped addInput(char templateSymbol, IItemConvertible stack){
        return addInput(templateSymbol, stack, 0);
    }
    public RecipeBuilderShaped addInput(char templateSymbol, IItemConvertible stack, int meta){
        ItemStack _stack = stack.getDefaultStack();
        _stack.setMetadata(meta);
        return addInput(templateSymbol, _stack);
    }
    public RecipeBuilderShaped addInput(char templateSymbol, ItemStack stack){
        return addInput(templateSymbol, new RecipeSymbol(stack));
    }
    public RecipeBuilderShaped addInput(char templateSymbol, String itemGroup) {
        return addInput(templateSymbol, new RecipeSymbol(itemGroup));
    }
    public RecipeBuilderShaped addInput(char templateSymbol, RecipeSymbol symbol){
        RecipeBuilderShaped builder = this.clone(this);
        symbolShapedMap.put(templateSymbol, symbol);
        return builder;
    }
    public void create(String recipeID, ItemStack outputStack) {
        if (shape == null) throw new RuntimeException("Shaped recipe: " + recipeID + " attempted to build without a assigned shape!!");
        RecipeSymbol[] recipe = new RecipeSymbol[height * width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Character cha = null;
                if (shape[y].length() > x) {
                    cha = shape[y].charAt(x);
                }
                recipe[x + y * 3] = symbolShapedMap.get(cha);
            }
        }
        ((RecipeGroup<RecipeEntryCrafting<?, ?>>) RecipeBuilder.getRecipeGroup(modID, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack())))
                .register(recipeID, new RecipeEntryCraftingShaped(width, height, recipe, outputStack, consumeContainer));
    }
}
