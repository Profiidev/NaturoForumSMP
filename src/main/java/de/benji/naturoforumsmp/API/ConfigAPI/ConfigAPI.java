package de.benji.naturoforumsmp.API.ConfigAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ConfigAPI {
    private final JavaPlugin plugin;
    private final Character splitChar = '~';

    public ConfigAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveString(String key, String s) {
        plugin.getConfig().set(key, s);
    }

    public void saveUUIDList(String key, List<UUID> list) {
        if(list != null) {
            if(!list.isEmpty()) {
                ArrayList<String> list1 = new ArrayList<>();
                for(UUID uuid: list) {
                    list1.add(uuid.toString());
                }
                plugin.getConfig().set(key, list1);
            }
        }
    }

    public void saveLocationList(String key, List<Location> list) {
        if(list != null) {
            if(!list.isEmpty()) {
                ArrayList<String> list1 = new ArrayList<>();
                for(Location l: list) {
                    String lData = l.getWorld().getName() + splitChar +
                            l.getX() + splitChar +
                            l.getY() + splitChar +
                            l.getZ() + splitChar +
                            l.getYaw() + splitChar +
                            l.getPitch();
                    list1.add(lData);
                }
                plugin.getConfig().set(key, list1);
            }
        }
    }

    public void saveLocationArrayList(String key, List<Location[]> list) {
        if(list != null) {
            if(!list.isEmpty()) {
                ArrayList<String> list1 = new ArrayList<>();
                for(Location[] locs: list) {
                    StringBuilder locsData = new StringBuilder();
                    for(Location l: locs) {
                        locsData.append(l.getWorld().getName()).append(splitChar);
                        locsData.append(l.getX()).append(splitChar);
                        locsData.append(l.getY()).append(splitChar);
                        locsData.append(l.getZ()).append(splitChar);
                        locsData.append(l.getYaw()).append(splitChar);
                        locsData.append(l.getPitch()).append(splitChar);
                    }
                    locsData.replace(locsData.length() - 1, locsData.length() - 1, "");
                    list1.add(locsData.toString());
                }
                plugin.getConfig().set(key, list1);
            }
        }
    }

    public void saveStringBooleanHashMap(String key, HashMap<String, Boolean> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(String s: map.keySet()) {
                    list.add(s + splitChar + map.get(s));
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveStringStringListHashMap(String key, HashMap<String, List<String>> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(String s: map.keySet()) {
                    StringBuilder sData = new StringBuilder();
                    sData.append(s).append(splitChar);
                    for(String s1: map.get(s)) {
                        sData.append(s1).append(splitChar);
                    }
                    sData.replace(sData.length() - 1, sData.length() - 1, "");

                    list.add(sData.toString());
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveStringUUIDBooleanHashMapHashMap(String key, HashMap<String, HashMap<UUID, Boolean>> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(String s: map.keySet()) {
                    StringBuilder sData = new StringBuilder();
                    sData.append(s).append(splitChar);
                    for(UUID uuid: map.get(s).keySet()) {
                        sData.append(uuid.toString()).append(splitChar);
                        sData.append(map.get(s).get(uuid)).append(splitChar);
                    }
                    sData.replace(sData.length() - 1, sData.length() - 1, "");
                    list.add(sData.toString());
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveUUIDIntegerHashMap(String key, HashMap<UUID, Integer> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(UUID uuid: map.keySet()) {
                    list.add(uuid.toString() + splitChar + map.get(uuid));
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveUUIDBooleanHashMap(String key, HashMap<UUID, Boolean> map)  {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(UUID uuid: map.keySet()) {
                    list.add(uuid.toString() + splitChar + map.get(uuid));
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveUUIDStringHashMap(String key, HashMap<UUID, String> map)  {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(UUID uuid: map.keySet()) {
                    list.add(uuid.toString() + splitChar + map.get(uuid));
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveUUIDStringArrayHashMap(String key, HashMap<UUID, String[]> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(UUID uuid: map.keySet()) {
                    StringBuilder mapData = new StringBuilder();
                    mapData.append(uuid.toString()).append(splitChar);
                    for(String s: map.get(uuid)) {
                        mapData.append(s).append(splitChar);
                    }
                    mapData.replace(mapData.length() - 1, mapData.length() - 1, "");
                    list.add(mapData.toString());
                }
                plugin.getConfig().set(key, list);
            }
        }
    }

    public void saveUUIDStringListHashMap(String key, HashMap<UUID, List<String>> map) {
        if(map != null) {
            if(!map.isEmpty()) {
                ArrayList<String> list = new ArrayList<>();
                for(UUID uuid: map.keySet()) {
                    StringBuilder mapData = new StringBuilder();
                    mapData.append(uuid.toString()).append(splitChar);
                    for(String s: map.get(uuid)) {
                        mapData.append(s).append(splitChar);
                    }
                    mapData.replace(mapData.length() - 1, mapData.length() - 1, "");
                    list.add(mapData.toString());
                }
                plugin.getConfig().set(key, list);
            }
        }
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
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            World world = Bukkit.getWorld(sData[0]);
            double x = Double.parseDouble(sData[1]);
            double y = Double.parseDouble(sData[2]);
            double z = Double.parseDouble(sData[3]);
            float yaw = Float.parseFloat(sData[4]);
            float pitch = Float.parseFloat(sData[5]);

            list.add(new Location(world, x, y, z, yaw, pitch));
        }
        return list;
    }

    public List<Location[]> loadLocationArrayList(String key) {
        List<Location[]> list = new ArrayList<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            int counter = 0;
            ArrayList<Location> locs = new ArrayList<>();

            while (sData.length > counter + 6) {
                World world = Bukkit.getWorld(sData[counter]);
                double x = Double.parseDouble(sData[counter + 1]);
                double y = Double.parseDouble(sData[counter + 2]);
                double z = Double.parseDouble(sData[counter + 3]);
                float yaw = Float.parseFloat(sData[counter + 4]);
                float pitch = Float.parseFloat(sData[counter + 5]);

                locs.add(new Location(world, x, y, z, yaw, pitch));
                counter += 6;
            }
            list.add((Location[]) locs.toArray());
        }
        return list;
    }

    public HashMap<String, Boolean> loadStringBooleanHashMap(String key) {
        HashMap<String, Boolean> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            map.put(sData[0], Boolean.parseBoolean(sData[1]));
        }
        return map;
    }

    public HashMap<String, List<String>> loadStringStringListHashMap(String key) {
        HashMap<String, List<String>> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            String[] sValue = new String[sData.length - 1];
            System.arraycopy(sData, 1, sValue, 0, sData.length - 1);
            map.put(sData[0], Arrays.asList(sValue));
        }
        return map;
    }

    public HashMap<String, HashMap<UUID, Boolean>> loadStringUUIDBooleanHashMapHashMap(String key) {
        HashMap<String, HashMap<UUID, Boolean>> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            HashMap<UUID, Boolean> sValue = new HashMap<>();
            for(int i = 1; i <= sData.length - 1; i = i + 2) {
                sValue.put(UUID.fromString(sData[i]), Boolean.parseBoolean(sData[i + 1]));
            }
            map.put(sData[0], sValue);
        }
        return map;
    }

    public HashMap<UUID, Integer> loadUUIDIntegerHashMap(String key) {
        HashMap<UUID, Integer> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            map.put(UUID.fromString(sData[0]), Integer.parseInt(sData[1]));
        }
        return map;
    }

    public HashMap<UUID, Boolean> loadUUIDBooleanHashMap(String key) {
        HashMap<UUID, Boolean> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            map.put(UUID.fromString(sData[0]), Boolean.parseBoolean(sData[1]));
        }
        return map;
    }

    public HashMap<UUID, String> loadUUIDStringHashMap(String key) {
        HashMap<UUID, String> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            if(sData.length == 2) {
                map.put(UUID.fromString(sData[0]), sData[1]);
            } else {
                map.put(UUID.fromString(sData[0]), "");
            }
        }
        return map;
    }

    public HashMap<UUID, String[]> loadUUIDStringArrayHashMap(String key) {
        HashMap<UUID, String[]> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            String[] sValue = new String[sData.length - 1];
            System.arraycopy(sData, 1, sValue, 0, sData.length - 1);
            map.put(UUID.fromString(sData[0]), sValue);
        }
        return map;
    }

    public HashMap<UUID, List<String>> loadUUIDStringListHashMap(String key) {
        HashMap<UUID, List<String>> map = new HashMap<>();
        for(String s: plugin.getConfig().getStringList(key)) {
            String[] sData = s.split(String.valueOf(splitChar));
            String[] sValue = new String[sData.length - 1];
            System.arraycopy(sData, 1, sValue, 0, sData.length - 1);
            map.put(UUID.fromString(sData[0]), Arrays.asList(sValue));
        }
        return map;
    }
}
