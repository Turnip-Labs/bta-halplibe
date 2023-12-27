package turniplabs.halplibe.mixin.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(value = ItemStackJsonAdapter.class, remap = false)
public class ItemStackJsonAdapterMixin {
    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/core/item/ItemStack;",
    at = @At("HEAD"), cancellable = true)
    protected void deserializeKey(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ItemStack> cir) {
        JsonObject obj = json.getAsJsonObject();
        if (obj.has("key")) {
            String key = obj.get("key").getAsString();
            Integer itemId;
            if (key.startsWith("tile")){
                itemId = Block.keyToIdMap.get(key);
                if (itemId == null) {
                    throw new IllegalArgumentException("Null return when trying to located block from key '" + obj.get("key") + "'");
                }
            } else if (key.startsWith("item")) {
                itemId = Item.nameToIdMap.get(key);
                if (itemId == null) {
                    throw new IllegalArgumentException("Null return when trying to located item from key '" + obj.get("key") + "'");
                }
            } else {
                throw new IllegalArgumentException("Key '" + key + "' does not start with a valid predicate of 'item' or 'tile'");
            }

            ItemStack stack = obj.has("amount") ? new ItemStack(itemId, obj.get("amount").getAsInt(), obj.get("meta").getAsInt()) : new ItemStack(itemId, 1, obj.get("meta").getAsInt());
            cir.setReturnValue(stack);
        }
    }
}

