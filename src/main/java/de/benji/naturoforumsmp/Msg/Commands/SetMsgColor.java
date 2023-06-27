package de.benji.naturoforumsmp.Msg.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.Msg.Main.MsgInvs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetMsgColor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Msg)) {
            commandSender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }

        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.openInventory(MsgInvs.getColorInv(MsgInvs.MsgStylePos.FromTo));
        } else {
            commandSender.sendMessage(CommandMessages.notAllowed);
        }
        return false;
    }
}
