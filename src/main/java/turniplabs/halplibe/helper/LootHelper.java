package turniplabs.halplibe.helper;

import turniplabs.halplibe.util.LootStack;

import java.util.HashMap;

public class LootHelper {

    public static void addToLootTable(HashMap lootTable, LootStack lootStack){
        lootTable.put( lootTable.size(), lootStack );
    }

    public static void addToLootTable(HashMap lootTable, int index, LootStack lootStack){
        lootTable.put( index, lootStack );
    }

    public static void removeFromLootTable(HashMap lootTable, int index){
        lootTable.put( index, null );
    }

    public static LootStack getLootTable(HashMap lootTable, int index){
        return (LootStack) lootTable.get(index);
    }

}
