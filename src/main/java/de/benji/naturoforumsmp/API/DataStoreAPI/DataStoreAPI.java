package de.benji.naturoforumsmp.API.DataStoreAPI;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.DataBaseAPI.DatabaseAPI;
import de.benji.naturoforumsmp.API.DataBaseAPI.MySQL;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.Msg.Main.MsgStyles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataStoreAPI {
    private final boolean isDBEnabled;
    private final ConfigAPI configAPI;
    private DatabaseAPI databaseAPI;

    public DataStoreAPI(boolean isDBEnabled, ConfigAPI configAPI) {
        this.isDBEnabled = isDBEnabled;
        this.configAPI = configAPI;

        MySQL mySQL = loadDBInfo();

        if(isDBEnabled && mySQL != null) {
            mySQL.connect();
            this.databaseAPI = new DatabaseAPI(mySQL);
        }
    }

    private MySQL loadDBInfo() {
        String ip = GlobalManager.getInstance().getConfig().getString("Database.ip");
        int port = GlobalManager.getInstance().getConfig().getInt("Database.port");
        String database = GlobalManager.getInstance().getConfig().getString("Database.database");
        String username = GlobalManager.getInstance().getConfig().getString("Database.username");
        String password = GlobalManager.getInstance().getConfig().getString("Database.password");

        if(ip == null || port == 0 || database == null || username == null || password == null) {
            configAPI.saveString("Database.ip", ip == null ? "" : ip);
            configAPI.saveString("Database.port", String.valueOf(port));
            configAPI.saveString("Database.database", database == null ? "" : database);
            configAPI.saveString("Database.username", username == null ? "" : username);
            configAPI.saveString("Database.password", password == null ? "" : password);

            GlobalManager.getInstance().saveConfig();
            if(isDBEnabled)
                Bukkit.getLogger().warning("Couldn't connect to Database. Not all Login-Info is set properly.");

            return null;
        }

        return new MySQL(ip, port, database, username, password);
    }

    public void disconnect() {
        if(databaseAPI != null)
            databaseAPI.disconnect();
    }

    private boolean isConnected() {
        if(databaseAPI != null)
            return databaseAPI.isConnected();
        return false;
    }

    public void saveString(DataKey key, String s) {
        if(isConnected()) {
            databaseAPI.saveString(key.databaseKey, s);
        }
        configAPI.saveString(key.configKey, s);
    }

    public void saveUUIDList(DataKey key, List<UUID> list) {
        if(list == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDList(key.databaseKey, list);
        }
        configAPI.saveUUIDList(key.configKey, list);
    }

    public void saveLocationList(DataKey key, List<Location> list) {
        if(list == null)
            return;

        if(isConnected()) {
            databaseAPI.saveLocationList(key.databaseKey, list);
        }
        configAPI.saveLocationList(key.configKey, list);
    }

    public void saveStringBooleanHashMap(DataKey key, HashMap<String, Boolean> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveStringBooleanHashMap(key.databaseKey, map);
        }
        configAPI.saveStringBooleanHashMap(key.configKey, map);
    }

    public void saveStringStatusHashMap(DataKey key, HashMap<String, Status> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveStringStatusHashMap(key.databaseKey, map);
        }
        configAPI.saveStringStatusHashMap(key.configKey, map);
    }

    public void saveStringUUIDBooleanHashMapHashMap(DataKey key, HashMap<String, HashMap<UUID, Boolean>> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveStringUUIDBooleanHashMapHashMap(key.databaseKey, map);
        }
        configAPI.saveStringUUIDBooleanHashMapHashMap(key.configKey, map);
    }

    public void saveUUIDStatusCacheHashMap(DataKey key, HashMap<UUID, StatusCache> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDStatusCacheHashMap(key.databaseKey, map);
        }
        configAPI.saveUUIDStatusCacheHashMap(key.configKey, map);
    }

    public void saveUUIDMsgStylesArrayHashMap(DataKey key, HashMap<UUID, MsgStyles[]> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDMsgStylesArrayHashMap(key.databaseKey, map);
        }
        configAPI.saveUUIDMsgStylesArrayHashMap(key.configKey, map);
    }

    public void saveUUIDPermissionListHashMap(DataKey key, HashMap<UUID, List<Permission>> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDPermissionListHashMap(key.databaseKey, map);
        }
        configAPI.saveUUIDPermissionListHashMap(key.configKey, map);
    }

    public void saveNPCList(DataKey key, List<NPC> list) {
        if(list == null)
            return;

        if(isConnected()) {
            databaseAPI.saveNPCList(key.databaseKey, list);
        }
        configAPI.saveNPCList(key.configKey, list);
    }

    public void saveUUIDStringHashMap(DataKey key, HashMap<UUID, String> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDStringHashMap(key.databaseKey, map);
        }
        configAPI.saveUUIDStringHashMap(key.configKey, map);
    }

    public void saveUUIDLocationHashMap(DataKey key, HashMap<UUID, Location> map) {
        if(map == null)
            return;

        if(isConnected()) {
            databaseAPI.saveUUIDLocationHashMap(key.databaseKey, map);
        }
        configAPI.saveUUIDLocationHashMap(key.configKey, map);
    }

    public String loadString(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadString(key);
        }
        return configAPI.loadString(key.configKey);
    }

    public List<UUID> loadUUIDList(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDList(key);
        }
        return configAPI.loadUUIDList(key.configKey);
    }

    public List<Location> loadLocationList(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadLocationList(key);
        }
        return configAPI.loadLocationList(key.configKey);
    }

    public HashMap<String, Boolean> loadStringBooleanHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadStringBooleanHashMap(key);
        }
        return configAPI.loadStringBooleanHashMap(key.configKey);
    }

    public HashMap<String, Status> loadStringStatusHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadStringStatusHashMap(key);
        }
        return configAPI.loadStringStatusHashMap(key.configKey);
    }

    public HashMap<String, HashMap<UUID, Boolean>> loadStringUUIDBooleanHashMapHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadStringUUIDBooleanHashMapHashMap(key);
        }
        return configAPI.loadStringUUIDBooleanHashMapHashMap(key.configKey);
    }

    public HashMap<UUID, StatusCache> loadUUIDStatusCacheHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDStatusCacheHashMap(key);
        }
        return configAPI.loadUUIDStatusCacheHashMap(key.configKey);
    }

    public HashMap<UUID, MsgStyles[]> loadUUIDMsgStylesArrayHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDMsgStylesArrayHashMap(key);
        }
        return configAPI.loadUUIDMsgStylesArrayHashMap(key.configKey);
    }

    public HashMap<UUID, List<Permission>> loadUUIDPermissionListHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDPermissionListHashMap(key);
        }
        return configAPI.loadUUIDPermissionListHashMap(key.configKey);
    }

    public HashMap<UUID, String> loadUUIDStringHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDStringHashMap(key);
        }
        return configAPI.loadUUIDStringHashMap(key.configKey);
    }

    public void loadNPCList(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //databaseAPI.loadNPCList(key);
            //return;
        }
        configAPI.loadNPCList(key.configKey);
    }

    public HashMap<UUID, Location> loadUUIDLocationHashMap(DataKey key) {
        if(isDBEnabled && isConnected()) {
            //return databaseAPI.loadUUIDLocationHashMap(key);
        }
        return configAPI.loadUUIDLocationHashMap(key.configKey);
    }
}
