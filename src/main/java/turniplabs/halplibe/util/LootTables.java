package turniplabs.halplibe.util;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;

public class LootTables {
    public static final HashMap<Integer, LootStack> dungeonLoot;

    static {
        dungeonLoot = new HashMap<Integer, LootStack>() {{

            put(0, new LootStack(new ItemStack(Item.saddle)));
            put(1, new LootStack(new ItemStack(Item.ingotIron), 0, 5));
            put(2, new LootStack(new ItemStack(Item.foodBread)));
            put(3, new LootStack(new ItemStack(Item.wheat), 0, 5));
            put(4, new LootStack(new ItemStack(Item.sulphur), 0, 5));
            put(5, new LootStack(new ItemStack(Item.string), 0, 5));
            put(6, new LootStack(new ItemStack(Item.bucket)));
            put(7, new LootStack(new ItemStack(Item.foodAppleGold), 100));
            put(8, new LootStack(new ItemStack(Item.dustRedstone), 2, 16));
            put(9, new LootStack(new ItemStack(Item.foodApple)));
            put(10, new LootStack(new ItemStack(Item.dye, 1, 3)));
            put(11, new LootStack(new ItemStack(Block.spongeDry), 0, 5));
            put(12, new LootStack(new ItemStack(Item.bone), 0, 5));

            //disks
            put(13, new LootStack(new ItemStack(Item.record13), 50));
            put(14, new LootStack(new ItemStack(Item.recordCat), 50));
            put(15, new LootStack(new ItemStack(Item.recordBlocks), 50));
            put(16, new LootStack(new ItemStack(Item.recordChirp), 50));
            put(17, new LootStack(new ItemStack(Item.recordFar), 50));
            put(18, new LootStack(new ItemStack(Item.recordMall), 50));
            put(19, new LootStack(new ItemStack(Item.recordMellohi), 50));
            put(20, new LootStack(new ItemStack(Item.recordStal), 50));
            put(21, new LootStack(new ItemStack(Item.recordStrad), 50));
            put(22, new LootStack(new ItemStack(Item.recordWard), 50));

        }};
    }

    public static final HashMap<Integer, LootStack> labyrinthLoot;

    static {
        labyrinthLoot = new HashMap<Integer, LootStack>() {{
            put(0, new LootStack( new ItemStack(Item.ingotIron),0,6) );
            put(1, new LootStack( new ItemStack(Item.ingotGold),0,4) );
            put(2, new LootStack( new ItemStack(Item.sulphur,3),0,6) );
            put(3, new LootStack( new ItemStack(Item.diamond),50,3) );
            put(4, new LootStack( new ItemStack(Item.foodAppleGold),100) );
            put(5, new LootStack( new ItemStack(Item.dustRedstone),0,4) );
            put(6, new LootStack( new ItemStack(Item.foodApple),0) );
            put(7, new LootStack(new ItemStack(Block.spongeDry), 0, 5) );
            put(8, new LootStack( new ItemStack(Item.handcannonLoaded), 50));
            put(9, new LootStack( new ItemStack(Item.handcannonUnloaded), 50));
            put(10, new LootStack( new ItemStack(Item.armorHelmetChainmail),5,1, 120) );
            put(11, new LootStack( new ItemStack(Item.armorChestplateChainmail),5,1, 120) );
            put(12, new LootStack( new ItemStack(Item.armorLeggingsChainmail),5,1, 120) );
            put(13, new LootStack( new ItemStack(Item.armorBootsChainmail),5,1, 120) );
            put(14, new LootStack( new ItemStack(Item.ingotSteelCrude),10,3) );

            //disks
            put(15, new LootStack(new ItemStack(Item.record13), 50));
            put(16, new LootStack(new ItemStack(Item.recordCat), 50));
            put(17, new LootStack(new ItemStack(Item.recordBlocks), 50));
            put(18, new LootStack(new ItemStack(Item.recordChirp), 50));
            put(19, new LootStack(new ItemStack(Item.recordFar), 50));
            put(20, new LootStack(new ItemStack(Item.recordMall), 50));
            put(21, new LootStack(new ItemStack(Item.recordMellohi), 50));
            put(22, new LootStack(new ItemStack(Item.recordStal), 50));
            put(23, new LootStack(new ItemStack(Item.recordStrad), 50));
            put(24, new LootStack(new ItemStack(Item.recordWard), 50));

        }};
}

}
