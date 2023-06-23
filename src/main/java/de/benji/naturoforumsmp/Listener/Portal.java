package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class Portal implements Listener {
    @EventHandler
    public void onPortal(EntityPortalEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.Portal, e);
    }
}
