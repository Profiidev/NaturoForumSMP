package de.benji.naturoforumsmp.API.DataBaseAPI;

import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.Msg.Main.MsgStyles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseAPI {
    private final MySQL mySQL;

    public DatabaseAPI(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public boolean isConnected() {
        return mySQL.isConnected();
    }

    public void disconnect() {
        if(isConnected())
            mySQL.disconnect();
    }

    public void saveString(String key, String s) {
        mySQL.createTable("Strings", Arrays.asList("`key` VARCHAR(100) NOT NULL", "`value` VARCHAR(100)"));
        mySQL.addEntryWithDupe("Strings", Arrays.asList("key", "value"), Arrays.asList(key, s), Collections.singletonList("value"), Collections.singletonList(s));
    }

    public void saveUUIDList(String key, List<UUID> list) {
        mySQL.createTable(key, Collections.singletonList("`key` VARCHAR(100) NOT NULL"));
        mySQL.clearTable(key);
        list.forEach(uuid -> mySQL.addEntry(key, Collections.singletonList("key"), Collections.singletonList(uuid.toString())));
    }

    public void saveLocationList(String key, List<Location> list) {
        mySQL.createTable(key, Arrays.asList("`key` INT NOT NULL", "`world` VARCHAR(100)", "`x` DOUBLE", "`y` DOUBLE", "`z` DOUBLE", "`yaw` DOUBLE", "`pitch` DOUBLE"));
        mySQL.clearTable(key);
        for(int i = 0; i < list.size(); i++) {
            Location loc = list.get(i);
            mySQL.addEntry(key, Arrays.asList("key", "world", "x", "y", "z", "yaw", "pitch"),
                    Arrays.asList(String.valueOf(i), loc.getWorld().getName(), String.valueOf(loc.x()), String.valueOf(loc.y()), String.valueOf(loc.z()), String.valueOf(loc.getYaw()), String.valueOf(loc.getPitch())));
        }
    }

    public void saveStringBooleanHashMap(String key, HashMap<String, Boolean> map) {
        mySQL.createTable(key, Arrays.asList("`key` VARCHAR(100) NOT NULL", "`value` TINYINT(1)"));
        mySQL.clearTable(key);
        map.keySet().forEach(k -> mySQL.addEntry(key, Arrays.asList("key", "value"), Arrays.asList(k, String.valueOf(map.get(k) ? 1 : 0))));
    }

    public void saveStringStatusHashMap(String key, HashMap<String, Status> map) {
        mySQL.createTable(key, Arrays.asList("`key` VARCHAR(100) NOT NULL", "`display` VARCHAR(100)", "`headURL` VARCHAR(200)", "`isBlacklist` TINYINT(1)"));
        mySQL.clearTable(key);
        map.keySet().forEach(k -> {
            Status status = map.get(k);
            mySQL.addEntry(key, Arrays.asList("key", "display", "headURL", "isBlacklist"), Arrays.asList(status.key, status.display, status.headURL, String.valueOf(status.isBlacklist ? 1 : 0)));
        });
    }

    public void saveStringUUIDBooleanHashMapHashMap(String key, HashMap<String, HashMap<UUID, Boolean>> map) {
        mySQL.createTable(key, Arrays.asList("`key` INT NOT NULL", "`name` VARCHAR(100)", "`uuid` VARCHAR(100)", "`show` TINYINT(1)"));
        mySQL.clearTable(key);

        AtomicInteger i = new AtomicInteger();
        map.keySet().forEach(k -> {
            HashMap<UUID, Boolean> value = map.get(k);
            value.keySet().forEach(uuid -> {
                mySQL.addEntry(key, Arrays.asList("key", "name", "uuid", "show"), Arrays.asList(String.valueOf(i.get()), k, uuid.toString(), String.valueOf(value.get(uuid) ? 1 : 0)));
                i.getAndIncrement();
            });
        });
    }

    public void saveUUIDStatusCacheHashMap(String key, HashMap<UUID, StatusCache> map) {
        mySQL.createTable(key, Arrays.asList("`key` VARCHAR(100)", "`current` VARCHAR(100)", "`isAFK` TINYINT(1)", "`AutoAFK` TINYINT(1)"));
        mySQL.clearTable(key);

        map.keySet().forEach(k -> {
            StatusCache cache = map.get(k);
            mySQL.addEntry(key, Arrays.asList("key", "current", "isAFK", "AutoAFK"), Arrays.asList(k.toString(), cache.currentStatus, String.valueOf(cache.isAFK ? 1 : 0), String.valueOf(cache.afkEnabled ? 1 : 0)));
        });
    }

    public void saveUUIDMsgStylesArrayHashMap(String key, HashMap<UUID, MsgStyles[]> styles) {
        mySQL.createTable(key, Arrays.asList("`key` VARCHAR(100)", "`style1` VARCHAR(100)", "`style2` VARCHAR(100)", "`style3` VARCHAR(100)", "`style4` VARCHAR(100)", "`style5` VARCHAR(100)", "`style6` VARCHAR(100)"));
        mySQL.clearTable(key);

        styles.keySet().forEach(uuid -> {
            MsgStyles[] style = styles.get(uuid);
            mySQL.addEntry(key, Arrays.asList("key", "style1", "style2", "style3", "style4", "style5", "style6"), Arrays.asList(uuid.toString(), style[0].key, style[1].key, style[2].key, style[3].key, style[4].key, style[5].key));
        });
    }

    public void saveUUIDPermissionListHashMap(String key, HashMap<UUID, List<Permission>> map) {
        List<String> perms = new ArrayList<>(Collections.singletonList("`key` VARCHAR(100)"));
        List<String> keys = new ArrayList<>(Collections.singletonList("key"));
        List<Permission> permissions = new ArrayList<>();
        Arrays.asList(Permission.values()).forEach(p -> {
            perms.add("`" + p.key + "` VARCHAR(100)");
            keys.add(p.key);
            permissions.add(p);
        });
        mySQL.createTable(key, perms);
        mySQL.clearTable(key);

        map.keySet().forEach(k -> {
            List<Permission> perm = map.get(k);
            List<String> hasPerm = new ArrayList<>(Collections.singletonList(k.toString()));
            permissions.forEach(p -> {
                hasPerm.add(String.valueOf(perm.contains(p) ? 1 : 0));
            });
            mySQL.addEntry(key, keys, hasPerm);
        });
    }

    public void saveUUIDStringHashMap(String key, HashMap<UUID, String> map) {
        mySQL.createTable(key, Arrays.asList("`key` VARCHAR(100)", "`value` VARCHAR(100)"));
        mySQL.clearTable(key);

        map.keySet().forEach(k -> {
            mySQL.addEntry(key, Arrays.asList("key", "value"), Arrays.asList(k.toString(), map.get(k)));
        });
    }

    public void saveNPCList(String key, List<NPC> list) {
        mySQL.createTable(key,
                Arrays.asList("`key` INT NOT NULL", "`owner` VARCHAR(100)", "`type` VARCHAR(100)", "`sellItem` VARCHAR(100)", "`payItem` VARCHAR(100)", "`itemsPerSell` INT", "`itemsPerPay` INT", "`sellStock` INT", "`payStock` INT", "`name` VARCHAR(100)",
                "`world` VARCHAR(100)", "`x` DOUBLE", "`y` DOUBLE", "`z` DOUBLE", "`yaw` DOUBLE"));
        mySQL.clearTable(key);

        AtomicInteger i = new AtomicInteger();
        list.forEach(npc -> {
            Location loc = npc.getBukkitEntity().getLocation();
            mySQL.addEntry(key,
                    Arrays.asList("key", "owner", "type", "sellItem", "payItem", "itemsPerSell", "itemsPerPay", "sellStock", "payStock", "name",
                    "world", "x", "y", "z", "yaw"),
                    Arrays.asList(String.valueOf(i.get()), npc.getOwner().toString(), npc.getType().toShortString(), npc.getSellItem().name(), npc.getPayItem().name(), String.valueOf(npc.getItemsPerSell()), String.valueOf(npc.getItemsPerPay()), String.valueOf(npc.getSellStock()), String.valueOf(npc.getPayStock()), npc.getCustomName().getString(),
                    loc.getWorld().getName(), String.valueOf(loc.x()), String.valueOf(loc.y()), String.valueOf(loc.z()), String.valueOf(npc.getYHeadRot())));
            i.getAndIncrement();
        });
    }

    public void saveUUIDLocationHashMap(String key, HashMap<UUID, Location> map) {
        mySQL.createTable(key,
                Arrays.asList("`key` VARCHAR(100) NOT NULL", "`world` VARCHAR(100)", "`x` DOUBLE", "`y` DOUBLE", "`z` DOUBLE", "`yaw` DOUBLE", "`pitch` DOUBLE"));
        mySQL.clearTable(key);

        map.keySet().forEach(uuid -> {
            Location loc = map.get(uuid);
            mySQL.addEntry(key, Arrays.asList("key", "world", "x", "y", "z", "yaw", "pitch"), Arrays.asList(uuid.toString(), loc.getWorld().getName(), String.valueOf(loc.x()), String.valueOf(loc.y()), String.valueOf(loc.z()), String.valueOf(loc.getYaw()), String.valueOf(loc.getPitch())));
        });
    }
}
