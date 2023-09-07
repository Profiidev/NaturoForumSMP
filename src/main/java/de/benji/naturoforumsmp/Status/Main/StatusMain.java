package de.benji.naturoforumsmp.Status.Main;

import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.DataStoreAPI.DataStoreAPI;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Status.Listener.StatusInvClick;
import de.benji.naturoforumsmp.Status.Listener.StatusMove;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import de.benji.naturoforumsmp.Status.Util.StatusInvs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StatusMain {
    private static StatusManager statusManager;

    public static void onEnable() {
        statusManager = new StatusManager();

        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.Status, Listeners.InvClick, StatusInvClick::onClick);
        lm.addCallback(Subplugin.Status, Listeners.Move, StatusMove::onMove);

        StatusInvs.registerInvClicks();

        DataStoreAPI dataStoreAPI = GlobalManager.getDataStoreAPI();

        statusManager.setStatusCaches(dataStoreAPI.loadUUIDStatusCacheHashMap(DataKey.StatusCaches));

        HashMap<String, HashMap<UUID, Boolean>> statusAccess = dataStoreAPI.loadStringUUIDBooleanHashMapHashMap(DataKey.StatusAccess);
        statusManager.setStatusData(dataStoreAPI.loadStringStatusHashMap(DataKey.StatusData));

        for(String key: statusManager.getStatusData().keySet()) {
            Status status = statusManager.getStatusData().get(key);
            status.statusAllow = statusAccess.get(key) == null ? new HashMap<>() : statusAccess.get(key);
            statusManager.getStatusData().put(key, status);
        }

        statusManager.setAfkStatusKey(dataStoreAPI.loadString(DataKey.StatusAfk));
    }

    public static void onDisable() {
        HashMap<UUID, StatusCache> caches = statusManager.getStatusCaches();

        HashMap<UUID, String> currentStatus = new HashMap<>();
        caches.keySet().forEach(u -> currentStatus.put(u, caches.get(u).currentStatus));
        HashMap<UUID, Boolean> isAFK = new HashMap<>();
        caches.keySet().forEach(u -> isAFK.put(u, caches.get(u).isAFK));
        HashMap<UUID, Boolean> afkEnabled = new HashMap<>();
        caches.keySet().forEach(u -> afkEnabled.put(u, caches.get(u).afkEnabled));

        HashMap<String, List<String>> statusData = new HashMap<>();
        HashMap<String, HashMap<UUID, Boolean>> statusAccess = new HashMap<>();
        for(String key: statusManager.getStatusData().keySet()) {
            Status status = statusManager.getStatusData().get(key);
            statusData.put(key, Arrays.asList(status.display, status.headURL, String.valueOf(status.isBlacklist)));
            if(!status.statusAllow.isEmpty())
                statusAccess.put(key, status.statusAllow);
        }

        DataStoreAPI dataStoreAPI = GlobalManager.getDataStoreAPI();
        dataStoreAPI.saveUUIDStatusCacheHashMap(DataKey.StatusCaches, statusManager.getStatusCaches());
        dataStoreAPI.saveStringStatusHashMap(DataKey.StatusData, statusManager.getStatusData());
        dataStoreAPI.saveStringUUIDBooleanHashMapHashMap(DataKey.StatusAccess, statusAccess);
        dataStoreAPI.saveString(DataKey.StatusAfk, statusManager.getAfkStatusKey());
    }

    public static StatusManager getStatusManager() {
        return statusManager;
    }
}
