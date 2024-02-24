package turniplabs.halplibe.mixin.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.HalpLibe;

@Mixin(value = GuiTooltip.class, remap = false)
public class GuiTooltipMixin extends Gui {

    @Shadow Minecraft mc;

    @Inject(method = "getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;", at = @At(value = "FIELD", target = "Lnet/minecraft/core/player/inventory/slot/Slot;discovered:Z", ordinal = 0, shift = At.Shift.AFTER))
    public void getTooltipText(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, @Local(ordinal = 0, argsOnly = true) LocalBooleanRef discovered) {
        if(mc.theWorld.getGameRule(HalpLibe.UNLOCK_ALL_RECIPES)){
            discovered.set(true);
        }
    }

}
