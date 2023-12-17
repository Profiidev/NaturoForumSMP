package de.benji.naturoforumsmp.SpawnElytra.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.SpawnElytra.Listener.SpawnElytraDeath;
import de.benji.naturoforumsmp.SpawnElytra.Listener.SpawnElytraQuit;
import de.benji.naturoforumsmp.SpawnElytra.Listener.SpawnElytraSwap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpawnElytraMain {
    private static ElytraManager elytraManager;
    private static boolean isRunning = true;

    public static void onEnable() {
        elytraManager = new ElytraManager();
        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.SpawnElytra, Listeners.Swap, SpawnElytraSwap::onSwap);
        lm.addCallback(Subplugin.SpawnElytra, Listeners.Quit, SpawnElytraQuit::onQuit);
        lm.addCallback(Subplugin.SpawnElytra, Listeners.Kick, SpawnElytraQuit::onKick);
        lm.addCallback(Subplugin.SpawnElytra, Listeners.Death, SpawnElytraDeath::onDeath);
    }

    public static void onDisable() {
        isRunning = false;
        for(Player p: Bukkit.getOnlinePlayers()) {
            ElytraManager elytraManager = SpawnElytraMain.getElytraManager();

            if(elytraManager.hasSpawnElytra(p.getUniqueId()))
                elytraManager.removeElytra(p);
        }
    }

    public static ElytraManager getElytraManager() {
        return elytraManager;
    }

    public static boolean isRunning() {
        return isRunning;
    }
}
