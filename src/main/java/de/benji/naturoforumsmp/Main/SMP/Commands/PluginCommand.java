package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.Main.SMP.Util.SMPInvs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginCommand implements CommandExecutor {
	private final List<String> dev = GlobalManager.getSubpluginManager().getAllDevSubplugins();
	
	@Override
	public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (!sender.isOp() && sender instanceof Player) {
			sender.sendMessage(CommandMessages.notAllowed);
			return true;
		}
		Player p = (Player) sender;
		p.openInventory(SMPInvs.getPluginInv());
		return true;
	}
}
