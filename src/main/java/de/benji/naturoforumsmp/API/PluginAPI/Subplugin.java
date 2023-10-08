package de.benji.naturoforumsmp.API.PluginAPI;

public enum Subplugin {
    Brausebad("Brausebad", 28),
    Msg("Msg", 10),
    Sanddupe("Sanddupe", 13),
    Status("Status", 11),
    Worldtracker("Worldtracker", 12),
    NPCShops("NPCShops", 14),
    CarpetDuper("CarpetDuper", 15),
    SpawnElytra("SpawnElytra", 16),
    Homes("Homes", 29);

    public final String key;
    public final int invPos;

    Subplugin(String key, int invPos) {
        this.key = key;
        this.invPos = invPos;
    }

    public static Subplugin fromString(String key) {
        for(Subplugin subplugin: Subplugin.values()) {
            if(subplugin.key.equals(key))
                return subplugin;
        }
        return null;
    }
}

