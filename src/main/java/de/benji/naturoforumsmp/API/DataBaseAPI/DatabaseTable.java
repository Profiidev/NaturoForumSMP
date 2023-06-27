package de.benji.naturoforumsmp.API.DataBaseAPI;

public enum DatabaseTable {
    UUIDs("uuids"),
    SMPJoined("smp_joined"),
    SMPPlugins("smp_plugins");

    public final String key;

    DatabaseTable(String key) {
        this.key = key;
    }
}
