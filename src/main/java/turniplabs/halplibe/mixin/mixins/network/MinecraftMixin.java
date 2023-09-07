package turniplabs.halplibe.mixin.mixins.network;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.NetworkHelper;

import java.lang.reflect.Method;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
    @Inject(at = @At("HEAD"), method = "run")
    public void postStartClient(CallbackInfo ci) {
        NetworkHelper.iterateEntries((e) -> {
            try {
                Method m = NetworkHelper.class.getDeclaredMethod("doRegister", Class.class, boolean.class, boolean.class);
                m.setAccessible(true);
                m.invoke(null, e.clazz, e.toServer, e.toClient);
                m.setAccessible(false);
            } catch (Throwable err) {
                err.printStackTrace();
            }
        });
    }
}
