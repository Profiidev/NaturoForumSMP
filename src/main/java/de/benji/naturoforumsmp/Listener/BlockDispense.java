package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class BlockDispense implements Listener {
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.BlockDispense, e);
    }
}
