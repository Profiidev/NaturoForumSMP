package de.benji.naturoforumsmp.Status.Listener;

import de.benji.naturoforumsmp.Status.Main.StatusMain;
import de.benji.naturoforumsmp.Status.Main.StatusManager;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class StatusMove {
    public static void onMove(Event event) {
        PlayerMoveEvent e = (PlayerMoveEvent) event;

        StatusManager manager = StatusMain.getStatusManager();
        StatusCache cache = manager.getStatusCaches().get(e.getPlayer().getUniqueId());
        cache.afkTime = 0;

        if(!cache.isAFK)
            return;

        cache.isAFK = false;
        if(!cache.currentStatus.equals(""))
            manager.setPlayerStatus(cache.currentStatus, e.getPlayer());
        else
            StatusManager.setPlayerPrefixSuffix("", null, e.getPlayer());
        manager.getStatusCaches().put(e.getPlayer().getUniqueId(), cache);
    }
}
