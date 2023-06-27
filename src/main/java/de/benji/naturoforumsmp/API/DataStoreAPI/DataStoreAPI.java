package de.benji.naturoforumsmp.API.DataStoreAPI;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.DataBaseAPI.DatabaseAPI;
import de.benji.naturoforumsmp.API.DataBaseAPI.MySQL;
import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.Msg.Main.MsgStyles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataStoreAPI {
    private final boolean isDBEnabled;
    private final ConfigAPI configAPI;
    private DatabaseAPI databaseAPI;

    public DataStoreAPI(boolean isDBEnabled, ConfigAPI configAPI, MySQL mySQL) {
        this.isDBEnabled = isDBEnabled;
        this.configAPI = configAPI;
        if(isDBEnabled) {
            mySQL.connect();
            this.databaseAPI = new DatabaseAPI(mySQL);
        }
    }

    public void disconnect() {
        databaseAPI.disconnect();
    }

    public void saveString(DataKey key, String s) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveString(key.databaseKey, s);
            return;
        }
        configAPI.saveString(key.configKey, s);
    }

    public void saveUUIDList(DataKey key, List<UUID> list) {
        if(list == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDList(key.databaseKey, list);
            return;
        }
        configAPI.saveUUIDList(key.configKey, list);
    }

    public void saveLocationList(DataKey key, List<Location> list) {
        if(list == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveLocationList(key.databaseKey, list);
            return;
        }
        configAPI.saveLocationList(key.configKey, list);
    }

    public void saveStringBooleanHashMap(DataKey key, HashMap<String, Boolean> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveStringBooleanHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveStringBooleanHashMap(key.configKey, map);
    }

    public void saveStringStatusHashMap(DataKey key, HashMap<String, Status> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveStringStatusHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveStringStatusHashMap(key.configKey, map);
    }

    public void saveStringUUIDBooleanHashMapHashMap(DataKey key, HashMap<String, HashMap<UUID, Boolean>> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveStringUUIDBooleanHashMapHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveStringUUIDBooleanHashMapHashMap(key.configKey, map);
    }

    public void saveUUIDStatusCacheHashMap(DataKey key, HashMap<UUID, StatusCache> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDStatusCacheHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveUUIDStatusCacheHashMap(key.configKey, map);
    }

    public void saveUUIDMsgStylesArrayHashMap(DataKey key, HashMap<UUID, MsgStyles[]> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDMsgStylesArrayHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveUUIDMsgStylesArrayHashMap(key.configKey, map);
    }

    public void saveUUIDPermissionListHashMap(DataKey key, HashMap<UUID, List<Permission>> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDPermissionListHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveUUIDPermissionListHashMap(key.configKey, map);
    }

    public void saveNPCList(DataKey key, List<NPC> list) {
        if(list == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveNPCList(key.databaseKey, list);
            return;
        }
        configAPI.saveNPCList(key.configKey, list);
    }

    public void saveUUIDStringHashMap(DataKey key, HashMap<UUID, String> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDStringHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveUUIDStringHashMap(key.configKey, map);
    }

    public void saveUUIDLocationHashMap(DataKey key, HashMap<UUID, Location> map) {
        if(map == null)
            return;

        if(isDBEnabled && databaseAPI.isConnected()) {
            databaseAPI.saveUUIDLocationHashMap(key.databaseKey, map);
            return;
        }
        configAPI.saveUUIDLocationHashMap(key.configKey, map);
    }

    public String loadString(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadString(key);
        }
        return configAPI.loadString(key.configKey);
    }

    public List<UUID> loadUUIDList(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDList(key);
        }
        return configAPI.loadUUIDList(key.configKey);
    }

    public List<Location> loadLocationList(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadLocationList(key);
        }
        return configAPI.loadLocationList(key.configKey);
    }

    public HashMap<String, Boolean> loadStringBooleanHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadStringBooleanHashMap(key);
        }
        return configAPI.loadStringBooleanHashMap(key.configKey);
    }

    public HashMap<String, Status> loadStringStatusHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadStringStatusHashMap(key);
        }
        return configAPI.loadStringStatusHashMap(key.configKey);
    }

    public HashMap<String, HashMap<UUID, Boolean>> loadStringUUIDBooleanHashMapHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadStringUUIDBooleanHashMapHashMap(key);
        }
        return configAPI.loadStringUUIDBooleanHashMapHashMap(key.configKey);
    }

    public HashMap<UUID, StatusCache> loadUUIDStatusCacheHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDStatusCacheHashMap(key);
        }
        return configAPI.loadUUIDStatusCacheHashMap(key.configKey);
    }

    public HashMap<UUID, MsgStyles[]> loadUUIDMsgStylesArrayHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDMsgStylesArrayHashMap(key);
        }
        return configAPI.loadUUIDMsgStylesArrayHashMap(key.configKey);
    }

    public HashMap<UUID, List<Permission>> loadUUIDPermissionListHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDPermissionListHashMap(key);
        }
        return configAPI.loadUUIDPermissionListHashMap(key.configKey);
    }

    public HashMap<UUID, String> loadUUIDStringHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDStringHashMap(key);
        }
        return configAPI.loadUUIDStringHashMap(key.configKey);
    }

    public void loadNPCList(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //databaseAPI.loadNPCList(key);
            //return;
        }
        configAPI.loadNPCList(key.configKey);
    }

    public HashMap<UUID, Location> loadUUIDLocationHashMap(DataKey key) {
        if(isDBEnabled && databaseAPI.isConnected()) {
            //return databaseAPI.loadUUIDLocationHashMap(key);
        }
        return configAPI.loadUUIDLocationHashMap(key.configKey);
    }
}
