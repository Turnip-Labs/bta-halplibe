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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.lang.reflect.Type;

@Mixin(value = ItemStackJsonAdapter.class, remap = false)
public class ItemStackJsonAdapterMixin {
    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/core/item/ItemStack;",
    at = @At("HEAD"), cancellable = true)
    protected void deserializeKey(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ItemStack> cir) {
        JsonObject obj = json.getAsJsonObject();
        if (obj.has("key")) {
            String key = obj.get("key").getAsString();
            int itemId = HalpLibe.getTrueItemOrBlockId(key);
            ItemStack stack = obj.has("amount") ? new ItemStack(itemId, obj.get("amount").getAsInt(), obj.get("meta").getAsInt()) : new ItemStack(itemId, 1, obj.get("meta").getAsInt());
            cir.setReturnValue(stack);
        }
    }
    @Redirect(method = "serialize(Lnet/minecraft/core/item/ItemStack;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;",
            at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;addProperty(Ljava/lang/String;Ljava/lang/Number;)V", ordinal = 0))
    private void useKeyForSerializing(JsonObject instance, String property, Number value){
        if (RecipeBuilder.isExporting){
            instance.addProperty("key", Item.itemsList[(int) value].getKey());
            return;
        }
        instance.addProperty(property, value);
    }
}

