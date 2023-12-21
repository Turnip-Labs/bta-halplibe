package turniplabs.halplibe.util;

import net.minecraft.core.item.Item;

import java.util.HashMap;

public class ItemUtil {
    public static HashMap<String, Item> itemCache = new HashMap<>();
    public static Item getItemFromKey(String key){
        if (itemCache.containsKey(key)){
            return itemCache.get(key);
        }
        for(Item item : Item.itemsList) {
            if (item != null) {
                itemCache.put(item.getKey(), item);
                if (item.getKey().equalsIgnoreCase(key)) {
                    return item;
                }
            }
        }
        throw new NullPointerException("Could not find an item that corresponds to key '" + key + "'");
    }
}
