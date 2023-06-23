package de.benji.naturoforumsmp.API;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.DatsBaseAPI.DatabaseAPI;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.PermissionAPI.PermissionManager;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class GlobalManager {
    private static JavaPlugin instance;
    private static PluginManager pluginManager;
    private static Scoreboard board;

    private static InvAPI invAPI;
    private static ConfigAPI configAPI;
    private static SubpluginManager subpluginManager;
    private static InventoryManager inventoryManager;
    private static PermissionManager permissionManager;
    private static ListenerManager listenerManager;
    private static InputManager inputManager;
    private static DatabaseAPI databaseAPI;
    private  static boolean databaseEnable = false;

    public static JavaPlugin getInstance() {
        return instance;
    }
    public static PluginManager getPluginManager() {
        return pluginManager;
    }
    public static Scoreboard getBoard() {
        return board;
    }
    public static InvAPI getInvAPI() {
        return invAPI;
    }
    public static ConfigAPI getConfigAPI() {
        return configAPI;
    }
    public static SubpluginManager getSubpluginManager() {
        return subpluginManager;
    }
    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }
    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }
    public static ListenerManager getListenerManager() {
        return listenerManager;
    }
    public static InputManager getInputManager() {
        return inputManager;
    }
    public static DatabaseAPI getDatabaseAPI() {
        return databaseAPI;
    }
    public static boolean isDatabaseEnable() {
        return databaseEnable;
    }

    public static void setInstance(JavaPlugin instance) {
        GlobalManager.instance = instance;
    }
    public static void setPluginManager(PluginManager pluginManager) {
        GlobalManager.pluginManager = pluginManager;
    }
    public static void setBoard(Scoreboard board) {
        GlobalManager.board = board;
    }
    public static void setInvAPI(InvAPI invAPI) {
        GlobalManager.invAPI = invAPI;
    }
    public static void setConfigAPI(ConfigAPI configAPI) {
        GlobalManager.configAPI = configAPI;
    }
    public static void setSubpluginManager(SubpluginManager subpluginManager) {
        GlobalManager.subpluginManager = subpluginManager;
    }
    public static void setInventoryManager(InventoryManager inventoryManager) {
        GlobalManager.inventoryManager = inventoryManager;
    }
    public static void setPermissionManager(PermissionManager permissionManager) {
        GlobalManager.permissionManager = permissionManager;
    }
    public static void setListenerManager(ListenerManager listenerManager) {
        GlobalManager.listenerManager = listenerManager;
    }
    public static void setInputManager(InputManager inputManager) {
        GlobalManager.inputManager = inputManager;
    }
    public static void setDatabaseAPI(DatabaseAPI databaseAPI) {
        GlobalManager.databaseAPI = databaseAPI;
    }
    public static void setDatabaseEnable(boolean databaseEnable) {
        GlobalManager.databaseEnable = databaseEnable;
    }
}
