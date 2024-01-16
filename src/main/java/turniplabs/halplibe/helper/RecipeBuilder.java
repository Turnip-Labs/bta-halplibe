package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShaped;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShapeless;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RecipeBuilder implements Cloneable {
    private RecipeType recipeType;
    private String[] shape; // Only used for shaped recipes
    private int width;
    private int height;
    private boolean consumeContainer = false; // Only used for shapedRecipes
    private final HashMap<Character, RecipeSymbol> symbolShapedMap = new HashMap<>();
    private final List<RecipeSymbol> symbolShapelessList = new ArrayList<>();
    private String modID;
    public RecipeBuilder(String modID){
        this.modID = modID;
    }
    @Override
    public RecipeBuilder clone() {
        try {
            // none of the fields are mutated so this should be fine
            return (RecipeBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public RecipeBuilder setShaped(String... shapeTemplate){
        if (shapeTemplate == null){
            throw new IllegalArgumentException("Shape Template cannot be set to null!");
        }
        if (shapeTemplate.length == 0){
            throw new IllegalArgumentException("Shape Template cannot have a size of 0!");
        }
        if (shapeTemplate.length > 3){
            throw new IllegalArgumentException("Shape Template height cannot exceed 3!\n" + Arrays.toString(shapeTemplate));
        }
        if (shapeTemplate[0].length() > 3){
            throw new IllegalArgumentException("Shape Template width cannot exceed 3!\n" + Arrays.toString(shapeTemplate));
        }
        RecipeBuilder builder = this.clone();
        builder.height = shapeTemplate.length;
        builder.width = shapeTemplate[0].length();
        builder.recipeType = RecipeType.SHAPED;
        builder.shape = shapeTemplate;
        return builder;
    }
    public RecipeBuilder setConsumeContainer(boolean consumeContainer){
        RecipeBuilder builder = this.clone();
        builder.consumeContainer = consumeContainer;
        return builder;
    }
    public RecipeBuilder addShapedInput(char templateSymbol, IItemConvertible stack){
        return addShapedInput(templateSymbol, stack, 0);
    }
    public RecipeBuilder addShapedInput(char templateSymbol, IItemConvertible stack, int meta){
        ItemStack _stack = stack.getDefaultStack();
        _stack.setMetadata(meta);
        return addShapedInput(templateSymbol, _stack);
    }
    public RecipeBuilder addShapedInput(char templateSymbol, ItemStack stack){
        return addShapedInput(templateSymbol, new RecipeSymbol(stack));
    }
    public RecipeBuilder addShapedInput(char templateSymbol, String itemGroup) {
        return addShapedInput(templateSymbol, new RecipeSymbol(itemGroup));
    }
    public RecipeBuilder addShapedInput(char templateSymbol, RecipeSymbol symbol){
        RecipeBuilder builder = this.clone();
        symbolShapedMap.put(templateSymbol, symbol);
        return builder;
    }
    public RecipeBuilder setShapeless(){
        RecipeBuilder builder = this.clone();
        builder.recipeType = RecipeType.SHAPELESS;
        return builder;
    }
    public RecipeBuilder addShapelessInput(IItemConvertible stack){
        return addShapelessInput(stack, 0);
    }
    public RecipeBuilder addShapelessInput(IItemConvertible stack, int meta){
        ItemStack _stack = stack.getDefaultStack();
        _stack.setMetadata(meta);
        return addShapelessInput(_stack);
    }
    public RecipeBuilder addShapelessInput(String itemGroup){
        return addShapelessInput(new RecipeSymbol(itemGroup));
    }
    public RecipeBuilder addShapelessInput(ItemStack stack){
        return addShapelessInput(new RecipeSymbol(stack));
    }
    public RecipeBuilder addShapelessInput(RecipeSymbol symbol){
        RecipeBuilder builder = this.clone();
        symbolShapelessList.add(symbol);
        return builder;
    }
    public RecipeBuilder setSmelting(){
        RecipeBuilder builder = this.clone();
        builder.recipeType = RecipeType.SMELTING;
        return builder;
    }
    public RecipeBuilder setBlasting(){
        RecipeBuilder builder = this.clone();
        builder.recipeType = RecipeType.BLASTING;
        return builder;
    }
    public RecipeBuilder setTrommeling(){
        RecipeBuilder builder = this.clone();
        builder.recipeType = RecipeType.TROMMEL;
        return builder;
    }
    public void build(String recipeID, ItemStack outputStack){
        switch (recipeType){
            case SHAPELESS:
                ((RecipeGroup<RecipeEntryCrafting<?, ?>>)RecipeHelper.getRecipeGroup(modID, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack())))
                        .register(recipeID, new RecipeEntryCraftingShapeless(symbolShapelessList, outputStack));
                break;
            case SHAPED:
                RecipeSymbol[] recipe = new RecipeSymbol[height * width];
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Character cha = null;
                        if (shape[y].length() > x){
                            cha = shape[y].charAt(x);
                        }
                        recipe[x + y * 3] = symbolShapedMap.get(cha);
                    }
                }
                ((RecipeGroup<RecipeEntryCrafting<?, ?>>)RecipeHelper.getRecipeGroup(modID, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack())))
                        .register(recipeID, new RecipeEntryCraftingShaped(width, height, recipe, outputStack, consumeContainer));
                break;
            case TROMMEL:
                break;
            case BLASTING:
                break;
            case SMELTING:
                break;
            default:
                throw new IllegalArgumentException("RecipeType " + recipeType + " has no assigned handling!");
        }
    }
    private enum RecipeType {
        SHAPELESS,
        SHAPED,
        SMELTING,
        BLASTING,
        TROMMEL;
    }
}
