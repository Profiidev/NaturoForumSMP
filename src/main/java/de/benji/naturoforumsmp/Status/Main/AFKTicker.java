package de.benji.naturoforumsmp.Status.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AFKTicker {
    public AFKTicker() {
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!GlobalManager.getSubpluginManager().isPluginEnabled(Subplugin.Status))
                    return;

                StatusManager statusManager = StatusMain.getStatusManager();
                HashMap<UUID, StatusCache> caches = statusManager.getStatusCaches();
                String afkStatusKey = statusManager.getAfkStatusKey();

                for(Player p: Bukkit.getOnlinePlayers()) {
                    StatusCache cache = caches.get(p.getUniqueId());
                    if(cache == null)
                        caches.put(p.getUniqueId(), new StatusCache(new Status("", "", "", true), "", "", new HashMap<>(), 1, "", "", false, true, true));

                    if(afkStatusKey == null)
                        continue;
                    cache.afkTime++;
                    if(!(cache.afkTime >= 60*5 && cache.afkEnabled && !cache.isAFK))
                        continue;

                    cache.isAFK = true;
                    statusManager.setPlayerStatus(afkStatusKey, p);
                }
            }
        }.runTaskTimer(GlobalManager.getInstance(), 1, 20);
    }
}
