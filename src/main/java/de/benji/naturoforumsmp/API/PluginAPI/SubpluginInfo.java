package de.benji.naturoforumsmp.API.PluginAPI;

public class SubpluginInfo {
    public boolean isEnabled, isDevOnly;
    public Subplugin subplugin;
    public Runnable subpluginEnable;
    public Runnable subpluginDisable;

    public SubpluginInfo(boolean isEnabled, boolean isDevOnly, Subplugin subplugin, Runnable subpluginEnable, Runnable subpluginDisable) {
        this.isEnabled = isEnabled;
        this.isDevOnly = isDevOnly;
        this.subplugin = subplugin;
        this.subpluginEnable = subpluginEnable;
        this.subpluginDisable = subpluginDisable;
    }
}