package turniplabs.halplibe.helper;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;
import turniplabs.halplibe.mixin.accessors.ItemAccessor;
import turniplabs.halplibe.mixin.accessors.ItemDamageAccessor;
import turniplabs.halplibe.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@NullMarked
@SuppressWarnings("unused")
public final class ItemBuilder implements Cloneable {
    private final String modId;

    private @Nullable String overrideKey = null;
    public Tag<Item>[] tags = ArrayUtils.newArray(Tag.class, 0);
    private @Nullable Integer stackSize = null;
    private @Nullable Integer maxDamage = null;
    private @Nullable Supplier<Item> containerItemSupplier = null;
    private @Nullable CreativeInventoryPlacement creativeInventoryPlacement = null;

    public ItemBuilder(String modId) {
        this.modId = modId;
    }

    @Override
    public ItemBuilder clone() {
        try {
            // none of the fields are mutated so this should be fine
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Sets the key to the built {@link Item}, for example if you set the key "gem.sapphire" the actual key ingame will be "item.<modid>.gem.sapphire"
     *
     * @param key Override translation key for the {@link Item}
     * @return Copy of {@link ItemBuilder}
     * @deprecated Use the {@link Item} constructor to provide a language key
     */
    @Deprecated(since = "6.1.0")
    @SuppressWarnings({"unused"})
    public ItemBuilder setKey(String key) {
        ItemBuilder builder = this.clone();
        builder.overrideKey = key;
        return builder;
    }

    /**
     * Sets stack size for the built {@link Item}, will override any class default stacksizes
     *
     * @param stackSize Stack size of the {@link Item}
     * @return Copy of {@link ItemBuilder}
     */
    @SuppressWarnings({"unused"})
    public ItemBuilder setStackSize(int stackSize) {
        ItemBuilder builder = this.clone();
        builder.stackSize = stackSize;
        return builder;
    }

    /**
     * Sets max durability for the built {@link Item}, will override any class default max damage values.
     * Probably only really affects tool classes.
     *
     * @param maxDamage Max durability of the {@link Item}
     * @return Copy of {@link ItemBuilder}
     */
    @SuppressWarnings({"unused"})
    public ItemBuilder setMaxDamage(int maxDamage) {
        ItemBuilder builder = this.clone();
        builder.maxDamage = maxDamage;
        return builder;
    }

    /**
     * Sets the container item for the built item. For example {@code Item.bucketMilk} uses the container item {@code Item.bucket}
     *
     * @param itemSupplier Supplies the {@link Item} to set as the container item
     * @return Copy of {@link ItemBuilder}
     */
    @SuppressWarnings({"unused"})
    public ItemBuilder setContainerItem(@Nullable Supplier<Item> itemSupplier) {
        ItemBuilder builder = this.clone();
        builder.containerItemSupplier = itemSupplier;
        return builder;
    }

    /**
     * Overrides all previous tags with the ones provided
     *
     * @return Copy of {@link ItemBuilder}
     */
    @SafeVarargs
    @SuppressWarnings({"unused"})
    public final ItemBuilder setTags(Tag<Item>... tags) {
        ItemBuilder itemBuilder = this.clone();
        itemBuilder.tags = Arrays.copyOf(tags, tags.length);
        return itemBuilder;
    }

    /**
     * Adds provided tags to previously specified tags
     *
     * @return Copy of {@link ItemBuilder}
     */
    @SafeVarargs
    @SuppressWarnings({"unused"})
    public final ItemBuilder addTags(Tag<Item>... tags) {
        ItemBuilder itemBuilder = this.clone();
        itemBuilder.tags = ArrayUtils.addAll(this.tags, tags);
        return itemBuilder;
    }

    /**
     * Adds item to the creative inventory based on the placement construct
     */
    public ItemBuilder setCreativeInventoryPlacement(@Nullable CreativeInventoryPlacement creativeInventoryPlacement) {
        ItemBuilder itemBuilder = this.clone();
        itemBuilder.creativeInventoryPlacement = creativeInventoryPlacement;
        return itemBuilder;
    }

    /**
     * Applies the builder configuration to the supplied item.
     *
     * @param item Input item object
     * @return Returns the input item after builder settings are applied to it.
     */
    @SuppressWarnings("unused")
    public <T extends Item> T build(T item) {
        final String theKey = overrideKey == null ? item.getKey() : overrideKey;
        List<String> tokens = Arrays.asList(theKey.split("\\."));

        item.withTags(tags);
        if (stackSize != null) item.setMaxStackSize(stackSize);
        if (containerItemSupplier != null) item.setContainerItem(containerItemSupplier.get());
        if (maxDamage != null) ((ItemDamageAccessor) item).callSetMaxDamage(maxDamage);

        if (creativeInventoryPlacement != null && !item.hasTag(ItemTags.NOT_IN_CREATIVE_MENU)) {
            CreativeInventoryRegistry.INSTANCE.register(item, creativeInventoryPlacement);
        }

        List<String> newTokens = new ArrayList<>();
        newTokens.add("item");
        newTokens.add(modId);
        newTokens.addAll(tokens.subList(1, tokens.size()));

        Item.nameToIdMap.remove(item.getKey(), item.id);
        ((ItemAccessor) item).setKey(String.join(".", newTokens));
        Item.nameToIdMap.put(item.getKey(), item.id);

        return item;
    }

}