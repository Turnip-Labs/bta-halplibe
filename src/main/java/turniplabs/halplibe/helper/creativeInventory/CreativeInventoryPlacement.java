package turniplabs.halplibe.helper.creativeInventory;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public sealed class CreativeInventoryPlacement {

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
