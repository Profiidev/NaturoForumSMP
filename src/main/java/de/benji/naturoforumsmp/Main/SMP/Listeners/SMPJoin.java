package de.benji.naturoforumsmp.Main.SMP.Listeners;

import de.benji.naturoforumsmp.Main.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SMPJoin {
    private static final List<UUID> joined = Main.getJoined();
    public static void onJoin(Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
        Player p = e.getPlayer();

        p.setPlayerListHeaderFooter(String.join("\n", Arrays.asList("§8---------------------------§r","§4§ka§r§7§lNaturoForum SMP§4§ka§r","§8---------------------------")),"§cYou§fTube§8: §6NaturoForum");


        if(joined.contains(p.getUniqueId()))
            return;

        p.sendTitle("§4§ka§r§6§lWelcome§4§ka", "§6§lto NaturoForum SMP", 20, 100, 20);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 0);
        p.sendMessage("§6We have some nice QoL-Features");
        p.sendMessage("§6For help use: §e/smphelp");

        joined.add(p.getUniqueId());
        Main.setJoined(joined);
    }
}
