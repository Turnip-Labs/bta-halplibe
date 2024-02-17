package turniplabs.halplibe.mixin.mixins.commands;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.CommandHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
@Environment(EnvType.CLIENT)
@Mixin(value = Commands.class, remap = false)
public class CommandsClientMixin {
    @Shadow
    public static List<Command> commands;
    @Inject(method = "initClientCommands(Lnet/minecraft/client/Minecraft;)V", at = @At("TAIL"))
    private static void addClientCommands(Minecraft minecraft, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        Field field = CommandHelper.class.getDeclaredField("clientCommands");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Function<AtomicReference<Minecraft>, Command>> clientCommands = (List<Function<AtomicReference<Minecraft>, Command>>) field.get(CommandHelper.class);
        field.setAccessible(false);
        for (Function<AtomicReference<Minecraft>, Command> f : clientCommands){
            commands.add(f.apply(new AtomicReference<>(minecraft)));
        }
    }
}