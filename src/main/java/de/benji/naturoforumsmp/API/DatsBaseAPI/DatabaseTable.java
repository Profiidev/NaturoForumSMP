package de.benji.naturoforumsmp.API.DatsBaseAPI;

public enum DatabaseTable {
    UUIDs("uuids"),
    SMPJoined("smp_joined"),
    SMPPlugins("smp_plugins");

    public final String key;

    DatabaseTable(String key) {
        this.key = key;
    }
}
