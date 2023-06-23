package de.benji.naturoforumsmp.SpawnElytra.Listener;

import de.benji.naturoforumsmp.SpawnElytra.Main.ElytraManager;
import de.benji.naturoforumsmp.SpawnElytra.Main.SpawnElytraMain;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpawnElytraQuit {
    public static void onQuit(Event event) {
        PlayerQuitEvent e = (PlayerQuitEvent) event;
        ElytraManager elytraManager = SpawnElytraMain.getElytraManager();

        if(elytraManager.hasSpawnElytra(e.getPlayer().getUniqueId()))
            elytraManager.removeElytra(e.getPlayer());
    }

    public static void onKick(Event event) {
        PlayerKickEvent e = (PlayerKickEvent) event;
        ElytraManager elytraManager = SpawnElytraMain.getElytraManager();

        if(elytraManager.hasSpawnElytra(e.getPlayer().getUniqueId()))
            elytraManager.removeElytra(e.getPlayer());
    }
}
