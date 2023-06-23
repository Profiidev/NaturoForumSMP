package de.benji.naturoforumsmp.Status.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.Status.Util.StatusInvs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Status)) {
            sender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(CommandMessages.notAllowed);
        }
        Player p = (Player)sender;
        p.openInventory(StatusInvs.getMainInv(p.getUniqueId(), 0));
        return true;
    }
}
