package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if(e.getCurrentItem() == null)
            return;
        if(e.getCurrentItem().getItemMeta() == null)
            return;
        String display = e.getCurrentItem().getItemMeta().getDisplayName();

        GlobalManager.getListenerManager().callListeners(Listeners.InvClick, e);
        GlobalManager.getInventoryManager().requestCallback(display, title, e);
    }
}
