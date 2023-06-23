package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Swap implements Listener {
    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.Swap, e);
    }
}
