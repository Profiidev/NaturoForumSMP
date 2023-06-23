package de.benji.naturoforumsmp.Sanddupe.Listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEvent;

public class SandDupePortal {
    public static void onPortal(Event event) {
        EntityPortalEvent e = (EntityPortalEvent) event;

        if(!e.getEntityType().equals(EntityType.FALLING_BLOCK) || !e.getFrom().getBlock().getType().equals(Material.END_PORTAL))
            return;

        Material m = ((FallingBlock) e.getEntity()).getMaterial();

        if(e.getFrom().getBlock().getRelative(1, 0, 0).getType().equals(Material.AIR))
            e.getFrom().getBlock().getRelative(1, 0, 0).setType(m);

        if(e.getFrom().getBlock().getRelative(-1, 0, 0).getType().equals(Material.AIR))
            e.getFrom().getBlock().getRelative(-1, 0, 0).setType(m);

        if(e.getFrom().getBlock().getRelative(0, 0, 1).getType().equals(Material.AIR))
            e.getFrom().getBlock().getRelative(0, 0, 1).setType(m);

        if(e.getFrom().getBlock().getRelative(0, 0, -1).getType().equals(Material.AIR))
            e.getFrom().getBlock().getRelative(0, 0, -1).setType(m);
    }
}
