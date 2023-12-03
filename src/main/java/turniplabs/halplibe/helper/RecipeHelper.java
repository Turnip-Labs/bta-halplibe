package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.legacy.CraftingManager;
import net.minecraft.core.crafting.legacy.recipe.RecipesBlastFurnace;
import net.minecraft.core.crafting.legacy.recipe.RecipesFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
@Deprecated
public class RecipeHelper {
    @Deprecated
    public static final CraftingManager craftingManager = CraftingManager.getInstance();
    public static final RecipesFurnace smeltingManager = RecipesFurnace.getInstance();
    public static final RecipesBlastFurnace blastingManager = RecipesBlastFurnace.getInstance();
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
