package de.benji.naturoforumsmp.Status.Util;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InputAPI.InputKey;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import de.benji.naturoforumsmp.Status.Main.StatusMain;
import de.benji.naturoforumsmp.Status.Main.StatusManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;

public class StatusInvClicks {
    static InvAPI invAPI = GlobalManager.getInvAPI();

    //Main Inv
    public static void close(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
    }

    public static void disableAutoAFK(InventoryClickEvent e) {
        switchAutoAFK(e, false);
    }

    public static void enableAutoAFK(InventoryClickEvent e) {
        switchAutoAFK(e, true);
    }

    public static void openCreateStatusInv(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getCreateInv(e.getWhoClicked().getUniqueId()));
    }

    public static void enableSetOwnStatus(InventoryClickEvent e) {
        switchPlayerStatus(e, true);
    }

    public static void disableSetOwnStatus(InventoryClickEvent e) {
        switchPlayerStatus(e, false);
    }

    public static void setAutoAFKStatus(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getAFKStatusInv(e.getWhoClicked().getUniqueId()));
    }

    public static void changeStatusPage(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(invAPI.switchInv(StatusMain.getStatusManager().getStatusCaches().get(e.getWhoClicked().getUniqueId()).invPages.get(StatusCache.InvLists.Main), e.getView().getTopInventory(), e.getSlot()));
    }

    public static void clearStatus(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
        StatusManager.setPlayerPrefixSuffix("", null, (Player) e.getWhoClicked());
        StatusMain.getStatusManager().getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentStatus = "";
    }

    private static void switchAutoAFK(InventoryClickEvent e, boolean autoAFKEnabled) {
        StatusManager statusManager = StatusMain.getStatusManager();
        StatusCache cache = statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId());
        cache.afkEnabled = autoAFKEnabled;

        boolean afkStatusSet = statusManager.getAfkStatusKey() != null;
        Status afkStatusData = null;
        if(afkStatusSet)
            afkStatusData = statusManager.getStatusData().get(statusManager.getAfkStatusKey());
        String display = afkStatusSet ? afkStatusData.display : "§cNot set";
        e.getView().setItem(8, invAPI.createIS(autoAFKEnabled ? Material.LIME_DYE : Material.GRAY_DYE, autoAFKEnabled ? ItemTitles.status_AutoAFKEnabled : ItemTitles.status_AutoAFKDisabled, Arrays.asList(" ", "§8This Status will be enabled after being AFK for more than 5 Minutes", " ", "§6Display: §r" + display, " ", autoAFKEnabled ? "§eClick to disable" : "§eClick to enable")));
    }

    private static void switchPlayerStatus(InventoryClickEvent e, boolean changeOwn) {
        StatusManager statusManager = StatusMain.getStatusManager();
        StatusCache cache = statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId());
        cache.changeOwnStatus = changeOwn;

        e.getView().setItem(50, invAPI.createIS(changeOwn ? Material.GRAY_DYE : Material.LIME_DYE, changeOwn ? ItemTitles.status_ChangeOwnPlayerStatus : ItemTitles.status_ChangeOtherPlayerStatus));
    }

    //Create Inv
    public static void setName(InventoryClickEvent e) {
        GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.Status_SetName, (s, p) -> {
            StatusCache cache = StatusMain.getStatusManager().getStatusCaches().get(p.getUniqueId());
            cache.cacheStatus.key = s;
            p.openInventory(StatusInvs.getCreateInv(p.getUniqueId()));
        }));
    }

    public static void setDisplay(InventoryClickEvent e) {
        GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.Status_SetDisplay, (s, p) -> {
            StatusCache cache = StatusMain.getStatusManager().getStatusCaches().get(p.getUniqueId());
            cache.cacheStatus.display = s.replace('&', '§');
            p.openInventory(StatusInvs.getCreateInv(p.getUniqueId()));
        }));
    }

    public static void setHeadurl(InventoryClickEvent e) {
        GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.Status_SetHead, (s, p) -> {
            StatusCache cache = StatusMain.getStatusManager().getStatusCaches().get(p.getUniqueId());
            cache.cacheStatus.headURL = s;
            p.openInventory(StatusInvs.getCreateInv(p.getUniqueId()));
        }));
    }

    public static void enableBlacklist(InventoryClickEvent e) {
        switchAccessType(e, true);
    }

    public static void disableBlacklist(InventoryClickEvent e) {
        switchAccessType(e, false);
    }

    public static void createStatus(InventoryClickEvent e) {
        StatusManager statusManager = StatusMain.getStatusManager();
        Status newStatus = statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).cacheStatus;
        if(newStatus.key.equals("") || newStatus.display.equals("") || newStatus.headURL.equals("")) {
            e.getWhoClicked().sendMessage("§cSet everything!");
            return;
        }
        statusManager.getStatusData().put(newStatus.key, newStatus);
        statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).cacheStatus = new Status("", "", "", true);
        e.getWhoClicked().openInventory(StatusInvs.getMainInv(e.getWhoClicked().getUniqueId(), 0));
    }

    public static void backToMain(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getMainInv(e.getWhoClicked().getUniqueId(), 0));
    }

    private static void switchAccessType(InventoryClickEvent e, boolean isBlacklist) {
        StatusManager statusManager = StatusMain.getStatusManager();
        StatusCache cache = statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId());
        cache.cacheStatus.isBlacklist = isBlacklist;
        e.getView().setItem(13, invAPI.createIS(isBlacklist ? Material.BLACK_DYE : Material.WHITE_DYE, isBlacklist ? ItemTitles.status_ChangeToWhitelist : ItemTitles.status_ChangeToBlacklist, isBlacklist ? Collections.singletonList("§fCurrent: Blacklist") : Collections.singletonList("§fCurrent: Whitelist")));
    }

    //Edit Inv
    public static void editDisplay(InventoryClickEvent e) {
        GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.Status_editDisplay, (s, p) -> {
            StatusCache cache = StatusMain.getStatusManager().getStatusCaches().get(p.getUniqueId());
            StatusMain.getStatusManager().getStatusData().get(cache.currentEditingStatus).display = s.replace('&', '§');
            p.openInventory(StatusInvs.getEditInv(cache.currentEditingStatus));
        }));
    }

    public static void editHeadurl(InventoryClickEvent e) {
        GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.Status_editHead, (s, p) -> {
            StatusCache cache = StatusMain.getStatusManager().getStatusCaches().get(p.getUniqueId());
            StatusMain.getStatusManager().getStatusData().get(cache.currentEditingStatus).headURL = s;
            p.openInventory(StatusInvs.getEditInv(cache.currentEditingStatus));
        }));
    }

    public static void changeToBlacklist(InventoryClickEvent e) {
        changeAccessType(e, true);
    }

    public static void changeToWhitelist(InventoryClickEvent e) {
        changeAccessType(e, false);
    }

    public static void openPlayerToBlacklist(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getPlayerInv(e.getWhoClicked().getUniqueId(), InvTitles.status_Player_ToBlacklist));
    }

    public static void openPlayerToWhitelist(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getPlayerInv(e.getWhoClicked().getUniqueId(), InvTitles.status_Player_ToWhitelist));
    }

    public static void deleteStatus(InventoryClickEvent e) {
        StatusManager statusManager = StatusMain.getStatusManager();
        statusManager.getStatusData().remove(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus);
        for(Player p: Bukkit.getOnlinePlayers()) {
            try {
                p.getOpenInventory().getTitle().contains(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus);
                p.closeInventory();
            } catch (Exception e1) {}
        }
    }

    private static void changeAccessType(InventoryClickEvent e, boolean toBlacklist) {
        StatusManager statusManager = StatusMain.getStatusManager();
        Status status = statusManager.getStatusData().get(statusManager.getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus);
        status.isBlacklist = toBlacklist;
        e.getView().setItem(12, invAPI.createIS(status.isBlacklist ? Material.BLACK_DYE : Material.WHITE_DYE, status.isBlacklist ? ItemTitles.status_ChangeToWhitelist : ItemTitles.status_ChangeToBlacklist, status.isBlacklist ? Collections.singletonList("§fCurrent: Blacklist") : Collections.singletonList("§fCurrent: Whitelist")));
    }

    //Player Inv
    public static void backToEditInv(InventoryClickEvent e) {
        e.getWhoClicked().openInventory(StatusInvs.getEditInv(StatusMain.getStatusManager().getStatusCaches().get(e.getWhoClicked().getUniqueId()).currentEditingStatus));
    }
}
