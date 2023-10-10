package de.benji.naturoforumsmp.Main.SMP.Util;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InvEnums;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginManager;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SMPInvs {
    public static Inventory getPluginInv() {
        InvAPI invAPI = GlobalManager.getInvAPI();
        SubpluginManager subpluginManager = GlobalManager.getSubpluginManager();
        Inventory inv = invAPI.createInv(6, "§8» §6§lPlugins §8«", InvEnums.Full, true, 49);

        boolean msg = subpluginManager.isPluginEnabled(Subplugin.Msg);
        boolean status = subpluginManager.isPluginEnabled(Subplugin.Status);
        boolean worldtracker = subpluginManager.isPluginEnabled(Subplugin.Worldtracker);
        boolean sanddupe = subpluginManager.isPluginEnabled(Subplugin.Sanddupe);
        boolean npcShops = subpluginManager.isPluginEnabled(Subplugin.NPCShops);
        boolean carpetDuper = subpluginManager.isPluginEnabled(Subplugin.CarpetDuper);
        boolean spawnElytra = subpluginManager.isPluginEnabled(Subplugin.SpawnElytra);
        boolean brausebad = subpluginManager.isPluginEnabled(Subplugin.Brausebad);
        boolean homes = subpluginManager.isPluginEnabled(Subplugin.Homes);
        boolean spawn = subpluginManager.isPluginEnabled(Subplugin.Spawn);

        inv.setItem(10, invAPI.createIS(Material.PAPER, "§8» §6§lMsg"));
        inv.setItem(11, invAPI.createIS(Material.OAK_SIGN, "§8» §6§lStatus"));
        inv.setItem(12, invAPI.createIS(Material.COMPASS, "§8» §6§lWorldtracker"));
        inv.setItem(13, invAPI.createIS(Material.SAND, "§8» §6§lSanddupe"));
        inv.setItem(14, invAPI.createIS(Material.EMERALD, "§8» §6§lNPCShops"));
        inv.setItem(15, invAPI.createIS(Material.WHITE_CARPET, "§8» §6§lCarpetDuper"));
        inv.setItem(16, invAPI.createIS(Material.ELYTRA, "§8» §6§lSpawnElytra"));
        inv.setItem(28, invAPI.createIS(Material.BREWING_STAND, "§8» §6§lBrausebad"));
        inv.setItem(29, invAPI.createIS(Material.RED_BED, "§8» §6§lHomes"));
        inv.setItem(30, invAPI.createIS(Material.GRASS_BLOCK, "§8» §6§lSpawn"));

        inv.setItem(19, invAPI.createIS(msg ? Material.LIME_DYE : Material.GRAY_DYE, msg ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(20, invAPI.createIS(status ? Material.LIME_DYE : Material.GRAY_DYE, status ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(21, invAPI.createIS(worldtracker ? Material.LIME_DYE : Material.GRAY_DYE, worldtracker ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(22, invAPI.createIS(sanddupe ? Material.LIME_DYE : Material.GRAY_DYE, sanddupe ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(23, invAPI.createIS(npcShops ? Material.LIME_DYE : Material.GRAY_DYE, npcShops ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(24, invAPI.createIS(carpetDuper ? Material.LIME_DYE : Material.GRAY_DYE, carpetDuper ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(25, invAPI.createIS(spawnElytra ? Material.LIME_DYE : Material.GRAY_DYE, spawnElytra ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(37, invAPI.createIS(brausebad ? Material.LIME_DYE : Material.GRAY_DYE, brausebad ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(38, invAPI.createIS(homes ? Material.LIME_DYE : Material.GRAY_DYE, homes ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));
        inv.setItem(39, invAPI.createIS(spawn ? Material.LIME_DYE : Material.GRAY_DYE, spawn ? "§8» §a§lAktiviert" : "§8» §c§lDeaktiviert"));

        return inv;
    }

    public static void registerPluginInvClicks() {
        InventoryManager im = GlobalManager.getInventoryManager();
        im.addItem(ItemTitles.closeItem, InvTitles.smp_PluginInv, SMPInvClicks::close, null);
        im.addItem(ItemTitles.smp_Msg, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Status, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Worldtracker, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Sanddupe, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_NPCShops, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_CarpetDuper, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_SpawnElytra, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Brausebad, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Homes, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Spawn, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Active, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
        im.addItem(ItemTitles.smp_Inactive, InvTitles.smp_PluginInv, SMPInvClicks::switchPluginStatus, null);
    }
}
