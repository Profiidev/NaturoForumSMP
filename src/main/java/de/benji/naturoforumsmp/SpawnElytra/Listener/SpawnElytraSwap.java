package de.benji.naturoforumsmp.SpawnElytra.Listener;

import de.benji.naturoforumsmp.SpawnElytra.Main.ElytraManager;
import de.benji.naturoforumsmp.SpawnElytra.Main.SpawnElytraMain;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SpawnElytraSwap {
    public static void onSwap(Event event) {
        PlayerSwapHandItemsEvent e = (PlayerSwapHandItemsEvent) event;

        Player p = e.getPlayer();

        if(p.getWorld().getEnvironment() != World.Environment.NORMAL)
            return;

        if(p.getLocation().distance(Bukkit.getWorld("world").getSpawnLocation()) > 50)
            return;

        ElytraManager elytraManager = SpawnElytraMain.getElytraManager();
        if(elytraManager.hasBoosted(p.getUniqueId()))
            return;

        if(!p.isGliding())
            return;

        elytraManager.boosted(p.getUniqueId());
        p.setVelocity(p.getEyeLocation().getDirection().multiply(3));
        e.setCancelled(true);
    }
}
