package de.benji.naturoforumsmp.Homes.Main;

import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.GlobalManager;

public class HomesMain {
    private static HomeManager homeManager;

    public static void onEnable() {
        homeManager = new HomeManager();

        homeManager.setHomes(GlobalManager.getDataStoreAPI().loadUUIDLocationHashMap(DataKey.Homes));
    }

    public static void onDisable() {
        GlobalManager.getDataStoreAPI().saveUUIDLocationHashMap(DataKey.Homes, homeManager.getHomes());
    }

    public static HomeManager getHomeManager() {
        return homeManager;
    }
}
