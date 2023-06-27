package de.benji.naturoforumsmp.API.ListenerAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ListenerManager {
    private final HashMap<Listeners, HashMap<Subplugin, Consumer<Event>>> calls;
    private final HashMap<Listeners, List<Consumer<Event>>> alwaysCalls;

    public ListenerManager() {
        calls = new HashMap<>();
        alwaysCalls = new HashMap<>();
    }

    public void addCallback(Subplugin subplugin, Listeners listeners, Consumer<Event> eventCallback) {
        HashMap<Subplugin, Consumer<Event>> events = calls.get(listeners);
        if(events == null)
            events = new HashMap<>();
        events.put(subplugin, eventCallback);
        calls.put(listeners, events);
    }

    public void addAlwaysCallback(Listeners listeners, Consumer<Event> eventCallback) {
        List<Consumer<Event>> events = alwaysCalls.get(listeners);
        if(events == null)
            events = new ArrayList<>();

        events.add(eventCallback);
        alwaysCalls.put(listeners, events);
    }

    public void callListeners(Listeners listeners, Event e) {
        HashMap<Subplugin, Consumer<Event>> events = calls.get(listeners);
        if(events != null)
            for(Subplugin subplugin : events.keySet()) {
                if(!GlobalManager.getSubpluginManager().isPluginEnabled(subplugin))
                    continue;
                events.get(subplugin).accept(e);
            }

        List<Consumer<Event>> alwaysEvents = alwaysCalls.get(listeners);
        if(alwaysEvents != null)
            for(Consumer<Event> eventCallback: alwaysEvents)
                eventCallback.accept(e);
    }
}
