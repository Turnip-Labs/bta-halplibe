package turniplabs.halplibe.mixin.fix;

import net.minecraft.src.ContainerPlayerCreative;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

import java.util.Arrays;

@Mixin(value = ContainerPlayerCreative.class, remap = false)
public class ContainerPlayerCreativeMixin {

    @Shadow
    public static ItemStack[] creativeItems;

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 1000))
    private static int halplibe_changeMaxDisplayedItems(int constant) {
        creativeItems = Arrays.copyOf(creativeItems, 10000);
        return constant + 9000;
    }




}
