package de.benji.naturoforumsmp.NPCShops.Main;

import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.DataStoreAPI.DataStoreAPI;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.NPCShops.Listener.NPCClick;
import de.benji.naturoforumsmp.NPCShops.Listener.NPCEntityClick;
import de.benji.naturoforumsmp.NPCShops.Listener.NPCInvClick;

import java.util.HashMap;
import java.util.UUID;

public class NPCShopsMain {
    private static NPCManager npcManager;

    public static void onEnable() {
        npcManager = new NPCManager();

        NPCInvs.registerListeners();

        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.NPCShops, Listeners.InvClick, NPCInvClick::onClick);
        lm.addCallback(Subplugin.NPCShops, Listeners.EntityClick, NPCEntityClick::onClick);
        lm.addCallback(Subplugin.NPCShops, Listeners.Click, NPCClick::onClick);

        DataStoreAPI dataStoreAPI = GlobalManager.getDataStoreAPI();
        dataStoreAPI.loadNPCList(DataKey.NPCShopsNPCs);
        HashMap<UUID, String> redeem = dataStoreAPI.loadUUIDStringHashMap(DataKey.NPCShopsItemstore);
        redeem.keySet().forEach(uuid -> npcManager.setRedeemType(uuid, NPCManager.ItemRedeemType.valueOf(redeem.get(uuid))));
    }

    public static void onDisable() {
        HashMap<UUID, String> itemRedeem = new HashMap<>();
        npcManager.getRedeemTypes().keySet().forEach(uuid -> itemRedeem.put(uuid, npcManager.getRedeemTypes().get(uuid).name()));

        DataStoreAPI dataStoreAPI = GlobalManager.getDataStoreAPI();
        dataStoreAPI.saveNPCList(DataKey.NPCShopsNPCs, npcManager.getNpcs());
        dataStoreAPI.saveUUIDStringHashMap(DataKey.NPCShopsItemstore, itemRedeem);
    }

    public static NPCManager getNpcManager() {
        return npcManager;
    }
}
