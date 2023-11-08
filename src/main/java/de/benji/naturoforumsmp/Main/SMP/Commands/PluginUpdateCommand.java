package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PluginUpdateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!sender.isOp()) {
            sender.sendMessage(CommandMessages.notAllowed);
            return true;
        }

        sender.sendMessage("§eStarting update...");
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("wget http://nacktebusen.de/file/NaturoForumSMP.jar -O plugins/NaturoForumSMP.jar");
        } catch (IOException e) {
            sender.sendMessage("§cError while updating");
            return true;
        }
        Bukkit.reload();
        sender.sendMessage("§eUpdate finished.");

        return true;
    }
}
