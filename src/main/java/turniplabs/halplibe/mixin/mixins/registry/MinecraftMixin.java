package turniplabs.halplibe.mixin.mixins.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.core.achievement.stat.StatList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.RegistryHelper;

import java.lang.reflect.Method;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Inject(at = @At("HEAD"), method = "run")
	public void postInit(CallbackInfo ci) {
		try {
			Method m = RegistryHelper.class.getDeclaredMethod("runRegistry");
			m.setAccessible(true);
			m.invoke(null);
			m.setAccessible(false);
			StatList.onItemInit();
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
}
