package de.benji.naturoforumsmp.Msg.Main;

import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Msg.Listener.MsgCommandPreprocess;

public class MsgMain {
    private static MsgColorManager msgColorManager;

    public static void onEnable() {
        msgColorManager = new MsgColorManager();
        MsgInvs.registerClicks();
        GlobalManager.getListenerManager().addCallback(Subplugin.Msg, Listeners.CommandPreprocess, MsgCommandPreprocess::onCommand);

        msgColorManager.setColors(GlobalManager.getDataStoreAPI().loadUUIDMsgStylesArrayHashMap(DataKey.MsgColors));
    }

    public static void onDisable() {
        GlobalManager.getDataStoreAPI().saveUUIDMsgStylesArrayHashMap(DataKey.MsgColors, msgColorManager.getColors());
    }

    public static MsgColorManager getMsgColorManager() {
        return msgColorManager;
    }
}
