package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.Quit, e);
        e.setQuitMessage("§6<< §b" + e.getPlayer().getName() + "§6 left");
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.Kick, e);
        e.setLeaveMessage("§6<< §b" + e.getPlayer().getName() + "§6 left");
    }
}
