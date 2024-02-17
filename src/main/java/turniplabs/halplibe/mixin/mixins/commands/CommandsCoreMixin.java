package turniplabs.halplibe.mixin.mixins.commands;

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

@Mixin(value = Commands.class, remap = false)
public class CommandsCoreMixin {
    @Shadow public static List<Command> commands;
    @SuppressWarnings("unchecked")
    @Inject(method = "initCommands()V", at = @At("TAIL"))
    private static void addCoreCommands(CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        Field field = CommandHelper.class.getDeclaredField("coreCommands");
        field.setAccessible(true);
        commands.addAll((List<Command>) field.get(CommandHelper.class));
        field.setAccessible(false);
    }
}
