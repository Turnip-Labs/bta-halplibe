package turniplabs.halplibe.mixin.recovery;

import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

@Mixin(value = HudIngame.class)
public abstract class HudIngameMixin {

    @Shadow
    protected abstract void drawDebugScreenLineRight(CharSequence string);

    @Shadow
    private int debugLine;

    @Shadow
    private int debugLineRight;

    @Shadow
    private int debugOffset;

    @Shadow
    private int debugOffsetRight;

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    public void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (HalpLibe.CONFIG.getBoolean("recoveryMode")) {
            drawDebugScreenLineRight(I18n.getInstance().translateKey("halplibe.recoveryMode"));
            drawDebugScreenLineRight(I18n.getInstance().translateKey("halplibe.recoveryMode.action1"));
            drawDebugScreenLineRight(I18n.getInstance().translateKey("halplibe.recoveryMode.action2"));
            this.debugLine = 0;
            this.debugLineRight = 0;
            this.debugOffset = 0;
            this.debugOffsetRight = 0;
        }
    }

}