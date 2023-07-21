package turniplabs.halplibe.helper;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;

public class CommandHelper {

    // Make your command extend ClientCommand if you want it to work on multiplayer
    // It will require a Minecraft class instance, so use Minecraft.getMinecraft(Minecraft.class) to do so.

    public static void createCommand(Command command) {
        if (Commands.commands.size() > 0) Commands.commands.add(command);
    }
}
