package turniplabs.halplibe.helper.recipeBuilders.modifiers;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.mixin.accessors.WeightedRandomBagAccessor;
import turniplabs.halplibe.mixin.accessors.WeightedRandomBagEntryAccessor;
import turniplabs.halplibe.util.IUnregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrommelModifier {
    private final WeightedRandomBag<WeightedRandomLootObject> trommelEntry;
    private final String key;
    private final String namespace;
    public TrommelModifier(String namespace, String key){
        this.key = key;
        this.namespace = namespace;
        trommelEntry = (WeightedRandomBag<WeightedRandomLootObject>) Objects.requireNonNull(RecipeBuilder.getRecipeGroup(namespace, "trommel", new RecipeSymbol(Block.trommelActive.getDefaultStack())).getItem(key), "Requested recipe " + (namespace + ":trommel/" + key) + " does not exist!").getOutput();
    }
    public void deleteRecipe(){
        RecipeGroup<RecipeEntryTrommel> recipeGroup = (RecipeGroup<RecipeEntryTrommel>) RecipeBuilder.getRecipeGroup(namespace, "trommel", new RecipeSymbol(Block.trommelActive.getDefaultStack()));
        ((IUnregister<RecipeEntryFurnace>)recipeGroup).bta_halplibe$unregister(key);
    }
    public TrommelModifier addEntry(WeightedRandomLootObject lootObject, double weight){
        trommelEntry.addEntry(lootObject, weight);
        return this;
    }

    /**
     * @param outputStack The stack the entries to be removed use
     * Deletes all entries matching the provided stack.
     */
    public TrommelModifier removeEntries(ItemStack outputStack){
        List<WeightedRandomBag<WeightedRandomLootObject>.Entry> outputs = new ArrayList<>(trommelEntry.getEntriesWithWeights());
        for (WeightedRandomBag.Entry object : outputs){
            WeightedRandomLootObject weightedObject = (WeightedRandomLootObject) object.getObject();
            if (weightedObject.getItemStack().isItemEqual(outputStack)){
                ((WeightedRandomBagAccessor)trommelEntry).getRawEntries().remove(object);
            }
        }
        recalculateWeights();
        return this;
    }
    /**
     * @param outputStack The stack the entry to be removed uses
     * Deletes the first entry matching the provided stack and weight.
     */
    public TrommelModifier removeEntry(ItemStack outputStack, double weight){
        List<WeightedRandomBag<WeightedRandomLootObject>.Entry> outputs = new ArrayList<>(trommelEntry.getEntriesWithWeights());
        for (WeightedRandomBag.Entry object : outputs){
            WeightedRandomLootObject weightedObject = (WeightedRandomLootObject) object.getObject();
            if (weightedObject.getItemStack().isItemEqual(outputStack) && weight == object.getWeight()){
                ((WeightedRandomBagAccessor)trommelEntry).getRawEntries().remove(object);
                break;
            }
        }
        recalculateWeights();
        return this;
    }

    /**
     * @param outputStack The stack the entry to be modified uses
     * @param oldWeight The weight the bag currently uses
     * @param newWeight The weight to replace the old weight with
     */
    public TrommelModifier setWeight(ItemStack outputStack, double oldWeight, double newWeight){
        List<WeightedRandomBag<WeightedRandomLootObject>.Entry> outputs = new ArrayList<>(trommelEntry.getEntriesWithWeights());
        for (WeightedRandomBag.Entry object : outputs){
            WeightedRandomLootObject weightedObject = (WeightedRandomLootObject) object.getObject();
            if (weightedObject.getItemStack().isItemEqual(outputStack) && oldWeight == object.getWeight()){
                ((WeightedRandomBagEntryAccessor)object).setWeight(newWeight);
                break;
            }
        }
        recalculateWeights();
        return this;
    }
    /**
     * @param outputStack The stack the entries to be modified uses
     * @param newWeight The weight to replace the old weight with
     */
    public TrommelModifier setWeights(ItemStack outputStack, double newWeight){
        List<WeightedRandomBag<WeightedRandomLootObject>.Entry> outputs = new ArrayList<>(trommelEntry.getEntriesWithWeights());
        for (WeightedRandomBag.Entry object : outputs){
            WeightedRandomLootObject weightedObject = (WeightedRandomLootObject) object.getObject();
            if (weightedObject.getItemStack().isItemEqual(outputStack)){
                ((WeightedRandomBagEntryAccessor)object).setWeight(newWeight);
            }
        }
        recalculateWeights();
        return this;
    }
    protected void recalculateWeights(){
        double weight = 0;
        for (WeightedRandomBag.Entry object : trommelEntry.getEntriesWithWeights()){
            weight += object.getWeight();
        }
        ((WeightedRandomBagAccessor)trommelEntry).setAccumulatedWeight(weight);
    }
}
