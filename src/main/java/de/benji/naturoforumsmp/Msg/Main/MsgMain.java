package de.benji.naturoforumsmp.Msg.Main;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigKey;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Msg.Listener.MsgCommandPreprocess;

import java.util.HashMap;
import java.util.UUID;

public class MsgMain {
    static MsgColorManager msgColorManager;

    public static void onEnable() {
        msgColorManager = new MsgColorManager();
        MsgInvs.registerClicks();
        GlobalManager.getListenerManager().addCallback(Subplugin.Msg, Listeners.CommandPreprocess, MsgCommandPreprocess::onCommand);

        HashMap<UUID, String[]> colors = GlobalManager.getConfigAPI().loadUUIDStringArrayHashMap(ConfigKey.MsgColors.key);
        HashMap<UUID, MsgStyles[]> styles = new HashMap<>();
        for(UUID uuid: colors.keySet()) {
            String[] color = colors.get(uuid);
            MsgStyles[] style = new MsgStyles[6];
            for(int i = 0; i < color.length; i++) {
                style[i] = MsgStyles.fromChar(color[i].charAt(0));
            }
            styles.put(uuid, style);
        }
        msgColorManager.setColors(styles);
    }

    public static void onDisable() {
        HashMap<UUID, String[]> colors = new HashMap<>();
        HashMap<UUID, MsgStyles[]> styles = msgColorManager.getColors();
        for(UUID uuid: styles.keySet()) {
            MsgStyles[] style = styles.get(uuid);
            String[] color = new String[6];
            for(int i = 0; i < style.length; i++) {
                color[i] = style[i].key;
            }
            colors.put(uuid, color);
        }
        GlobalManager.getConfigAPI().saveUUIDStringArrayHashMap(ConfigKey.MsgColors.key, colors);
    }

    public static MsgColorManager getMsgColorManager() {
        return msgColorManager;
    }
}
