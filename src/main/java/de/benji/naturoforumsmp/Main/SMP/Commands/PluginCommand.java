package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Messages.CommandMessages;
import de.benji.naturoforumsmp.API.Strings.UUIDs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PluginCommand implements CommandExecutor, TabCompleter {
	List<String> dev = GlobalManager.getSubpluginManager().getAllDevSubplugins();
	
	@Override
	public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		HashMap<String, Boolean> plugins = GlobalManager.getSubpluginManager().getAllSubpluginsStatus();

		if (!sender.isOp()) {
			sender.sendMessage(CommandMessages.notAllowed);
			return true;
		}
		if(args.length != 2) {
			return false;
		}

		switch(args[0]) {
			case "enable": {
				if(!plugins.containsKey(args[1])) {
					sender.sendMessage(CommandMessages.pluginCommand_WrongPluginName);
					return true;
				}
				if(dev.contains(args[1]) && sender instanceof Player) {
					if(!((Player) sender).getUniqueId().equals(UUIDs.benji)) {
						sender.sendMessage(CommandMessages.pluginCommand_WrongPermissions);
						return true;
					}
					enableSubplugin(sender, args[1]);
					return true;
				}
				if(dev.contains(args[1])) {
					sender.sendMessage(CommandMessages.pluginCommand_WrongPermissions);
					return true;
				}
				enableSubplugin(sender, args[1]);
				return true;
			}
			case "disable": {
				if(!plugins.containsKey(args[1])) {
					sender.sendMessage(CommandMessages.pluginCommand_WrongPluginName);
					return true;
				}
				disableSubplugin(sender, args[1]);
				return true;
			}
			case "status": {
				if(plugins.containsKey(args[1])) {
					sender.sendMessage("§6" + args[1] + " is currently " + (plugins.get(args[1]) ? "§aenabled§6." : "§cdisabled§6."));
					return true;
				}

				if(args[1].equals("all")) {
					sender.sendMessage("§6Status for every Subplugin:");
					for(String s: plugins.keySet()) {
						sender.sendMessage("§6" + s + " is currently " + (plugins.get(s) ? "§aenabled§6." : "§cdisabled§6."));
					}
					return true;
				}
				sender.sendMessage(CommandMessages.pluginCommand_WrongPluginName);
				return true;
			}
			default: {
				return false;
			}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		HashMap<String, Boolean> plugins = GlobalManager.getSubpluginManager().getAllSubpluginsStatus();

		List<String> l = new ArrayList<>();
		List<String> completions = new ArrayList<>();

		if(!sender.isOp())
			return null;

		switch (args.length) {
			case 1: {
				l.add("enable");
				l.add("disable");
				l.add("status");

				StringUtil.copyPartialMatches(args[0], l, completions);
				break;
			}
			case 2: {
				for(String s: plugins.keySet()) {
					if(args[0].equalsIgnoreCase("enable")) {
						if(!plugins.get(s))
							l.add(s);
					} else if(args[0].equalsIgnoreCase("disable")) {
						if(plugins.get(s))
							l.add(s);
					} else {
						l.add(s);
					}
				}
				if(args[0].equalsIgnoreCase("status")) {
					l.add("all");
				}

				StringUtil.copyPartialMatches(args[1], l, completions);
				break;
			}
			default: break;
		}
		Collections.sort(completions);
		return completions;
	}

	public void enableSubplugin(CommandSender sender, String name) {
		sender.sendMessage("§6" + name + " §aenabled§6!");
		GlobalManager.getSubpluginManager().enableSubplugin(Subplugin.fromString(name));
	}

	public void disableSubplugin(CommandSender sender, String name) {
		sender.sendMessage("§6" + name + " §cdisabled§6!");
		GlobalManager.getSubpluginManager().disableSubplugin(Subplugin.fromString(name));
	}
}
