package de.benji.naturoforumsmp.Main.SMP.Util;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginManager;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SMPInvClicks {
    public static void close(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
    }
    public static void switchPluginStatus(InventoryClickEvent e) {
        SubpluginManager subpluginManager = GlobalManager.getSubpluginManager();
        Subplugin plugin = getSubplugin(e.getSlot());
        if(subpluginManager.isPluginEnabled(plugin)) {
            e.getWhoClicked().sendMessage("§c§l" + plugin.key + " §7wurde deaktiviert.");
            subpluginManager.disableSubplugin(plugin);
        } else {
            e.getWhoClicked().sendMessage("§a§l" + plugin.key + " §7wurde aktiviert.");
            subpluginManager.enableSubplugin(plugin);
        }
        e.getView().getTopInventory().setContents(SMPInvs.getPluginInv().getContents());
    }

    private static Subplugin getSubplugin(int slot) {
        for(Subplugin plugin : Subplugin.values()) {
            if(plugin.invPos == slot || plugin.invPos + 9 == slot) {
                return plugin;
            }
        }
        return null;
    }
}
