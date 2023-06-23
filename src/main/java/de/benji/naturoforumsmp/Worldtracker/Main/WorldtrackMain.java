package de.benji.naturoforumsmp.Worldtracker.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Worldtracker.Listener.WorldtrackerJoin;
import de.benji.naturoforumsmp.Worldtracker.Listener.WorldtrackerWorldChange;

public class WorldtrackMain {
    public static void onEnable() {
        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.Worldtracker, Listeners.WorldChange, WorldtrackerWorldChange::onWorldChange);
        lm.addCallback(Subplugin.Worldtracker, Listeners.Join, WorldtrackerJoin::onJoin);
    }
}
