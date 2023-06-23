package de.benji.naturoforumsmp.Main.SMP.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PortalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou can't use this command");
            return true;
        }
        Player p = (Player) sender;
        World.Environment e = p.getWorld().getEnvironment();
        Location l = p.getLocation();
        if(e.equals(World.Environment.THE_END)) {
            p.sendMessage("§No valid Dimension");
            return true;
        }
        if(e.equals(World.Environment.NORMAL)) {
            p.sendMessage("§6Nethercords: §a" + Math.round(l.getBlockX() / 8f) + "§6,§a " + l.getBlockY() + "§6,§a " + Math.round(l.getBlockZ() / 8f));
        } else if(e.equals(World.Environment.NETHER)) {
            p.sendMessage("§6Nethercords: §a" + Math.round(l.getBlockX() * 8f) + "§6,§a " + l.getBlockY() + "§6,§a " + Math.round(l.getBlockZ() * 8f));
        }

        return true;
    }
}
