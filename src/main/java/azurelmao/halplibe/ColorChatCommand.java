package azurelmao.halplibe;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandHandler;
import net.minecraft.src.command.CommandSender;

public class ColorChatCommand extends Command {

    public ColorChatCommand() {
        super("cchat", "cch");
    }

    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
        if (strings.length < 2) {
            return false;
        }

        if (!commandSender.isPlayer()) {
            return false;
        }

        String text = "";
        for (int i = 1; i < strings.length; ++i) {
            text += " " + strings[i];
        }

        EntityPlayer player = commandSender.getPlayer();
        commandSender.sendMessage("<" + player.username + ">" + ChatColor.getColor(strings[0]) + text);

        return true;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return false;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
        commandSender.sendMessage("/cchat <color> <text>");
    }
}
