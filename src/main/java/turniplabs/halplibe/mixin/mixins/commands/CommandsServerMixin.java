package turniplabs.halplibe.mixin.mixins.commands;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.server.MinecraftServer;
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
@Environment(EnvType.SERVER)
@Mixin(value = Commands.class, remap = false)
public class CommandsServerMixin {
    @Shadow
    public static List<Command> commands;
    @Inject(method = "initServerCommands(Lnet/minecraft/server/MinecraftServer;)V", at = @At("TAIL"))
    private static void addServerCommands(MinecraftServer server, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        Field field = CommandHelper.class.getDeclaredField("serverCommands");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Function<AtomicReference<MinecraftServer>, Command>> serverCommands = (List<Function<AtomicReference<MinecraftServer>, Command>>) field.get(CommandHelper.class);
        field.setAccessible(false);
        for (Function<AtomicReference<MinecraftServer>, Command> f : serverCommands){
            commands.add(f.apply(new AtomicReference<>(server)));
        }
    }
}
