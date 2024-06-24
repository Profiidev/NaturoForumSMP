package de.benji.naturoforumsmp.Status.Util;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InvEnums;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.API.PermissionAPI.PermissionManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import de.benji.naturoforumsmp.API.Strings.UUIDs;
import de.benji.naturoforumsmp.Status.Main.StatusMain;
import de.benji.naturoforumsmp.Status.Main.StatusManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class StatusInvs {
    public static Inventory getMainInv(UUID uuid, int page) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        StatusManager statusManager = StatusMain.getStatusManager();
        PermissionManager permissionManager = GlobalManager.getPermissionManager();

        Inventory baseInv = invAPI.createInv(6, InvTitles.status_MainInv, InvEnums.Circle, true, 49);

        boolean hasPermissions = uuid.equals(UUIDs.benji) || uuid.equals(UUIDs.maxi) || permissionManager.hasPlayerPermission(uuid, Permission.StatusControl);
        boolean autoAFKEnabled = statusManager.getStatusCaches().get(uuid).afkEnabled;
        boolean changeOwn = statusManager.getStatusCaches().get(uuid).changeOwnStatus;
        boolean afkStatusSet = !statusManager.getAfkStatusKey().equals("");

        Status afkStatusData = null;
        if(afkStatusSet)
            afkStatusData = statusManager.getStatusData().get(statusManager.getAfkStatusKey());
        String display = afkStatusSet ? afkStatusData.display : "§cNot set";

        baseInv.setItem(8, invAPI.createIS(autoAFKEnabled ? Material.LIME_DYE : Material.GRAY_DYE, autoAFKEnabled ? ItemTitles.status_AutoAFKEnabled : ItemTitles.status_AutoAFKDisabled, Arrays.asList(" ", "§8This Status will be enabled after being AFK for more than 5 Minutes", " ", "§6Display: §r" + display, " ", autoAFKEnabled ? "§eClick to disable" : "§eClick to enable")));
        baseInv.setItem(0, invAPI.createIS(Material.BARRIER, ItemTitles.status_ClearStatus));

        if(hasPermissions) {
            baseInv.setItem(48, invAPI.createIS(Material.EMERALD, ItemTitles.status_CreateStatus));
            baseInv.setItem(50, invAPI.createIS(changeOwn ? Material.GRAY_DYE : Material.LIME_DYE, changeOwn ? ItemTitles.status_ChangeOwnPlayerStatus : ItemTitles.status_ChangeOtherPlayerStatus));
            baseInv.setItem(17, afkStatusSet ? invAPI.createSkull(afkStatusData.headURL, invAPI.createIS(Material.PLAYER_HEAD, ItemTitles.status_CurrentAFKStatus + afkStatusData.key, Arrays.asList(" ", "§6Display: §r" + display, " ", "§eRight click to edit")), false, true, true) : invAPI.createIS(Material.STRUCTURE_VOID, ItemTitles.status_SetAFKStatus));
        }

        if(statusManager.getStatusData().isEmpty())
            return baseInv;

        List<ItemStack> statusHeads = new ArrayList<>();

        for(String key: statusManager.getStatusData().keySet()) {
            Status status = statusManager.getStatusData().get(key);

            if(!hasPermissions) {
                if(status.statusAllow.containsKey(uuid)) {
                    if(!status.statusAllow.get(uuid))
                        continue;
                } else {
                    if(!status.isBlacklist)
                        continue;
                }
            }

            List<String> headLore = hasPermissions ? Arrays.asList("§6Display: §r" + status.display, "", "§bLeft click to choose", "", "§eRight click to edit") : Arrays.asList("§6Display: §r" + status.display, "", "§bLeft click to choose");
            statusHeads.add(invAPI.createSkull(status.headURL, invAPI.createIS(Material.BARRIER, key, headLore), false, true, true));
        }

        List<Inventory> mainInvs = invAPI.createInvPages(baseInv, InvTitles.status_MainInv, statusHeads, 45, 53);

        HashMap<StatusCache.InvLists, List<Inventory>> invs = statusManager.getStatusCaches().get(uuid).invPages;
        invs.put(StatusCache.InvLists.Main, mainInvs);
        statusManager.getStatusCaches().get(uuid).invPages = invs;

        return mainInvs.get(page);
    }

    public static Inventory getCreateInv(UUID uuid) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        StatusManager statusManager = StatusMain.getStatusManager();
        Status cache = statusManager.getStatusCaches().get(uuid).cacheStatus;

        Inventory inv = invAPI.createInv(3, InvTitles.status_CreateInv, InvEnums.Full);

        inv.setItem(16, invAPI.createIS(Material.EMERALD, ItemTitles.status_CreateNewStatus));
        inv.setItem(13, invAPI.createIS(cache.isBlacklist ? Material.BLACK_DYE : Material.WHITE_DYE, cache.isBlacklist ? ItemTitles.status_ChangeToWhitelist : ItemTitles.status_ChangeToBlacklist, cache.isBlacklist ? Collections.singletonList("§fCurrent: Blacklist") : Collections.singletonList("§fCurrent: Whitelist")));
        inv.setItem(11, invAPI.createSkull("http://textures.minecraft.net/texture/fa3fce5036f7ad4f11111ce318f8f1aee859ef49de1293f1162ca2e2dea281db", invAPI.createIS(Material.BARRIER, ItemTitles.status_SetDisplay, cache.display.equals("") ? Collections.singletonList("§4No Display set") : Collections.singletonList("Display: " + cache.display)), false, true, true));
        inv.setItem(10, invAPI.createSkull("http://textures.minecraft.net/texture/5f4c21d17ad636387ea3c736bff6ade897317e1374cd5d9b1c15e6e8953432", invAPI.createIS(Material.BARRIER, ItemTitles.status_SetName, cache.key.equals("") ? Collections.singletonList("§4No Name set") : Collections.singletonList("Name: " + cache.key)), false, true, true));
        inv.setItem(12, invAPI.createSkull(cache.headURL.equals("") ? "http://textures.minecraft.net/texture/46ba63344f49dd1c4f5488e926bf3d9e2b29916a6c50d610bb40a5273dc8c82" : cache.headURL, invAPI.createIS(Material.BARRIER, ItemTitles.status_SetHead, cache.headURL.equals("") ? Collections.singletonList("§4No Url set") : Collections.singletonList("Headurl: " + cache.headURL)), false, true, true));
        inv.setItem(22, invAPI.createIS(Material.BARRIER, ItemTitles.closeItem));
        inv.setItem(21, invAPI.createIS(Material.ARROW, ItemTitles.backItem));

        return inv;
    }

    public static Inventory getEditInv(String key) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        Status status = StatusMain.getStatusManager().getStatusData().get(key);

        List<String> blacklist = new ArrayList<>();
        List<String> whitelist = new ArrayList<>();
        for(UUID k: status.statusAllow.keySet()) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(k);
            if(p == null)
                continue;

            if(!status.statusAllow.get(k)) {
                blacklist.add(Objects.requireNonNull(p).getName());
            } else {
                whitelist.add(Objects.requireNonNull(p).getName());
            }
        }

        Inventory inv = invAPI.createInv(3, InvTitles.status_EditInv + status.key, InvEnums.Full, true, 22);

        inv.setItem(21, invAPI.createIS(Material.ARROW, ItemTitles.backItem));
        inv.setItem(16, invAPI.createIS(Material.REDSTONE, ItemTitles.status_Delete));
        inv.setItem(10, invAPI.createSkull("http://textures.minecraft.net/texture/fa3fce5036f7ad4f11111ce318f8f1aee859ef49de1293f1162ca2e2dea281db", invAPI.createIS(Material.BARRIER, ItemTitles.status_EditDisplay, Collections.singletonList("Display: §r" + status.display)), false, true, true));
        inv.setItem(11, invAPI.createSkull(status.headURL, invAPI.createIS(Material.BARRIER, ItemTitles.status_EditHead, Collections.singletonList("Headurl: §r" + status.headURL)), false, true, true));
        inv.setItem(12, invAPI.createIS(status.isBlacklist ? Material.BLACK_DYE : Material.WHITE_DYE, status.isBlacklist ? ItemTitles.status_ChangeToWhitelist : ItemTitles.status_ChangeToBlacklist, status.isBlacklist ? Collections.singletonList("§fCurrent: Blacklist") : Collections.singletonList("§fCurrent: Whitelist")));
        inv.setItem(13, invAPI.createIS(Material.BLACK_DYE, ItemTitles.status_AddPlayerToBlacklist, blacklist));
        inv.setItem(14, invAPI.createIS(Material.WHITE_DYE, ItemTitles.status_AddPlayerToWhitelist, whitelist));

        return inv;
    }

    public static Inventory getPlayerInv(UUID uuid, String reason) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        StatusManager statusManager = StatusMain.getStatusManager();

        Inventory inv = invAPI.createInv(6, InvTitles.status_PlayerInv + reason, InvEnums.Circle, true, 49);

        inv.setItem(48, invAPI.createIS(Material.ARROW, ItemTitles.backItem));

        List<ItemStack> iss = new ArrayList<>();
        for(Player p: Bukkit.getOnlinePlayers()) {
            if(p.getUniqueId().equals(UUIDs.benji) || p.getUniqueId().equals(UUIDs.maxi))
                continue;

            iss.add(invAPI.createSkull(((CraftPlayer) p).getProfile(), invAPI.createIS(Material.BARRIER, p.getName()), false, false, true));
        }

        List<Inventory> mainInvs = invAPI.createInvPages(inv, InvTitles.status_PlayerInv + reason, iss, 45, 53);

        HashMap<StatusCache.InvLists, List<Inventory>> invs = statusManager.getStatusCaches().get(uuid).invPages;
        invs.put(StatusCache.InvLists.Player, mainInvs);
        statusManager.getStatusCaches().get(uuid).invPages = invs;

        return mainInvs.get(0);
    }

    public static Inventory getAFKStatusInv(UUID uuid) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        StatusManager statusManager = StatusMain.getStatusManager();

        Inventory inv = invAPI.createInv(6, InvTitles.status_AFKStatusInv, InvEnums.Circle, true, 49);

        inv.setItem(48, invAPI.createIS(Material.ARROW, ItemTitles.backItem));

        List<ItemStack> iss = new ArrayList<>();
        HashMap<String, Status> statusData = statusManager.getStatusData();
        for(String key: statusData.keySet()) {
            Status status = statusData.get(key);
            iss.add(invAPI.createSkull(status.headURL, invAPI.createIS(Material.BARRIER, key, Arrays.asList("§6Display: §r" + status.display, "", "§bLeft click to choose", "", "§eRight click to edit")), false, true, true));
        }

        List<Inventory> mainInvs = invAPI.createInvPages(inv, InvTitles.status_AFKStatusInv, iss, 45, 53);

        HashMap<StatusCache.InvLists, List<Inventory>> invs = statusManager.getStatusCaches().get(uuid).invPages;
        invs.put(StatusCache.InvLists.AFKStatus, mainInvs);
        statusManager.getStatusCaches().get(uuid).invPages = invs;

        return mainInvs.get(0);
    }

    public static void registerInvClicks() {
        registerMainInvClicks();
        registerCreateInvCLicks();
        registerEditInvClicks();
        registerPlayerInvClicks();
        registerAFKInvClicks();
    }

    private static void registerMainInvClicks() {
        InventoryManager inventoryManager = GlobalManager.getInventoryManager();
        inventoryManager.addItem(ItemTitles.closeItem, InvTitles.status_MainInv, StatusInvClicks::close, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_AutoAFKEnabled, InvTitles.status_MainInv, StatusInvClicks::disableAutoAFK, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_AutoAFKDisabled, InvTitles.status_MainInv, StatusInvClicks::enableAutoAFK, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_CreateStatus, InvTitles.status_MainInv, StatusInvClicks::openCreateStatusInv, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeOtherPlayerStatus, InvTitles.status_MainInv, StatusInvClicks::enableSetOwnStatus, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeOwnPlayerStatus, InvTitles.status_MainInv, StatusInvClicks::disableSetOwnStatus, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_SetAFKStatus, InvTitles.status_MainInv, StatusInvClicks::setAutoAFKStatus, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_CurrentAFKStatus, InvTitles.status_MainInv, StatusInvClicks::setAutoAFKStatus, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.page, InvTitles.status_MainInv, StatusInvClicks::changeStatusPage, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ClearStatus, InvTitles.status_MainInv, StatusInvClicks::clearStatus, Subplugin.Status);
    }

    private static void registerCreateInvCLicks() {
        InventoryManager inventoryManager = GlobalManager.getInventoryManager();
        inventoryManager.addItem(ItemTitles.status_SetName, InvTitles.status_CreateInv, StatusInvClicks::setName, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_SetDisplay, InvTitles.status_CreateInv, StatusInvClicks::setDisplay, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_SetHead, InvTitles.status_CreateInv, StatusInvClicks::setHeadurl, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeToBlacklist, InvTitles.status_CreateInv, StatusInvClicks::enableBlacklist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeToWhitelist, InvTitles.status_CreateInv, StatusInvClicks::disableBlacklist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_CreateNewStatus, InvTitles.status_CreateInv, StatusInvClicks::createStatus, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.closeItem, InvTitles.status_CreateInv, StatusInvClicks::close, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.backItem, InvTitles.status_CreateInv, StatusInvClicks::backToMain, Subplugin.Status);
    }

    private static void registerEditInvClicks() {
        InventoryManager inventoryManager = GlobalManager.getInventoryManager();
        inventoryManager.addItem(ItemTitles.closeItem, InvTitles.status_EditInv, StatusInvClicks::close, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.backItem, InvTitles.status_EditInv, StatusInvClicks::backToMain, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_EditDisplay, InvTitles.status_EditInv, StatusInvClicks::editDisplay, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_EditHead, InvTitles.status_EditInv, StatusInvClicks::editHeadurl, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeToBlacklist, InvTitles.status_EditInv, StatusInvClicks::changeToBlacklist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_ChangeToWhitelist, InvTitles.status_EditInv, StatusInvClicks::changeToWhitelist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_AddPlayerToBlacklist, InvTitles.status_EditInv, StatusInvClicks::openPlayerToBlacklist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_AddPlayerToWhitelist, InvTitles.status_EditInv, StatusInvClicks::openPlayerToWhitelist, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.status_Delete, InvTitles.status_EditInv, StatusInvClicks::deleteStatus, Subplugin.Status);
    }

    private static void registerPlayerInvClicks() {
        InventoryManager inventoryManager = GlobalManager.getInventoryManager();
        inventoryManager.addItem(ItemTitles.closeItem, InvTitles.status_PlayerInv, StatusInvClicks::close, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.backItem, InvTitles.status_PlayerInv, StatusInvClicks::backToEditInv, Subplugin.Status);
    }

    private static void registerAFKInvClicks() {
        InventoryManager inventoryManager = GlobalManager.getInventoryManager();
        inventoryManager.addItem(ItemTitles.closeItem, InvTitles.status_AFKStatusInv, StatusInvClicks::close, Subplugin.Status);
        inventoryManager.addItem(ItemTitles.backItem, InvTitles.status_AFKStatusInv, StatusInvClicks::backToMain, Subplugin.Status);
    }
}
