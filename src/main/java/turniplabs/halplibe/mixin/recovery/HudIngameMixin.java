package turniplabs.halplibe.mixin.recovery;

import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

@Mixin(value = HudIngame.class)
public abstract class HudIngameMixin {

    @Shadow
    private int debugLine;

    @Shadow
    private int debugLineRight;

    @Shadow
    private int debugOffset;

    @Shadow
    private int debugOffsetRight;

    @Shadow
    protected abstract void drawDebugScreenLineRight(@NotNull TessellatorGeneral tessellator, @NotNull CharSequence string);

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    public void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (HalpLibe.CONFIG.getBoolean("recoveryMode")) {
            drawDebugScreenLineRight(GLRenderer.getTessellator(),I18n.getInstance().translateKey("halplibe.recoveryMode"));
            drawDebugScreenLineRight(GLRenderer.getTessellator(),I18n.getInstance().translateKey("halplibe.recoveryMode.action1"));
            drawDebugScreenLineRight(GLRenderer.getTessellator(),I18n.getInstance().translateKey("halplibe.recoveryMode.action2"));
            this.debugLine = 0;
            this.debugLineRight = 0;
            this.debugOffset = 0;
            this.debugOffsetRight = 0;
        }
    }

}