package turniplabs.halplibe.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.metadata.BuiltinModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ExceptionPopupElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.popup.PopupScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(value = ExceptionPopupElement.class, remap = false)
public class ExceptionPopupElementMixin {

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", ordinal = 4, shift = At.Shift.BEFORE))
    private static void create(Screen parent, Throwable e, Minecraft mc, CallbackInfoReturnable<PopupScreen> cir, @Local(name = "s") StringBuilder s) {
        s.append("Installed mods:").append("\n");
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if(Objects.equals(mod.getMetadata().getType(), "builtin")) continue;
            s.append("- ").append(mod.getMetadata().getName()).append(" ").append("(").append(mod.getMetadata().getId()).append(")").append(" ").append(mod.getMetadata().getVersion()).append("\n");
        }
        s.append("\n");
    }

    @Inject(method = "createFatal", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", ordinal = 4, shift = At.Shift.BEFORE))
    private static void createFatal(Throwable e, Minecraft mc, CallbackInfoReturnable<PopupScreen> cir, @Local(name = "s") StringBuilder s) {
        s.append("Installed mods:").append("\n");
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if(Objects.equals(mod.getMetadata().getType(), "builtin")) continue;
            s.append("- ").append(mod.getMetadata().getName()).append(" ").append("(").append(mod.getMetadata().getId()).append(")").append(" ").append(mod.getMetadata().getVersion()).append("\n");
        }
        s.append("\n");
    }

    @Inject(method = "createStartupError", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", ordinal = 4, shift = At.Shift.BEFORE))
    private static void createStartup(Throwable e, Minecraft mc, CallbackInfoReturnable<PopupScreen> cir, @Local(name = "s") StringBuilder s) {
        s.append("Installed mods:").append("\n");
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if(Objects.equals(mod.getMetadata().getType(), "builtin")) continue;
            s.append("- ").append(mod.getMetadata().getName()).append(" ").append("(").append(mod.getMetadata().getId()).append(")").append(" ").append(mod.getMetadata().getVersion()).append("\n");
        }
        s.append("\n");
    }

}
