package de.benji.naturoforumsmp.API.ConfigAPI;

public enum ConfigKey {
    BrausebadLoc("Brausebad.Locs"),
    CarpetDuperLoc("CarpetDuper.Loc"),
    SMPPlugins("SMP.Plugins"),
    SMPJoined("SMP.Joined"),
    MsgColors("Msg.Colors"),
    NPCShopsItemstore("NPCShops.Itemstore"),
    NPCShopsNPCs("NPCShops.NPCs"),
    StatusData("Status.Data"),
    StatusAccess("Status.Access"),
    StatusAutoAfk("Status.AutoAfk"),
    StatusPlayerisAfk("Status.PlayerisAfk"),
    StatusCurrent("Status.Current"),
    StatusAfk("Status.Afk"),
    SMPPermissions("SMP.Permissions"),
    SpawnEyltraHasElytra("SpawnElytra.HasElytra"),
    SpawnElytraChestplates("SpawnElytra.Chestplates");

    public final String key;

    ConfigKey(String key) {
        this.key = key;
    }
}
