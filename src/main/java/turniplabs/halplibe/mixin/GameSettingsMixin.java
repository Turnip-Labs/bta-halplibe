package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

import java.io.File;

@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin {
    @Inject(method = "<init>", at = @At(value = "NEW", target = "(Ljava/io/File;Ljava/lang/String;)Ljava/io/File;"))
    public void initOptions(Minecraft minecraft, File file, CallbackInfo ci) {
        GameSettings settings = (GameSettings) (Object) this;

        FabricLoader.getInstance().getEntrypoints("initOptions", OptionsInitEntrypoint.class).forEach(e -> e.initOptions(settings));
    }
}
