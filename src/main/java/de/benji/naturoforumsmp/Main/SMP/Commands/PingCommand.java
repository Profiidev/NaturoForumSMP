package de.benji.naturoforumsmp.Main.SMP.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            int ping = ((CraftPlayer) sender).getPing();
            sender.sendMessage("§6Your Ping is: §" + (ping < 100 ? "a" : (ping < 250 ? "6" : "c")) + ping);
        }
        return true;
    }
}
