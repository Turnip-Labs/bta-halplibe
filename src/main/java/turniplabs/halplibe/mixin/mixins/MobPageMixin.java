package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.gui.guidebook.mobs.MobPage;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

@Mixin(value = MobPage.class, remap = false)
public class MobPageMixin {
    @Shadow private boolean discovered;

    @Shadow @Final private static Minecraft mc;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onMobPageInit(GuidebookSection section, Class<?> mobClass, MobInfoRegistry.MobInfo mobInfo, CallbackInfo ci) {
        if(mc.theWorld.getGameRule(HalpLibe.UNLOCK_ALL_RECIPES)){
            discovered = true;
        }
    }

    @Inject(method = "renderForeground", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/guidebook/mobs/MobPage;discovered:Z", ordinal = 0, shift = At.Shift.AFTER))
    public void setDiscovered(RenderEngine re, FontRenderer fr, int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if(mc.theWorld.getGameRule(HalpLibe.UNLOCK_ALL_RECIPES)){
            discovered = true;
        }
    }
}
