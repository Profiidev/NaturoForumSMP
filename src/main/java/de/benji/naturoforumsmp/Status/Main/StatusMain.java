package de.benji.naturoforumsmp.Status.Main;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.ConfigAPI.ConfigKey;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Status.Listener.StatusInvClick;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import de.benji.naturoforumsmp.Status.Util.StatusInvs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StatusMain {
    static StatusManager statusManager;

    public static void onEnable() {
        statusManager = new StatusManager();

        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.Status, Listeners.InvClick, StatusInvClick::onClick);

        StatusInvs.registerInvClicks();

        ConfigAPI configAPI = GlobalManager.getConfigAPI();
        HashMap<UUID, String> currentStatus = configAPI.loadUUIDStringHashMap(ConfigKey.StatusCurrent.key);
        HashMap<UUID, Boolean> isAFK = configAPI.loadUUIDBooleanHashMap(ConfigKey.StatusAfk.key);
        HashMap<UUID, Boolean> afkEnabled = configAPI.loadUUIDBooleanHashMap(ConfigKey.StatusAutoAfk.key);

        HashMap<UUID, StatusCache> caches = new HashMap<>();
        currentStatus.keySet().forEach(uuid -> caches.put(uuid, new StatusCache(new Status("", "", "", true), "", "", new HashMap<>(), 0, currentStatus.get(uuid), "", isAFK.get(uuid), afkEnabled.get(uuid), true)));
        statusManager.setStatusCaches(caches);

        HashMap<String, List<String>> statusData = configAPI.loadStringStringListHashMap(ConfigKey.StatusData.key);
        HashMap<String, HashMap<UUID, Boolean>> statusAccess = configAPI.loadStringUUIDBooleanHashMapHashMap(ConfigKey.StatusAccess.key);
        HashMap<String, Status> status = new HashMap<>();
        statusData.keySet().forEach(s -> status.put(s, new Status(s, statusData.get(s).get(0), statusData.get(s).get(1), Boolean.parseBoolean(statusData.get(s).get(2)), statusAccess.get(s) == null ? new HashMap<>() : statusAccess.get(s))));
        statusManager.setStatusData(status);
    }

    public static void onDisable() {
        ConfigAPI configAPI = GlobalManager.getConfigAPI();
        HashMap<UUID, StatusCache> caches = statusManager.getStatusCaches();

        HashMap<UUID, String> currentStatus = new HashMap<>();
        caches.keySet().forEach(u -> currentStatus.put(u, caches.get(u).currentStatus));
        HashMap<UUID, Boolean> isAFK = new HashMap<>();
        caches.keySet().forEach(u -> isAFK.put(u, caches.get(u).isAFK));
        HashMap<UUID, Boolean> afkEnabled = new HashMap<>();
        caches.keySet().forEach(u -> afkEnabled.put(u, caches.get(u).afkEnabled));

        configAPI.saveUUIDStringHashMap(ConfigKey.StatusCurrent.key, currentStatus);
        configAPI.saveUUIDBooleanHashMap(ConfigKey.StatusAfk.key, isAFK);
        configAPI.saveUUIDBooleanHashMap(ConfigKey.StatusAutoAfk.key, afkEnabled);

        HashMap<String, List<String>> statusData = new HashMap<>();
        HashMap<String, HashMap<UUID, Boolean>> statusAccess = new HashMap<>();
        for(String key: statusManager.getStatusData().keySet()) {
            Status status = statusManager.getStatusData().get(key);
            statusData.put(key, Arrays.asList(status.display, status.headURL, String.valueOf(status.isBlacklist)));
            if(!status.statusAllow.isEmpty())
                statusAccess.put(key, status.statusAllow);
        }

        configAPI.saveStringStringListHashMap(ConfigKey.StatusData.key, statusData);
        configAPI.saveStringUUIDBooleanHashMapHashMap(ConfigKey.StatusAccess.key, statusAccess);
    }

    public static StatusManager getStatusManager() {
        return statusManager;
    }
}
