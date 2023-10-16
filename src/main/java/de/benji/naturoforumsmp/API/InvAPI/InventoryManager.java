package de.benji.naturoforumsmp.API.InvAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryManager {
    private final List<ItemCallbackInfo> itemCallbacks;
    private boolean isLooping = false;
    private final List<ItemCallbackInfo> tempCallbacks;

    public InventoryManager() {
        itemCallbacks = new ArrayList<>();
        tempCallbacks = new ArrayList<>();
    }

    public void addItem(String display, String invTitle, Consumer<InventoryClickEvent> callback, Subplugin subplugin) {
        ItemCallbackInfo itemCallbackInfo = new ItemCallbackInfo(display, invTitle, callback, subplugin);
        for(ItemCallbackInfo itemCallbackInfo1: itemCallbacks) {
            if(itemCallbackInfo1.display.equals(display) && itemCallbackInfo1.invTitle.equals(invTitle) && itemCallbackInfo1.subplugin == subplugin)
                return;
        }

        if(isLooping) {
            tempCallbacks.add(itemCallbackInfo);
        } else {
            itemCallbacks.add(itemCallbackInfo);
        }
    }

    public void requestCallback(String display, String invTitle, InventoryClickEvent e) {
        isLooping = true;
        for(ItemCallbackInfo itemCallbackInfo: itemCallbacks) {
            if(display.contains(itemCallbackInfo.display) && invTitle.contains(itemCallbackInfo.invTitle)) {
                e.setCancelled(true);
                if(itemCallbackInfo.subplugin == null) {
                    itemCallbackInfo.callback.accept(e);
                    break;
                }
                if(GlobalManager.getSubpluginManager().isPluginEnabled(itemCallbackInfo.subplugin))
                    itemCallbackInfo.callback.accept(e);
            }
            if(display.equals(ItemTitles.spacingItem))
                e.setCancelled(true);
        }
        isLooping = false;
        itemCallbacks.addAll(tempCallbacks);
    }

    private static class ItemCallbackInfo {
        public String display;
        public String invTitle;
        public Consumer<InventoryClickEvent> callback;
        public Subplugin subplugin;

        public ItemCallbackInfo(String display, String invTitle, Consumer<InventoryClickEvent> callback, Subplugin subplugin) {
            this.display = display;
            this.invTitle = invTitle;
            this.callback = callback;
            this.subplugin = subplugin;
        }
    }
}
