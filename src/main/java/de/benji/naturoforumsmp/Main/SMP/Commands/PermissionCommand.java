package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.API.PermissionAPI.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PermissionCommand implements CommandExecutor, TabCompleter {
    private PermissionManager permissionManager = GlobalManager.getPermissionManager();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        permissionManager = GlobalManager.getPermissionManager();
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 2) {
                Player t = Bukkit.getPlayer(args[0]);
                if(t != null) {
                    if(args[1].equals("list")) {
                        p.sendMessage("§a" + t.getName() + "§6 has the following Permissions:");
                        for(Permission perm: permissionManager.getPlayerPermissions(t)) {
                            p.sendMessage(perm.key);
                        }
                    } else {
                        p.sendMessage("§6Please use §f/permission <Player> add/remove/list <permission>");
                    }
                } else {
                    p.sendMessage("§cPlayer not found!");
                }
            } else if (args.length == 3) {
                Player t = Bukkit.getPlayer(args[0]);
                if(t != null) {
                    if(args[1].equals("add")) {
                        Permission perm = Permission.fromString(args[2]);
                        if(perm != null) {
                            if(permissionManager.hasPlayerPermission(t, perm)) {
                                p.sendMessage("§cPlayer already has this Permission!");
                            } else {
                                permissionManager.addPlayerPermission(t, perm);
                            }
                        } else {
                            p.sendMessage("§cPermission not found!");
                        }
                    } else if (args[1].equals("remove")) {
                        Permission perm = Permission.fromString(args[2]);
                        if(perm != null) {
                            permissionManager.removePlayerPermission(t, perm);
                        } else {
                            p.sendMessage("§cPermission not found!");
                        }
                    } else {
                        p.sendMessage("§6Please use §f/permission <Player> add/remove/list <permission>");
                    }
                } else {
                    p.sendMessage("§cPlayer not found!");
                }
            } else {
                p.sendMessage("§6Please use §f/permission <Player> add/remove/list <permission>");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        permissionManager = GlobalManager.getPermissionManager();
        List<String> tab = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1: {
                for(Player p: Bukkit.getOnlinePlayers()) {
                    tab.add(p.getName());
                }
                StringUtil.copyPartialMatches(args[0], tab, completions);
                break;
            }
            case 2: {
                tab.add("add");
                tab.add("list");
                tab.add("remove");

                StringUtil.copyPartialMatches(args[1], tab, completions);
                break;
            }
            case 3: {
                if(commandSender instanceof Player) {
                    switch (args[1]) {
                        case "add": {
                            try {
                                List<Permission> perm = permissionManager.getPlayerPermissions(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
                                for(Permission p: Permission.values()) {
                                    if(!perm.contains(p)) {
                                        tab.add(p.key);
                                    }
                                }
                            } catch (Exception ignored) {}

                            StringUtil.copyPartialMatches(args[2], tab, completions);
                            break;
                        }
                        case "remove": {
                            try {
                                List<Permission> perm = permissionManager.getPlayerPermissions(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
                                perm.forEach(p -> {
                                    tab.add(p.key);
                                });
                            } catch (Exception ignored) {}

                            StringUtil.copyPartialMatches(args[2], tab, completions);
                            break;
                        }
                    }
                }
                break;
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
