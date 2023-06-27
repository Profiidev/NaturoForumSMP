package de.benji.naturoforumsmp.API.PluginAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubpluginManager {
    private final HashMap<Subplugin,SubpluginInfo> subPlugins;

    public SubpluginManager() {
        subPlugins = new HashMap<>();
    }

    public void addSubplugin(SubpluginInfo subpluginInfo) {
        subPlugins.put(subpluginInfo.subplugin, subpluginInfo);
    }

    public boolean isPluginEnabled(Subplugin subplugin) {
        return subPlugins.get(subplugin).isEnabled;
    }

    public void startAllEnabledSubplugins() {
        for(SubpluginInfo data: subPlugins.values()) {
            if(data.isEnabled && data.subpluginEnable != null) {
                data.subpluginEnable.run();
            }
        }
    }

    public void enableSubplugin(Subplugin subplugin) {
        SubpluginInfo data = subPlugins.get(subplugin);
        data.isEnabled = true;

        if(data.subpluginEnable == null)
            return;

        data.subpluginEnable.run();
    }

    public void disableSubplugin(Subplugin subplugin) {
        SubpluginInfo data = subPlugins.get(subplugin);
        data.isEnabled = false;

        if(data.subpluginDisable == null)
            return;

        data.subpluginDisable.run();
    }

    public void disableAllSubplugins() {
        for(SubpluginInfo data: subPlugins.values()) {
            if(data.subpluginDisable == null || !data.isEnabled)
                continue;
            data.subpluginDisable.run();
        }
    }

    public HashMap<String, Boolean> getAllSubpluginsStatus() {
        HashMap<String, Boolean> data = new HashMap<>();
        subPlugins.keySet().forEach(k -> data.put(k.key, subPlugins.get(k).isEnabled));
        return data;
    }

    public List<String> getAllDevSubplugins() {
        List<String> dev = new ArrayList<>();
        subPlugins.keySet().forEach(k -> dev.add(subPlugins.get(k).isDevOnly ? k.key : null));
        return dev;
    }
}