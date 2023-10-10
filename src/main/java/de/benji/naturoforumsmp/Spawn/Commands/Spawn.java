package de.benji.naturoforumsmp.Spawn.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Spawn)) {
            sender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
            return true;
        }
        sender.sendMessage(CommandMessages.notAllowed);
        return false;
    }
}
