package turniplabs.halplibe.mixin.helper;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.minecraft.src.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Mixin(value = StringTranslate.class, remap = false)
public abstract class StringTranslateMixin {
    @Shadow private Properties translateTable;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void onApplyTranslations(CallbackInfo ci) {
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            try (InputStream stream = FabricLauncherBase.getLauncher().getResourceAsStream("lang/" +
                    mod.getMetadata().getId() +
                    "/en_US.lang")) {
                HalpLibe.LOGGER.debug("Language file for " + mod.getMetadata().getId() + " is not null: " + (stream != null));
                if (stream != null) {
                    this.translateTable.load(stream);
                }
            } catch (IOException ignored) {}
        }
    }
}
