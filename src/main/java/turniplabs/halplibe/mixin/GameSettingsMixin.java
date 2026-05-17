package turniplabs.halplibe.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

@Environment(EnvType.CLIENT)
@Mixin(value = GameSettings.class)
public abstract class GameSettingsMixin {

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void onGameSettingsInit(CallbackInfo ci) {
        FabricLoader.getInstance()
                .getEntrypoints("initOptions", OptionsInitEntrypoint.class)
                .forEach(OptionsInitEntrypoint::initOptions);
    }
}
