package de.benji.naturoforumsmp.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocess implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        GlobalManager.getListenerManager().callListeners(Listeners.CommandPreprocess, e);
    }
}
