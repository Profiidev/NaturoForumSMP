package de.benji.naturoforumsmp.Msg.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.Msg.Main.MsgColorManager;
import de.benji.naturoforumsmp.Msg.Main.MsgMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Msg)) {
            commandSender.sendMessage(CommandMessages.pluginNotEnabled);
            return true;
        }

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(CommandMessages.notAllowed);
            return true;
        }
        if(strings.length == 0)
            return false;

        Player p = (Player) commandSender;
        MsgColorManager mcm = MsgMain.getMsgColorManager();
        if(mcm.getLastMsg(p.getUniqueId()) == null) {
            p.sendMessage("§cNo recent messages");
            return true;
        }

        Player target = Bukkit.getPlayer(mcm.getLastMsg(p.getUniqueId()));

        p.sendMessage(mcm.colorMsg(p.getUniqueId(), String.join(" ", strings), true, p.getName(), target.getName()));
        target.sendMessage(mcm.colorMsg(target.getUniqueId(), String.join(" ", strings), false, p.getName(), target.getName()));

        return true;
    }
}
