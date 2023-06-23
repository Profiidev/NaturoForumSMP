package de.benji.naturoforumsmp.API.ListenerAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {
    private final HashMap<Listeners, HashMap<Subplugin, EventCallback>> calls;
    private final HashMap<Listeners, List<EventCallback>> alwaysCalls;

    public ListenerManager() {
        calls = new HashMap<>();
        alwaysCalls = new HashMap<>();
    }

    public void addCallback(Subplugin subplugin, Listeners listeners, EventCallback eventCallback) {
        HashMap<Subplugin, EventCallback> events = calls.get(listeners);
        if(events == null)
            events = new HashMap<>();
        events.put(subplugin, eventCallback);
        calls.put(listeners, events);
    }

    public void addAlwaysCallback(Listeners listeners, EventCallback eventCallback) {
        List<EventCallback> events = alwaysCalls.get(listeners);
        if(events == null)
            events = new ArrayList<>();

        events.add(eventCallback);
        alwaysCalls.put(listeners, events);
    }

    public void callListeners(Listeners listeners, Event e) {
        HashMap<Subplugin, EventCallback> events = calls.get(listeners);
        if(events != null)
            for(Subplugin subplugin : events.keySet()) {
                if(!GlobalManager.getSubpluginManager().isPluginEnabled(subplugin))
                    continue;
                events.get(subplugin).onEvent(e);
            }

        List<EventCallback> alwaysEvents = alwaysCalls.get(listeners);
        if(alwaysEvents != null)
            for(EventCallback eventCallback: alwaysEvents)
                eventCallback.onEvent(e);
    }

    @FunctionalInterface
    public interface EventCallback {
        void onEvent(Event e);
    }
}
