package de.benji.naturoforumsmp.Homes.Main;

public class HomesMain {
    private static HomeManager homeManager;

    public static void onEnable() {
        homeManager = new HomeManager();

        //TODO config
    }

    public static void onDisable() {

    }

    public static HomeManager getHomeManager() {
        return homeManager;
    }
}
