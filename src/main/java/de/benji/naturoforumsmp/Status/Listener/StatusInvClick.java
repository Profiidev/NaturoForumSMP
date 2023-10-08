package de.benji.naturoforumsmp.Status.Listener;

import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.Status.Main.StatusMain;
import de.benji.naturoforumsmp.Status.Main.StatusManager;
import de.benji.naturoforumsmp.Status.Util.StatusInvs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StatusInvClick {
    public static void onClick(Event event) {
        InventoryClickEvent e = (InventoryClickEvent)event;
        if(e.getCurrentItem() == null || !(e.getWhoClicked() instanceof Player))
            return;
        if(e.getCurrentItem().getItemMeta() == null)
            return;
        String display = e.getCurrentItem().getItemMeta().getDisplayName();

        if(e.getView().getTitle().equals(InvTitles.status_MainInv)) {
            e.setCancelled(true);
            StatusManager statusManager = StatusMain.getStatusManager();

            if(!statusManager.getStatusData().containsKey(display))
                return;

            if(e.getClick().equals(ClickType.LEFT)) {
                e.getWhoClicked().closeInventory();
                if(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).changeOwnStatus) {
                    statusManager.setPlayerStatus(display, (Player) e.getWhoClicked());
                } else {
                    statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentStatusToGive = display;
                    statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus = "";
                    e.getWhoClicked().openInventory(StatusInvs.getPlayerInv(e.getWhoClicked().getUniqueId(), InvTitles.status_Player_ToStatus));
                }
            } else if(e.getClick().equals(ClickType.RIGHT)) {
                e.getWhoClicked().openInventory(StatusInvs.getEditInv(display));
                StatusMain.getStatusManager().getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus = display;
            }
        } else if(e.getView().getTitle().contains(InvTitles.status_PlayerInv)) {
            e.setCancelled(true);
            StatusManager statusManager = StatusMain.getStatusManager();

            String reason = e.getView().getTitle().replace(InvTitles.status_PlayerInv, "");
            switch (reason) {
                case InvTitles.status_Player_ToBlacklist: {
                    Player p = Bukkit.getPlayer(display);
                    if(p == null)
                        return;

                    statusManager.getStatusData().get(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus).statusAllow.put(p.getUniqueId(), false);
                    e.getWhoClicked().openInventory(StatusInvs.getEditInv(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus));
                    break;
                }
                case InvTitles.status_Player_ToWhitelist: {
                    Player p = Bukkit.getPlayer(display);
                    if(p == null)
                        return;

                    statusManager.getStatusData().get(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus).statusAllow.put(p.getUniqueId(), true);
                    e.getWhoClicked().openInventory(StatusInvs.getEditInv(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus));
                    break;
                }
                case InvTitles.status_Player_ToStatus: {
                    Player p = Bukkit.getPlayer(display);
                    if(p == null)
                        return;

                    e.getWhoClicked().openInventory(StatusInvs.getMainInv(e.getWhoClicked().getUniqueId(), 0));
                    statusManager.setPlayerStatus(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentStatusToGive, p);
                    break;
                }
            }
        } else if(e.getView().getTitle().contains(InvTitles.status_AFKStatusInv)) {
            e.setCancelled(true);
            StatusManager statusManager = StatusMain.getStatusManager();

            if(!statusManager.getStatusData().containsKey(display))
                return;

            statusManager.setAfkStatusKey(display);

            e.getWhoClicked().openInventory(StatusInvs.getMainInv(e.getWhoClicked().getUniqueId(), 0));
        }
    }
}
