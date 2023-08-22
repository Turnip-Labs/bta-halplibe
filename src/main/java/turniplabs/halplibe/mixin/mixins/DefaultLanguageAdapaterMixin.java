package turniplabs.halplibe.mixin.mixins;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.util.DefaultLanguageAdapter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DefaultLanguageAdapter.class, remap = false)
public class DefaultLanguageAdapaterMixin {

    @Inject(
            method = "create",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Class;forName(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;"
            )
    )
    private <T> void create(ModContainer mod, String value, Class<T> type, CallbackInfoReturnable<T> cir) {
        try {
            Class.forName("net.minecraft.core.block.Block");
            Class.forName("net.minecraft.core.item.Item");
        } catch (ClassNotFoundException ignored) {
        }
    }
}
