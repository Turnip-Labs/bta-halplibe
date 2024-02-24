package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiRenderItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

@Mixin(
        value = GuiRenderItem.class,
        remap = false
)
public class GuiRenderItemMixin extends Gui {

    @Shadow
    Minecraft mc;

    @Inject(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", at = @At("HEAD"))
    public void render(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo ci) {
        if(mc.theWorld.getGameRule(HalpLibe.UNLOCK_ALL_RECIPES)){
            slot.discovered = true;
        }
    }
}
