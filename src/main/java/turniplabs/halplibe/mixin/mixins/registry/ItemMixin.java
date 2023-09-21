package turniplabs.halplibe.mixin.mixins.registry;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.util.HalplibeItemBlock;

@Mixin(value = Item.class, remap = false)
public class ItemMixin {
	@Shadow public static int highestItemId;
	
	@Shadow public static Item[] itemsList;
	
	@Unique private static Item tmp;
	
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/core/item/Item;id:I"), method = "<init>(I)V")
	public void preSetId(int id, CallbackInfo ci) {
		tmp = itemsList[id];
		//noinspection ConstantValue
		if ((Object) this instanceof HalplibeItemBlock) {
			itemsList[id] = null;
		}
	}
	
	@Inject(at = @At(value = "TAIL"), method = "<init>(I)V")
	public void postSetId(int id, CallbackInfo ci) {
		itemsList[id] = tmp;
	}
	
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/core/item/Item;highestItemId:I", shift = At.Shift.BEFORE), method = "<clinit>", cancellable = true)
	private static void captureHighest(CallbackInfo ci) {
		ItemHelper.highestVanilla = highestItemId;
		ci.cancel();
	}
	
	@Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;onItemInit()V"))
	private static void delayInit() {
	
	}
}
