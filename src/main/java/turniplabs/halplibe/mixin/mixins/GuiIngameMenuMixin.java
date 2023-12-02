package turniplabs.halplibe.mixin.mixins;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.achievements.GuiAchievements;

@Mixin(
        value = GuiIngameMenu.class,
        remap = false
)
public class GuiIngameMenuMixin extends GuiScreen {
    @Inject(
            method = "buttonPressed",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void buttonPressed(GuiButton guibutton, CallbackInfo ci) {
        if (guibutton.id == 5) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.statsCounter));
            ci.cancel();
        }
    }
}
