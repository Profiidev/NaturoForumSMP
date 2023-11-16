package de.benji.naturoforumsmp.API.DataStoreAPI;

public enum DataKey {
    CarpetDuperLoc("CarpetDuper.Loc"),
    SMPPlugins("SMP.Plugins"),
    SMPJoined("SMP.Joined"),
    MsgColors("Msg.Colors"),
    NPCShopsItemstore("NPCShops.Itemstore"),
    NPCShopsNPCs("NPCShops.NPCs"),
    StatusData("Status.Data"),
    StatusAccess("Status.Access"),
    StatusCaches("Status.Caches"),
    StatusAfk("Status.Afk"),
    SMPPermissions("SMP.Permissions"),
    Homes("Homes");

    public final String configKey;
    public final String databaseKey;

    DataKey(String configKey) {
        this.configKey = configKey;
        this.databaseKey = configKey.replace(".", "_");
    }
}
