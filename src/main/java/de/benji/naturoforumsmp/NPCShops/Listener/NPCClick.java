package de.benji.naturoforumsmp.NPCShops.Listener;

import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.NPCShops.Main.NPCManager;
import de.benji.naturoforumsmp.NPCShops.Main.NPCShopsMain;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class NPCClick {
    public static void onClick(Event event) {
        PlayerInteractEvent e = (PlayerInteractEvent) event;

        Player p = e.getPlayer();
        if(p.getItemInHand().getItemMeta() == null)
            return;

        if(!p.getItemInHand().getItemMeta().getDisplayName().equals(InvTitles.npcs_Shops))
            return;

        e.setCancelled(true);

        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        Location l = new Location(p.getWorld(), e.getClickedBlock().getX() + 0.5, e.getClickedBlock().getY() + 1, e.getClickedBlock().getZ() + 0.5);

        NPCManager manager = NPCShopsMain.getNpcManager();
        if(manager.getNpcs().isEmpty()) {
            manager.createNPC(EntityType.VILLAGER, l, p.getUniqueId(), Material.DIRT, Material.DIAMOND, 1, 1, 0, 0, InvTitles.npcs_Shops);
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            return;
        }

        for(NPC npc: manager.getNpcs()) {
            Location loc = npc.getBukkitEntity().getLocation();
            Location block = e.getClickedBlock().getLocation();

            if(block.getX() + 0.5 == loc.getX() && block.getY() + 1 == loc.getY() && block.getZ() + 0.5 == loc.getZ()) {
                p.sendMessage("§cBlock occupied");
                return;
            }
        }

        manager.createNPC(EntityType.VILLAGER, l, p.getUniqueId(), Material.DIRT, Material.DIAMOND, 1, 1, 0, 0, InvTitles.npcs_Shops);
        p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
    }
}
