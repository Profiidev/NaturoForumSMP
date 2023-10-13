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

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Homes)) {
            commandSender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }

        if (!(commandSender instanceof Player) ){
            commandSender.sendMessage(CommandMessages.notAllowed);
            return false;
        }
        Player p = (Player) commandSender;
        HomeManager homeManager = HomesMain.getHomeManager();
        homeManager.setHome(p.getUniqueId(), p.getLocation());
        p.sendMessage("§eYour Home was set.");
        return false;
    }
}
