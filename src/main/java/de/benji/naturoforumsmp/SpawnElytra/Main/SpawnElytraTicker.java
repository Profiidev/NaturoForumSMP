package de.benji.naturoforumsmp.SpawnElytra.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnElytraTicker {
    public SpawnElytraTicker() {
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.SpawnElytra))
                    return;

                ElytraManager elytraManager = SpawnElytraMain.getElytraManager();

                for(Player p: Bukkit.getOnlinePlayers()) {
                    if(p.isOnGround()) {

                        elytraManager.resetBoost(p.getUniqueId());

                        if(elytraManager.hasSpawnElytra(p.getUniqueId()) && (p.getLocation().distance(Bukkit.getWorld("world").getSpawnLocation()) > 50)) {
                            elytraManager.removeElytra(p);
                            continue;
                        }
                    }

                    if(p.getWorld().getEnvironment() != World.Environment.NORMAL)
                        continue;

                    if(p.getLocation().distance(Bukkit.getWorld("world").getSpawnLocation()) > 50)
                        continue;

                    if(p.getInventory().getChestplate() != null)
                        if(p.getInventory().getChestplate().getType().equals(Material.ELYTRA))
                            continue;

                    elytraManager.giveElytra(p);
                }
            }
        }.runTaskTimer(GlobalManager.getInstance(), 0, 1);
    }
}
