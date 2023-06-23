package de.benji.naturoforumsmp.API.PermissionAPI;

import org.bukkit.entity.Player;

import java.util.*;

public class PermissionManager {

    HashMap<UUID, List<String>> permissions;

    public PermissionManager() {
        permissions = new HashMap<>();
    }

    public PermissionManager(HashMap<UUID, List<String>> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPlayerPermission(Player p, Permission per) {
        if(permissions.containsKey(p.getUniqueId())) {
            return permissions.get(p.getUniqueId()).contains(per.key);
        }
        return false;
    }

    public boolean hasPlayerPermission(UUID uuid, Permission per) {
        if(permissions.containsKey(uuid)) {
            return permissions.get(uuid).contains(per.key);
        }
        return false;
    }

    public void addPlayerPermission(Player p, Permission per) {
        if(permissions.containsKey(p.getUniqueId())) {
            List<String> perm = permissions.get(p.getUniqueId());
            perm.add(per.key);
            permissions.put(p.getUniqueId(), perm);
        } else {
            permissions.put(p.getUniqueId(), Collections.singletonList(per.key));
        }
    }

    public void addPlayerPermission(UUID uuid, Permission per) {
        if(permissions.containsKey(uuid)) {
            List<String> perm = permissions.get(uuid);
            perm.add(per.key);
            permissions.put(uuid, perm);
        } else {
            permissions.put(uuid, Collections.singletonList(per.key));
        }
    }

    public void removePlayerPermission(Player p, Permission per) {
        if(permissions.containsKey(p.getUniqueId())) {
            List<String> perm = permissions.get(p.getUniqueId());
            if(perm == null)
                System.out.println("hi");
            List<String> perm1 = new ArrayList<>();
            for(String s: perm) {
                if(!Objects.equals(s, per.key)) {
                    perm1.add(s);
                }
            }
            permissions.put(p.getUniqueId(), perm1);
        }
    }

    public void removePlayerPermission(UUID uuid, Permission per) {
        if(permissions.containsKey(uuid)) {
            List<String> perm = permissions.get(uuid);
            perm.remove(per.key);
            permissions.put(uuid, perm);
        }
    }

    public List<String> getPlayerPermissions(Player p) {
        if(!permissions.isEmpty()) {
            if(permissions.containsKey(p.getUniqueId())) {
                return  permissions.get(p.getUniqueId());
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> getPlayerPermissions(UUID uuid) {
        if(!permissions.isEmpty()) {
            if(permissions.containsKey(uuid)) {
                return  permissions.get(uuid);
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public HashMap<UUID, List<String>> getPermissions() {
        return permissions;
    }
}
