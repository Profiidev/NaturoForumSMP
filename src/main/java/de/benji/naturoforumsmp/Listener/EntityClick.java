package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityClick implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.EntityClick, e);
    }
}
