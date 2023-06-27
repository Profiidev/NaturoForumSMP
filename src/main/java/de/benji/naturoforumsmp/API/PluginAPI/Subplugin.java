package de.benji.naturoforumsmp.API.PluginAPI;

public enum Subplugin {
    Brausebad("Brausebad"),
    Msg("Msg"),
    Sanddupe("Sanddupe"),
    Status("Status"),
    Worldtracker("Worldtracker"),
    NPCShops("NPCShops"),
    CarpetDuper("CarpetDuper"),
    SpawnElytra("SpawnElytra"),
    Homes("Homes");

    public final String key;

    Subplugin(String key) {
        this.key = key;
    }

    public static Subplugin fromString(String key) {
        for(Subplugin subplugin: Subplugin.values()) {
            if(subplugin.key.equals(key))
                return subplugin;
        }
        return null;
    }
}

