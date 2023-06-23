package de.benji.naturoforumsmp.Sanddupe.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.Sanddupe.Listener.SandDupePortal;

public class SanddupeMain {
    public static void onEnable() {
        GlobalManager.getListenerManager().addCallback(Subplugin.Sanddupe, Listeners.Portal, SandDupePortal::onPortal);
    }
}
