package turniplabs.halplibe.mixin.mixins;

import net.minecraft.src.ContainerPlayerCreative;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Arrays;

@Mixin(value = ContainerPlayerCreative.class, remap = false)
public abstract class ContainerPlayerCreativeMixin {

	@Shadow
	public static ItemStack[] creativeItems;

	@ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 1000))
	private static int halplibe_changeMaxDisplayedItems(int constant) {
		creativeItems = Arrays.copyOf(creativeItems, 10000);
		return constant + 9000;
	}


}
