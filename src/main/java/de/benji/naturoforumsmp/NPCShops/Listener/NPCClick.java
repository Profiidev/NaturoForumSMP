package de.benji.naturoforumsmp.NPCShops.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NPCClick {
    public static void onClick(Event event) {
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        Player p = e.getPlayer();
        NPCManager manager = NPCShopsMain.getNpcManager();

        if(manager.getRedeemType(p.getUniqueId()) != null && e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            if(manager.getRedeemType(p.getUniqueId()).equals(NPCManager.ItemRedeemType.EC)&& e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
                putItemsInEC(p);

        if(p.getItemInHand().getItemMeta() == null)
            return;

        if(!p.getItemInHand().getItemMeta().getDisplayName().equals(InvTitles.npcs_Shops))
            return;

        e.setCancelled(true);

        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        Location l = new Location(p.getWorld(), e.getClickedBlock().getX() + 0.5, e.getClickedBlock().getY() + 1, e.getClickedBlock().getZ() + 0.5);

        if(manager.getNpcs().isEmpty()) {
            manager.createNPC(EntityType.VILLAGER, l, p.getUniqueId(), Material.DIRT, Material.DIAMOND, 1, 1, 0, 0, InvTitles.npcs_Shops, 0);
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

        manager.createNPC(EntityType.VILLAGER, l, p.getUniqueId(), Material.DIRT, Material.DIAMOND, 1, 1, 0, 0, InvTitles.npcs_Shops, 0);
        p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
    }

    private static void putItemsInEC(Player p) {
        List<NPC> npcs = new ArrayList<>();

        NPCShopsMain.getNpcManager().getNpcs().forEach(npc -> {
            if(npc.getOwner().equals(p.getUniqueId()))
                npcs.add(npc);
        });

        Inventory ec = p.getEnderChest();
        InvAPI invAPI = GlobalManager.getInvAPI();

        for(NPC npc: npcs) {
            Material mat = npc.getPayItem();
            if(npc.getPayStock() == 0)
                continue;

            int maxStack = mat.getMaxStackSize();
            for(int i = 0; i < 27; i++) {
                ItemStack is = ec.getItem(i);

                if(is == null) {
                    is = invAPI.createIS(mat, Math.min(maxStack, npc.getPayStock()));
                    npc.setPayStock(npc.getPayStock() - Math.min(maxStack, npc.getPayStock()));
                    ec.setItem(i, is);
                    continue;
                }

                if(is.getType().equals(Material.AIR)) {
                    is = invAPI.createIS(mat, Math.min(maxStack, npc.getPayStock()));
                    npc.setPayStock(npc.getPayStock() - Math.min(maxStack, npc.getPayStock()));
                    ec.setItem(i, is);
                    continue;
                }

                if(!is.getType().equals(mat))
                    continue;

                int free = maxStack - is.getAmount();
                int toAdd = Math.min(free, npc.getPayStock());
                is.setAmount(is.getAmount() + toAdd);
                npc.setPayStock(npc.getPayStock() - toAdd);
                ec.setItem(i, is);
            }
        }
    }
}
