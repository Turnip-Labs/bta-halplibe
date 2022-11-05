package turniplabs.halplibe.helper;

import net.minecraft.src.command.Command;
import net.minecraft.src.command.Commands;

public class CommandHelper {

    public static void createCommand(Command command) {
        Commands.commands.add(command);
    }
}
