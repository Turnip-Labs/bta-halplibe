package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryBlastFurnace;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.Objects;

public class RecipeBuilderBlastFurnace extends RecipeBuilderBase {
    protected final RecipeSymbol[] input = new RecipeSymbol[2];

    /**
     * Used for creating new blast furnace recipes, supports 1 or 2 inputs.
     *
     * @param modID Namespace to create recipe under
     */
    public RecipeBuilderBlastFurnace(String modID) {
        super(modID);
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(IItemConvertible input) {
        return setInput(0, input);
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(IItemConvertible input, int meta) {
        return setInput(0, new ItemStack(input, 1, meta));
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(ItemStack input) {
        return setInput(0, input);
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(String input) {
        return setInput(0, input);
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(RecipeSymbol input) {
        return setInput(0, input);
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(int index, IItemConvertible input) {
        return setInput(index, new ItemStack(input));
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(int index, IItemConvertible input, int meta) {
        return setInput(index, new ItemStack(input, 1, meta));
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(int index, ItemStack input) {
        return setInput(index, new RecipeSymbol(input));
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(int index, String input) {
        return setInput(index, new RecipeSymbol(input));
    }

    @SuppressWarnings({"unused"})
    public RecipeBuilderBlastFurnace setInput(int index, RecipeSymbol symbol) {
        if (index < 0 || index > 1) {
            throw new IndexOutOfBoundsException("Blast furnace only supports input indices 0 and 1");
        }
        this.input[index] = Objects.requireNonNull(symbol, "Input symbol must not be null!");
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void create(String recipeID, ItemStack outputStack) {
        int count = (this.input[1] != null) ? 2 : 1;
        RecipeSymbol[] inputs = new RecipeSymbol[count];
        inputs[0] = this.input[0];
        if (count == 2) {
            inputs[1] = this.input[1];
        }

        Objects.requireNonNull(this.input[0], "At least inputs[0] must be set!");
        ((RecipeGroup<RecipeEntryBlastFurnace>) RecipeBuilder.getRecipeGroup(modID, "blast_furnace", new RecipeSymbol(Blocks.FURNACE_BLAST_ACTIVE.getDefaultStack())))
                .register(recipeID, new RecipeEntryBlastFurnace(inputs, outputStack));
    }
}