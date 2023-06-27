package de.benji.naturoforumsmp.NPCShops.Listener;

import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.NPCShops.Main.NPCInvs;
import de.benji.naturoforumsmp.NPCShops.Main.NPCManager;
import de.benji.naturoforumsmp.NPCShops.Main.NPCShopsMain;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCEntityClick {
    public static void onClick(Event event) {
        PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;
        NPCManager manager = NPCShopsMain.getNpcManager();
        NPC npc = manager.findNPCByID(e.getRightClicked().getUniqueId());

        if(npc == null)
            return;

        e.setCancelled(true);

        if(npc.getOwner().equals(e.getPlayer().getUniqueId())) {
            manager.putLastOpenNPC(e.getPlayer().getUniqueId(), npc.getUUID());
            e.getPlayer().openInventory(NPCInvs.getOwnerInv(npc.getUUID()));
        } else {
            manager.putLastOpenNPC(e.getPlayer().getUniqueId(), npc.getUUID());
            e.getPlayer().openInventory(NPCInvs.getShopInv(npc.getUUID()));
        }
    }
}
