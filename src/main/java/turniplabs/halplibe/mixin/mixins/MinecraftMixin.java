package turniplabs.halplibe.mixin.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, remap = false, priority = 999)
public class MinecraftMixin {
    @Shadow private static Minecraft INSTANCE;

    @Inject(method = "getMinecraft(Ljava/lang/Object;)Lnet/minecraft/client/Minecraft;", at = @At("HEAD"), cancellable = true)
    private static void returnMinecraft(Object caller, CallbackInfoReturnable<Minecraft> cir){
        cir.setReturnValue(INSTANCE);
    }
}
