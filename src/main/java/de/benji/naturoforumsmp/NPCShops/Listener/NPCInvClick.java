package de.benji.naturoforumsmp.NPCShops.Listener;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InputAPI.InputKey;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.NPCShops.Main.NPC;
import de.benji.naturoforumsmp.NPCShops.Main.NPCInvs;
import de.benji.naturoforumsmp.NPCShops.Main.NPCManager;
import de.benji.naturoforumsmp.NPCShops.Main.NPCShopsMain;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NPCInvClick {
    public static void onClick(Event event) {
        InventoryClickEvent e = (InventoryClickEvent) event;
        if(!e.getView().getTitle().equals(InvTitles.npcs_Shops))
            return;

        if(e.getCurrentItem() == null)
            return;

        if(e.getCurrentItem().getItemMeta() == null)
            return;

        Player p = (Player) e.getWhoClicked();
        NPCManager manager = NPCShopsMain.getNpcManager();
        NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));
        ItemStack is = e.getCurrentItem();

        switch (manager.getItemInput(p.getUniqueId())) {
            case None: {
                return;
            }
            case SellItem: {
                manager.setItemInput(p.getUniqueId(), NPCManager.ItemInputType.None);

                if(is.getType().getMaxStackSize() == 1 && !is.getType().equals(Material.TOTEM_OF_UNDYING)) {
                    p.sendMessage("§cItem must be stackable");
                    p.closeInventory();
                    return;
                }

                npc.setSellItem(is.getType());

                if(is.getType().equals(Material.TOTEM_OF_UNDYING)) {
                    npc.setItemsPerSell(1);
                    p.openInventory(NPCInvs.getOwnerInv(npc.getUUID()));
                    return;
                }

                GlobalManager.getInputManager().createIntInput(p, new InputManager.InputInfo(InputKey.NPC_SellAmount, (s, player) -> {
                    npc.setItemsPerSell(Integer.parseInt(s));
                    player.openInventory(NPCInvs.getOwnerInv(npc.getUUID()));
                }), 1, is.getType().getMaxStackSize());
                break;
            }
            case PayItem: {
                manager.setItemInput(p.getUniqueId(), NPCManager.ItemInputType.None);

                if(is.getType().getMaxStackSize() == 1) {
                    p.sendMessage("§cItem must be stackable");
                    p.closeInventory();
                    return;
                }

                npc.setPayItem(is.getType());

                GlobalManager.getInputManager().createIntInput(p, new InputManager.InputInfo(InputKey.NPC_SellAmount, (s, player) -> {
                    npc.setItemsPerPay(Integer.parseInt(s));
                    player.openInventory(NPCInvs.getOwnerInv(npc.getUUID()));
                }), 1, is.getType().getMaxStackSize());
                break;
            }
        }
    }
}
