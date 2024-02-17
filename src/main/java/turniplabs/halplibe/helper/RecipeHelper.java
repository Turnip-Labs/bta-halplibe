package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.legacy.CraftingManager;
import net.minecraft.core.crafting.legacy.recipe.IRecipe;
import net.minecraft.core.crafting.legacy.recipe.RecipesBlastFurnace;
import net.minecraft.core.crafting.legacy.recipe.RecipesFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
/**
 * @deprecated Class will be removed once the Legacy crafting manager is removed from BTA
 * Replace by the RecipeBuilder
 */
@Deprecated
public class RecipeHelper {
    @Deprecated
    public static final CraftingManager craftingManager = CraftingManager.getInstance();
    @Deprecated
    public static final RecipesFurnace smeltingManager = RecipesFurnace.getInstance();
    @Deprecated
    public static final RecipesBlastFurnace blastingManager = RecipesBlastFurnace.getInstance();
    @Deprecated
    public static void removeRecipe(Item outputItem, int meta) {
        IRecipe theRecipe = null;
    }
    @Deprecated
    public static class Crafting {
        
        public static void createShapelessRecipe(Item outputItem, int amount, Object[] aobj) {
            craftingManager.addShapelessRecipe(new ItemStack(outputItem, amount), aobj);
        }

        public static void createShapelessRecipe(Block outputBlock, int amount, Object[] aobj) {
            craftingManager.addShapelessRecipe(new ItemStack(outputBlock, amount), aobj);
        }

        public static void createRecipe(ItemStack stack, Object[] aobj) {
            craftingManager.addRecipe(stack, aobj);
        }

        public static void createShapelessRecipe(ItemStack stack, Object[] aobj) {
            craftingManager.addShapelessRecipe(stack, aobj);
        }
    }

    @Deprecated
    public static class Smelting {

        public static void createRecipe(Item outputItem, Item inputItem) {
            smeltingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Item outputItem, Block inputItem) {
            smeltingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Item inputItem) {
            smeltingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Block inputItem) {
            smeltingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }
    }

    @Deprecated
    public static class Blasting {
        public static void createRecipe(Item outputItem, Item inputItem) {
            blastingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Item outputItem, Block inputItem) {
            blastingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Item inputItem) {
            blastingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Block inputItem) {
            blastingManager.addSmelting(inputItem.id, new ItemStack(outputItem));
        }
    }

}
