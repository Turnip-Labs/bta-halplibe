package turniplabs.halplibe.mixin.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(value = ItemStackJsonAdapter.class, remap = false)
public class ItemStackJsonAdapterMixin {
    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/core/item/ItemStack;",
    at = @At("HEAD"), cancellable = true)
    protected void deserializeKey(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ItemStack> cir){
        JsonObject obj = json.getAsJsonObject();
        if (obj.has("key")){
            ItemStack stack = obj.has("amount") ? new ItemStack(getItemFromKey(obj.get("key").getAsString()), obj.get("amount").getAsInt(), obj.get("meta").getAsInt()) : new ItemStack(getItemFromKey(obj.get("key").getAsString()), 1, obj.get("meta").getAsInt());
            cir.setReturnValue(stack);
        }
    }
    @Unique
    private Item getItemFromKey(String key){
        for(Item item : Item.itemsList) {
            if (item != null) {
                if (item.getKey().equalsIgnoreCase(key)) {
                    return item;
                }
            }
        }
        throw new NullPointerException("Could not find an item that corresponds to key '" + key + "'");
    }
}

