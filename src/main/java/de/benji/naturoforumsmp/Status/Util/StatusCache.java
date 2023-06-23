package de.benji.naturoforumsmp.Status.Util;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

public class StatusCache {
    public Status cacheStatus;
    public String statusInputCache;
    public String currentEditingStatus;
    public HashMap<InvLists, List<Inventory>> invPages;
    public int afkTime;
    public String currentStatus;
    public String currentStatusToGive;
    public boolean isAFK;
    public boolean afkEnabled;
    public boolean changeOwnStatus;

    public StatusCache(Status cacheStatus, String statusInputCache, String currentEditingStatus, HashMap<InvLists, List<Inventory>> invPages, int afkTime, String currentStatus, String currentStatusToGive, boolean isAFK, boolean afkEnabled, boolean changeOwnStatus) {
        this.cacheStatus = cacheStatus;
        this.statusInputCache = statusInputCache;
        this.currentEditingStatus = currentEditingStatus;
        this.invPages = invPages;
        this.afkTime = afkTime;
        this.currentStatus = currentStatus;
        this.currentStatusToGive = currentStatusToGive;
        this.isAFK = isAFK;
        this.afkEnabled = afkEnabled;
        this.changeOwnStatus = changeOwnStatus;
    }

    public enum InvLists {
        Main,
        Player,
        AFKStatus
    }
}
