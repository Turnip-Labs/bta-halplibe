package turniplabs.halplibe.helper.creativeInventory;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

// Hey uh. If you want a custom "category" you can just create an After inventory placement instance and save it somewhere.
//
// If you need a new kind of placement all together you will have to mixin into the registry,
// then you write a mixin to splice the inventory list.
// - Khep

public abstract class CreativeInventoryPlacement {

    private Supplier<List<ItemStack>> customSupplier = null;

    public CreativeInventoryPlacement setCustomSupplier(Supplier<List<ItemStack>> customSupplier) {
        this.customSupplier = customSupplier;
        return this;
    }

    public Supplier<List<ItemStack>> getCustomSupplier() {
        return customSupplier;
    }

    public static final class Category extends CreativeInventoryPlacement {
        private final CreativeInventoryCategory category;

        public Category(CreativeInventoryCategory category) {
            this.category = category;
        }

        public CreativeInventoryCategory getCategory() {
            return this.category;
        }
    }

    public static final class After extends CreativeInventoryPlacement {
        private final Supplier<IItemConvertible> entry;

        public After(Supplier<IItemConvertible> entry) {
            this.entry = entry;
        }

        public IItemConvertible getEntry() {
            return entry.get();
        }
    }
}
