package de.benji.naturoforumsmp.SpawnElytra.Listener;

import de.benji.naturoforumsmp.SpawnElytra.Main.ElytraManager;
import de.benji.naturoforumsmp.SpawnElytra.Main.SpawnElytraMain;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SpawnElytraDeath {
    public static void onDeath(Event event) {
        PlayerDeathEvent e = (PlayerDeathEvent) event;
        Player p = e.getPlayer();
        ElytraManager elytraManager = SpawnElytraMain.getElytraManager();

        if(elytraManager.hasSpawnElytra(p.getUniqueId()))
            elytraManager.dropChestplate(p);
    }
}
