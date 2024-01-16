package turniplabs.halplibe.helper.recipeBuilders;

import net.minecraft.core.item.ItemStack;

import java.util.Objects;

abstract class RecipeBuilderBase implements Cloneable {
    protected String modID;
    public RecipeBuilderBase(String modID){
        this.modID = Objects.requireNonNull(modID, "ModID must not be null!");
    }
    public <T> T clone(T object){
        return (T) clone();
    }
    @Override
    public RecipeBuilderBase clone() {
        try {
            // none of the fields are mutated so this should be fine
            return (RecipeBuilderBase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public abstract void build(String recipeID, ItemStack outputStack);
}
