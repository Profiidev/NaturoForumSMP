package de.benji.naturoforumsmp.Main.SMP.TPS;

import de.benji.naturoforumsmp.API.GlobalManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class TpsTicker {
    public TpsTicker() {
        run ();
    }

    public void run() {
        new BukkitRunnable() {

            @Override
            public void run() {
                double[] tps = Bukkit.getTPS();
                for(Player p: Bukkit.getOnlinePlayers()) {
                    p.setPlayerListHeaderFooter(String.join("\n", Arrays.asList("§8---------------------------§r","§4§ka§r§7§lNaturoForum SMP§4§ka§r","§8---------------------------")),String.join("\n", Arrays.asList("§cYou§fTube§8: §6NaturoForum", "§6TPS: 1m:§a" + roundTo100(tps[0]) + " §65m:§a" + roundTo100(tps[1]) + " §615m:§a" + roundTo100(tps[2]))));
                }
            }

        }.runTaskTimer(GlobalManager.getInstance(), 20, 20);
    }
    
    public double roundTo100(double d) {
        double ret = Math.round(d * 100.0) / 100.0;
        return Math.min(ret, 20.0);
    }
}
