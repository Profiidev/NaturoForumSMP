package de.benji.naturoforumsmp.API.PluginAPI;

public class SubpluginInfo {
    public boolean isEnabled, isDevOnly;
    public Subplugin subplugin;
    public SubpluginEnable subpluginEnable;
    public SubpluginDisable subpluginDisable;

    public SubpluginInfo(boolean isEnabled, boolean isDevOnly, Subplugin subplugin, SubpluginEnable subpluginEnable, SubpluginDisable subpluginDisable) {
        this.isEnabled = isEnabled;
        this.isDevOnly = isDevOnly;
        this.subplugin = subplugin;
        this.subpluginEnable = subpluginEnable;
        this.subpluginDisable = subpluginDisable;
    }

    @FunctionalInterface
    public interface SubpluginEnable {
        void enable();
    }

    @FunctionalInterface
    public interface SubpluginDisable {
        void disable();
    }
}