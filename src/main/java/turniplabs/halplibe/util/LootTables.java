package turniplabs.halplibe.util;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;

public class LootTables {
    public static final HashMap<Integer, LootStack> dungeonLoot;

    static {
        dungeonLoot = new HashMap<Integer, LootStack>() {{

            put(0, new LootStack(Item.saddle) );
            put(1, new LootStack(Item.ingotIron, 0, 5, 1) );
            put(2, new LootStack(Item.foodBread) );
            put(3, new LootStack(Item.wheat, 0, 5) );
            put(4, new LootStack(Item.sulphur, 0, 5) );
            put(5, new LootStack(Item.string, 0, 5) );
            put(6, new LootStack(Item.bucket) );
            put(7, new LootStack(Item.foodAppleGold, 100) );
            put(8, new LootStack(Item.dustRedstone, 2, 6,3) );
            put(9, new LootStack(Item.foodApple) );
            put(10, new LootStack(Item.dye, 1, 1,1, 3,3) );
            put(11, new LootStack( Block.spongeDry, 0, 5) );
            put(12, new LootStack(Item.bone, 0, 5) );

            //disks
            put(13, new LootStack(Item.record13, 50) );
            put(14, new LootStack(Item.recordCat, 50) );
            put(15, new LootStack(Item.recordBlocks, 50) );
            put(16, new LootStack(Item.recordChirp, 50) );
            put(17, new LootStack(Item.recordFar, 50) );
            put(18, new LootStack(Item.recordMall, 50) );
            put(19, new LootStack(Item.recordMellohi, 50) );
            put(20, new LootStack(Item.recordStal, 50) );
            put(21, new LootStack(Item.recordStrad, 50) );
            put(22, new LootStack(Item.recordWard, 50) );

        }};
    }

    public static final HashMap<Integer, LootStack> labyrinthLoot;

    static {
        labyrinthLoot = new HashMap<Integer, LootStack>() {{
            put(0, new LootStack( Item.ingotIron,0,6) );
            put(1, new LootStack( Item.ingotGold,0,4) );
            put(2, new LootStack( Item.sulphur,0,6) );
            put(3, new LootStack( Item.diamond,50,6,3) );
            put(4, new LootStack( Item.foodAppleGold,100) );
            put(5, new LootStack( Item.dustRedstone,0,4) );
            put(6, new LootStack( Item.foodApple,0) );
            put(7, new LootStack( Block.spongeDry, 0, 5) );
            put(8, new LootStack( Item.handcannonLoaded, 50));
            put(9, new LootStack( Item.handcannonUnloaded, 50));
            put(10, new LootStack( Item.armorHelmetChainmail,5,1,1, 240,120) );
            put(11, new LootStack( Item.armorChestplateChainmail,5,1,1, 240,120) );
            put(12, new LootStack( Item.armorLeggingsChainmail,5,1, 1, 240,120) );
            put(13, new LootStack( Item.armorBootsChainmail,5,1, 1, 240,120) );
            put(14, new LootStack( Item.ingotSteelCrude,10,3) );

            //disks
            put(15, new LootStack(Item.record13, 50) );
            put(16, new LootStack(Item.recordCat, 50) );
            put(17, new LootStack(Item.recordBlocks, 50) );
            put(18, new LootStack(Item.recordChirp, 50) );
            put(19, new LootStack(Item.recordFar, 50) );
            put(20, new LootStack(Item.recordMall, 50) );
            put(21, new LootStack(Item.recordMellohi, 50) );
            put(22, new LootStack(Item.recordStal, 50) );
            put(23, new LootStack(Item.recordStrad, 50) );
            put(24, new LootStack(Item.recordWard, 50) );

        }};
}

}
