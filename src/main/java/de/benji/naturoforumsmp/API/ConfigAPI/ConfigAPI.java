package de.benji.naturoforumsmp.API.ConfigAPI;

import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.Msg.Main.MsgStyles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.NPCShops.Main.NPCShopsMain;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigAPI {
    private final JavaPlugin plugin;

    public ConfigAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveString(String key, String s) {
        plugin.getConfig().set(key, s);
    }

    public void saveUUIDList(String key, List<UUID> list) {
        ArrayList<String> list1 = new ArrayList<>();
        for(UUID uuid: list) {
            list1.add(uuid.toString());
        }
        plugin.getConfig().set(key, list1);
    }

    public void saveLocationList(String key, List<Location> list) {
        plugin.getConfig().set(key, "");
        for(int i = 0; i < list.size(); i++) {
            Location l = list.get(i);
            plugin.getConfig().set(key + "." + i + ".world", l.getWorld().getName());
            plugin.getConfig().set(key + "." + i + ".x", l.x());
            plugin.getConfig().set(key + "." + i + ".y", l.y());
            plugin.getConfig().set(key + "." + i + ".z", l.z());
            plugin.getConfig().set(key + "." + i + ".yaw", l.getYaw());
            plugin.getConfig().set(key + "." + i + ".pitch", l.getPitch());
        }
    }

    public void saveStringBooleanHashMap(String key, HashMap<String, Boolean> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(s -> plugin.getConfig().set(key + "." + s, map.get(s)));
    }

    public void saveStringStatusHashMap(String key, HashMap<String, Status> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            Status status = map.get(k);
            plugin.getConfig().set(key + "." + k + ".display", status.display);
            plugin.getConfig().set(key + "." + k + ".headURL", status.headURL);
            plugin.getConfig().set(key + "." + k + ".isBlacklist", status.isBlacklist);
        });
    }

    public void saveStringUUIDBooleanHashMapHashMap(String key, HashMap<String, HashMap<UUID, Boolean>> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            HashMap<UUID, Boolean> perms = map.get(k);
            perms.keySet().forEach(uuid -> {
                plugin.getConfig().set(key + "." + k + "." + uuid.toString() + ".show", perms.get(uuid));
            });
        });
    }

    public void saveUUIDStatusCacheHashMap(String key, HashMap<UUID, StatusCache> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            StatusCache cache = map.get(k);
            plugin.getConfig().set(key + "." + k + ".current", cache.currentStatus);
            plugin.getConfig().set(key + "." + k + ".isAFK", cache.isAFK);
            plugin.getConfig().set(key + "." + k + ".AutoAFK", cache.afkEnabled);
        });
    }

    public void saveUUIDMsgStylesArrayHashMap(String key, HashMap<UUID, MsgStyles[]> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            MsgStyles[] style = map.get(k);
            plugin.getConfig().set(key + "." + k + ".style1", style[0].key);
            plugin.getConfig().set(key + "." + k + ".style2", style[1].key);
            plugin.getConfig().set(key + "." + k + ".style3", style[2].key);
            plugin.getConfig().set(key + "." + k + ".style4", style[3].key);
            plugin.getConfig().set(key + "." + k + ".style5", style[4].key);
            plugin.getConfig().set(key + "." + k + ".style6", style[5].key);
        });
    }

    public void saveUUIDPermissionListHashMap(String key, HashMap<UUID, List<Permission>> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            List<Permission> perms = map.get(k);
            Arrays.asList(Permission.values()).forEach(perm -> {
                plugin.getConfig().set(key + "." + k.toString() + "." + perm.key, perms.contains(perm));
            });
        });
    }

    public void saveUUIDStringHashMap(String key, HashMap<UUID, String> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> plugin.getConfig().set(key + "." + k.toString(), map.get(k)));
    }

    public void saveNPCList(String key, List<NPC> list) {
        plugin.getConfig().set(key, "");
        AtomicInteger i = new AtomicInteger();

        List<NPC> newList = new ArrayList<>(list);

        newList.forEach(npc -> {
            plugin.getConfig().set(key + "." + i.get() + ".owner", npc.getOwner().toString());
            plugin.getConfig().set(key + "." + i.get() + ".type", npc.getType().toShortString());
            plugin.getConfig().set(key + "." + i.get() + ".sellItem", npc.getSellItem().name());
            plugin.getConfig().set(key + "." + i.get() + ".payItem", npc.getPayItem().name());
            plugin.getConfig().set(key + "." + i.get() + ".itemsPerSell", npc.getItemsPerSell());
            plugin.getConfig().set(key + "." + i.get() + ".itemsPerPay", npc.getItemsPerPay());
            plugin.getConfig().set(key + "." + i.get() + ".sellStock", npc.getSellStock());
            plugin.getConfig().set(key + "." + i.get() + ".payStock", npc.getPayStock());
            plugin.getConfig().set(key + "." + i.get() + ".name", npc.getCustomName().getString());

            Location loc = npc.getBukkitEntity().getLocation();
            plugin.getConfig().set(key + "." + i.get() + ".world", loc.getWorld().getName());
            plugin.getConfig().set(key + "." + i.get() + ".x", loc.x());
            plugin.getConfig().set(key + "." + i.get() + ".y", loc.y());
            plugin.getConfig().set(key + "." + i.get() + ".z", loc.z());
            plugin.getConfig().set(key + "." + i.get() + ".yaw", npc.getYHeadRot());
            i.getAndIncrement();

            NPCShopsMain.getNpcManager().deleteNPC(npc);
        });
    }

    public void saveUUIDLocationHashMap(String key, HashMap<UUID, Location> map) {
        plugin.getConfig().set(key, "");
        map.keySet().forEach(k -> {
            Location loc = map.get(k);
            plugin.getConfig().set(key + "." + k + ".world", loc.getWorld().getName());
            plugin.getConfig().set(key + "." + k + ".x", loc.x());
            plugin.getConfig().set(key + "." + k + ".y", loc.y());
            plugin.getConfig().set(key + "." + k + ".z", loc.z());
            plugin.getConfig().set(key + "." + k + ".yaw", loc.getYaw());
            plugin.getConfig().set(key + "." + k + ".pitch", loc.getPitch());
        });
    }

    public String loadString(String key) {
        return plugin.getConfig().getString(key) != null ? plugin.getConfig().getString(key) : "";
    }

    public List<UUID> loadUUIDList(String key) {
        List<UUID> list = new ArrayList<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            list.add(UUID.fromString(s));
        }
        return list;
    }

    public List<Location> loadLocationList(String key) {
        List<Location> list = new ArrayList<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return list;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            list.add(new Location(
                    Bukkit.getWorld(plugin.getConfig().getString(key + "." + k + ".world")),
                    plugin.getConfig().getDouble(key + "." + k + ".x"),
                    plugin.getConfig().getDouble(key + "." + k + ".y"),
                    plugin.getConfig().getDouble(key + "." + k + ".z"),
                    (float) plugin.getConfig().getDouble(key + "." + k + ".yaw"),
                    (float) plugin.getConfig().getDouble(key + "." + k + ".pitch")));
        });
        return list;
    }

    public HashMap<String, Boolean> loadStringBooleanHashMap(String key) {
        HashMap<String, Boolean> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(k, plugin.getConfig().getBoolean(key + "." + k));
        });
        return map;
    }

    public HashMap<String, Status> loadStringStatusHashMap(String key) {
        HashMap<String, Status> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(k, new Status(k,
                    plugin.getConfig().getString(key + "." + k + ".display"),
                    plugin.getConfig().getString(key + "." + k + ".headURL"),
                    plugin.getConfig().getBoolean(key + "." + k + ".isBlacklist")));
        });
        return map;
    }

    public HashMap<String, HashMap<UUID, Boolean>> loadStringUUIDBooleanHashMapHashMap(String key) {
        HashMap<String, HashMap<UUID, Boolean>> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            HashMap<UUID, Boolean> data = new HashMap<>();
            plugin.getConfig().getConfigurationSection(key + "." + k).getKeys(false).forEach(uuid -> {
                data.put(UUID.fromString(uuid), plugin.getConfig().getBoolean(key + "." + k + ".show"));
            });
            map.put(k, data);
        });
        return map;
    }

    public HashMap<UUID, StatusCache> loadUUIDStatusCacheHashMap(String key) {
        HashMap<UUID, StatusCache> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(UUID.fromString(k), new StatusCache(
                    new Status("", "", "", true), "", "", new HashMap<>(), 0,
                    plugin.getConfig().getString(key + "." + k + ".current"), "",
                    plugin.getConfig().getBoolean(key + "." + k + ".isAFK"),
                    plugin.getConfig().getBoolean(key + "." + k + ".AutoAFK"), true));
        });
        return map;
    }

    public HashMap<UUID, MsgStyles[]> loadUUIDMsgStylesArrayHashMap(String key) {
        HashMap<UUID, MsgStyles[]> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(UUID.fromString(k), new MsgStyles[]{
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style1")),
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style2")),
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style3")),
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style4")),
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style5")),
                    MsgStyles.fromString(plugin.getConfig().getString(key + "." + k + ".style6"))});
        });
        return map;
    }

    public HashMap<UUID, List<Permission>> loadUUIDPermissionListHashMap(String key) {
        HashMap<UUID, List<Permission>> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            List<Permission> perms = new ArrayList<>();
            plugin.getConfig().getConfigurationSection(key + "." + k).getKeys(false).forEach(perm -> {
                if(plugin.getConfig().getBoolean(key + "." + k + "." + perm))
                    perms.add(Permission.fromString(perm));
            });
            map.put(UUID.fromString(k), perms);
        });
        return map;
    }

    public HashMap<UUID, String> loadUUIDStringHashMap(String key) {
        HashMap<UUID, String> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(UUID.fromString(k), plugin.getConfig().getString(key + "." + k));
        });
        return map;
    }

    public void loadNPCList(String key) {
        if(plugin.getConfig().getConfigurationSection(key) == null)
            return;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            NPCShopsMain.getNpcManager().createNPC(
                    (EntityType<? extends Mob>) EntityType.byString(plugin.getConfig().getString(key + "." + k + ".type")).get(),
                    new Location(
                            Bukkit.getWorld(plugin.getConfig().getString(key + "." + k + ".world")),
                            plugin.getConfig().getDouble(key + "." + k + ".x"),
                            plugin.getConfig().getDouble(key + "." + k + ".y"),
                            plugin.getConfig().getDouble(key + "." + k + ".z")),
                    UUID.fromString(plugin.getConfig().getString(key + "." + k + ".owner")),
                    Material.matchMaterial(plugin.getConfig().getString(key + "." + k + ".sellItem")),
                    Material.matchMaterial(plugin.getConfig().getString(key + "." + k + ".payItem")),
                    plugin.getConfig().getInt(key + "." + k + ".itemsPerSell"),
                    plugin.getConfig().getInt(key + "." + k + ".itemsPerPay"),
                    plugin.getConfig().getInt(key + "." + k + ".sellStock"),
                    plugin.getConfig().getInt(key + "." + k + ".payStock"),
                    plugin.getConfig().getString(key + "." + k + ".name"),
                    (float) plugin.getConfig().getDouble(key + "." + k + ".yaw"));
        });
    }

    public HashMap<UUID, Location> loadUUIDLocationHashMap(String key) {
        HashMap<UUID, Location> map = new HashMap<>();

        if(plugin.getConfig().getConfigurationSection(key) == null)
            return map;

        plugin.getConfig().getConfigurationSection(key).getKeys(false).forEach(k -> {
            map.put(UUID.fromString(k), new Location(
                    Bukkit.getWorld(plugin.getConfig().getString(key + "." + k + ".world")),
                    plugin.getConfig().getDouble(key + "." + k + ".x"),
                    plugin.getConfig().getDouble(key + "." + k + ".y"),
                    plugin.getConfig().getDouble(key + "." + k + ".z"),
                    (float) plugin.getConfig().getDouble(key + "." + k + ".yaw"),
                    (float) plugin.getConfig().getDouble(key + "." + k + ".pitch")));
        });
        return map;
    }
}
