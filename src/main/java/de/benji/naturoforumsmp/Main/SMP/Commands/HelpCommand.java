package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HelpCommand implements CommandExecutor, TabCompleter {

	private final HashMap<String, Boolean> plugins = GlobalManager.getSubpluginManager().getAllSubpluginsStatus();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage("§4§ka§r§6This is a Plugin by §2Abzocker123§4§ka");
				p.sendMessage("§6There are currently these Subplugins active:");
				
				for(String s: plugins.keySet()) {
					if(plugins.get(s)) {
						p.sendMessage("§e" + s);
					}
				}
				
				p.sendMessage("§6Use §e/smphelp <Subplugin> §6for more help");
				
				if(p.getUniqueId().equals(UUID.fromString("9b7e3ec8-1875-41ff-9320-62428b3bb393"))) {
					try {
						if(p.getItemInHand().getType().equals(Material.POTATO)) {
							p.sendMessage(String.valueOf(p.getWorld().getSeed()));
						}
					} catch(Exception e) {}
				}
				
			} else if(args.length == 1) {
				try {
					if(plugins.get(args[0])) {
						switch(args[0].toLowerCase()) {
						case "msg": {
							p.sendMessage("§6With §e/setmsgcolor §6you can set the Colors for each Part of the Msgmessage");
							p.sendMessage("§6It adds the §e/r §6Command, too");
							break;
						}
						case "sanddupe": {
							p.sendMessage("§6This Subplugin re-adds Fallingblock-Dupers");
							break;
						}
						case "status": {
							p.sendMessage("§6With §e/status §6you can choose your personal Status which is shown in the Tablist");
							break;
						}
						case "worldtracker": {
							p.sendMessage("§6This Subplugin shows in the Tablist in which Dimension each Player is at the Moment");
							break;
						}
						case "npcshops": {
							p.sendMessage("§6With a Diamond, 4 Blazepowder and 4 Leather you can craft your own custom NPC to sell Items");
							p.sendMessage("§6You can select which Item you want to sell and which Item you want to get for it");
							p.sendMessage("§6You can set the look of the Shop, too");
							p.sendMessage("§6If you want your Profit to be automaticly moved into your Enderchest our in a Customchest which can be opend by §eShift + Right Click §6a Chest u can use §e/npc");
							break;
						}
						case "carpetduper": {
							p.sendMessage("§6This Subplugin adds a crafable Carpetduper because Paper fixed it");
							p.sendMessage("§6You have to make a Redstoneclock to power the Duper");
							break;
						}
						case "nick": {
							p.sendMessage("§6With this Subplugin you can use §e/nick <Name> §6to change your Name to whatever you want");
							break;
						}
						default: {
							p.sendMessage("§cThats no active Subplugin!");
							break;
						}
						}
					} else {
						p.sendMessage("§cThats no active Subplugin!");
					}
				} catch (Exception e1) {
					p.sendMessage("§cThats no active Subplugin!");
				}
			} else {
				p.sendMessage("§cPlease use: §6/smphelp <subplugin>§c!");
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		List<String> l = new ArrayList<>();
		
		if(args.length == 1) {
			for(String s: plugins.keySet()) {
				if(plugins.get(s)) {
					l.add(s);
				}
			}
		}
		
		return l;
	}
}
