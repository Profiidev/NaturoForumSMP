package de.benji.naturoforumsmp.Homes.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.Homes.Main.HomeManager;
import de.benji.naturoforumsmp.Homes.Main.HomesMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Homes)) {
            commandSender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(CommandMessages.notAllowed);
            return true;
        }
        HomeManager homeManager = HomesMain.getHomeManager();
        Player p = (Player) commandSender;

        if(!homeManager.hasHome(p.getUniqueId())) {
            p.sendMessage("§cYou haven't set a home yet!");
            p.sendMessage("§cUse: /sethome");
            return true;
        }

        p.teleport(homeManager.getHome(p.getUniqueId()));
        p.sendMessage("§eTeleported.");
        return true;
    }
}
