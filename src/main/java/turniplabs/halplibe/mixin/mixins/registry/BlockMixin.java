package turniplabs.halplibe.mixin.mixins.registry;

import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.BlockHelper;

@Mixin(value = Block.class, remap = false)
public class BlockMixin {
	@Shadow public static int highestBlockId;
	
	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void captureHighest(CallbackInfo ci) {
		BlockHelper.highestVanilla = highestBlockId;
	}
}
