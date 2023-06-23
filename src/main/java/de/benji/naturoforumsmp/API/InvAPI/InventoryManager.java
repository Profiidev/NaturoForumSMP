package de.benji.naturoforumsmp.API.InvAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private final List<ItemCallbackInfo> itemCallbacks;

    public InventoryManager() {
        itemCallbacks = new ArrayList<>();
    }

    public void addItem(String display, String invTitle, ItemCallback callback, Subplugin subplugin) {
        itemCallbacks.add(new ItemCallbackInfo(display, invTitle, callback, subplugin));
    }

    public void requestCallback(String display, String invTitle, InventoryClickEvent e) {
        for(ItemCallbackInfo itemCallbackInfo: itemCallbacks) {
            if(display.contains(itemCallbackInfo.display) && invTitle.contains(itemCallbackInfo.invTitle)) {
                e.setCancelled(true);
                if(GlobalManager.getSubpluginManager().isPluginEnabled(itemCallbackInfo.subplugin))
                    itemCallbackInfo.callback.click(e);
            }
            if(display.equals(ItemTitles.spacingItem))
                e.setCancelled(true);
        }
    }

    private static class ItemCallbackInfo {
        public String display;
        public String invTitle;
        public ItemCallback callback;
        public Subplugin subplugin;

        public ItemCallbackInfo(String display, String invTitle, ItemCallback callback, Subplugin subplugin) {
            this.display = display;
            this.invTitle = invTitle;
            this.callback = callback;
            this.subplugin = subplugin;
        }
    }

    @FunctionalInterface
    public interface ItemCallback {
        void click(InventoryClickEvent e);
    }
}
