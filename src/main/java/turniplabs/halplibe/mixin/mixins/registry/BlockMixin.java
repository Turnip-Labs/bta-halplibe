package turniplabs.halplibe.mixin.mixins.registry;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.util.BlockExtension;

@Mixin(value = Block.class, remap = false)
public class BlockMixin implements BlockExtension {
	@Shadow public static int highestBlockId;
	
	@Shadow @Final public int id;
	
	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void captureHighest(CallbackInfo ci) {
		BlockHelper.highestVanilla = highestBlockId;
	}
	
	@Inject(at = @At("TAIL"), method = "<init>")
	public void postInit(String key, int id, Material material, CallbackInfo ci) {
		this.itemId = id;
	}
	
	@Unique
	int itemId;
	
	@Override
	public int bta_halplibe$itemId() {
		return itemId;
	}
	
	@Override
	public void bta_halplibe$setItemId(int id) {
		itemId = id;
	}
	
	@Redirect(method = "asItem", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;id:I"))
	public int switchIdRef(Block instance) {
		return itemId;
	}
}
