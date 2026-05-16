package turniplabs.halplibe.helper.creativeInventory;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreativeInventoryRegistry {

    private CreativeInventoryRegistry() {}
    public final static CreativeInventoryRegistry INSTANCE = new CreativeInventoryRegistry();

    private final Map<CreativeInventoryCategory, List<ItemStack>> itemsByCategory = new HashMap<>();
    private final Map<NamespaceID, List<ItemStack>> itemsByInserted = new HashMap<>();

    private final List<IItemConvertible> selfList = new ArrayList<>();
    private final List<CreativeInventoryPlacement> placementList = new ArrayList<>();

    public void register(IItemConvertible self, CreativeInventoryPlacement placement) {
            selfList.add(self);
            placementList.add(placement);
    }

    /// call this after all blocks are registered to get all the inserts.
    public void bakeAll() {
        var itemIt = selfList.iterator();
        var placementIt = placementList.iterator();

        while (itemIt.hasNext()) {
            var placement = placementIt.next();
            var item = itemIt.next();

            var toAdd = placement.getCustomSupplier() != null ? placement.getCustomSupplier().get() : List.of(item.getDefaultStack());

            List<ItemStack> list;

            if (placement instanceof CreativeInventoryPlacement.After after) {
                list = itemsByInserted.computeIfAbsent(after.getEntry().asItem().namespaceID, (k) -> new ArrayList<>());
            }

            else if (placement instanceof CreativeInventoryPlacement.Category cat) {
                list = itemsByCategory.computeIfAbsent(cat.getCategory(), (k) -> new ArrayList<>());
            }

            else throw new RuntimeException("CreativeInventoryPlacement type not registered. Call an developer!");

            list.addAll(toAdd);
        }
    }

    public List<ItemStack> getAllFor(CreativeInventoryCategory category) {
        return this.itemsByCategory.getOrDefault(category, new ArrayList<>());
    }

    public List<ItemStack> getAllFor(NamespaceID item) {
        return this.itemsByInserted.getOrDefault(item, List.of());
    }
}
