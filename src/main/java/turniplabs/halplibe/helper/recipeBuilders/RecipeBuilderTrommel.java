package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.Objects;

public class RecipeBuilderTrommel extends RecipeBuilderBase{
    protected RecipeSymbol input;
    protected WeightedRandomBag<WeightedRandomLootObject> bag = new WeightedRandomBag<>();
    public RecipeBuilderTrommel(String modID) {
        super(modID);
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel setInput(IItemConvertible item){
        return setInput(item, 0);
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel setInput(IItemConvertible item, int meta){
        return setInput(new ItemStack(item, 1, meta));
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel setInput(ItemStack input){
        return setInput(new RecipeSymbol(input));
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel setInput(String itemGroup){
        return setInput(new RecipeSymbol(itemGroup));
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel setInput(RecipeSymbol input){
        RecipeBuilderTrommel builder = this.clone(this);
        builder.input = Objects.requireNonNull(input, "Input symbol must not be null!");
        return builder;
    }
    @SuppressWarnings({"unused"})
    public RecipeBuilderTrommel addEntry(WeightedRandomLootObject lootObject, double weight){
        RecipeBuilderTrommel builder = this.clone(this);
        builder.bag.addEntry(lootObject, weight);
        return builder;
    }
    @SuppressWarnings({"unused", "unchecked"})
    public void create(String recipeID) {
        Objects.requireNonNull(input, "Input symbol must not be null!");
        Objects.requireNonNull(bag, "Weighted Bag must not be null!");
        ((RecipeGroup<RecipeEntryTrommel>) RecipeBuilder.getRecipeGroup(modID, "trommel", new RecipeSymbol(Block.trommelActive.getDefaultStack())))
                .register(recipeID, new RecipeEntryTrommel(input, bag));
    }
    @Override
    protected void create(String recipeID, ItemStack outputStack) {
        // Standard create method doesn't apply to this class
    }
}
