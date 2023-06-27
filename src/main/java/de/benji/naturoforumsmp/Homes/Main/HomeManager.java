package de.benji.naturoforumsmp.Homes.Main;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class HomeManager {
    private HashMap<UUID, Location> homes;

    public HomeManager() {
        homes = new HashMap<>();
    }

    public void setHomes(HashMap<UUID, Location> homes) {
        this.homes = homes;
    }

    public HashMap<UUID, Location> getHomes() {
        return homes;
    }

    public boolean hasHome(UUID uuid) {
        return homes.containsKey(uuid);
    }

    public Location getHome(UUID uuid) {
        return homes.get(uuid);
    }

    public void setHome(UUID uuid, Location location) {
        homes.put(uuid, location);
    }
}
