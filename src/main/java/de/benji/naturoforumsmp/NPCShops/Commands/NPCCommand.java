package de.benji.naturoforumsmp.NPCShops.Commands;

import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.NPCShops.Main.NPCInvs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NPCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(CommandMessages.notAllowed);
            return true;
        }
        ((Player) commandSender).openInventory(NPCInvs.getSettingsInv(((Player) commandSender).getUniqueId()));
        return false;
    }
}
