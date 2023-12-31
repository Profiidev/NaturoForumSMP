package de.benji.naturoforumsmp.Main;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.DataStoreAPI.DataStoreAPI;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PermissionAPI.Permission;
import de.benji.naturoforumsmp.API.PermissionAPI.PermissionManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginInfo;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginManager;
import de.benji.naturoforumsmp.CarpetDuper.Main.CarpetDuperMain;
import de.benji.naturoforumsmp.Homes.Commands.HomeCommand;
import de.benji.naturoforumsmp.Homes.Commands.SetHomeCommand;
import de.benji.naturoforumsmp.Homes.Main.HomesMain;
import de.benji.naturoforumsmp.Listener.*;
import de.benji.naturoforumsmp.Main.SMP.Commands.*;
import de.benji.naturoforumsmp.Main.SMP.Listeners.SMPJoin;
import de.benji.naturoforumsmp.Main.SMP.TPS.TpsTicker;
import de.benji.naturoforumsmp.Main.SMP.Util.SMPInvs;
import de.benji.naturoforumsmp.Msg.Commands.RCommand;
import de.benji.naturoforumsmp.Msg.Commands.SetMsgColorCommand;
import de.benji.naturoforumsmp.Msg.Main.MsgMain;
import de.benji.naturoforumsmp.NPCShops.Commands.NPCCommand;
import de.benji.naturoforumsmp.NPCShops.Main.NPCShopsMain;
import de.benji.naturoforumsmp.Sanddupe.Main.SanddupeMain;
import de.benji.naturoforumsmp.Spawn.Commands.Spawn;
import de.benji.naturoforumsmp.Spawn.Main.SpawnMain;
import de.benji.naturoforumsmp.SpawnElytra.Main.SpawnElytraMain;
import de.benji.naturoforumsmp.Status.Commands.StatusCommand;
import de.benji.naturoforumsmp.Status.Main.StatusMain;
import de.benji.naturoforumsmp.Worldtracker.Main.WorldtrackMain;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private static List<UUID> joined;
    private static TpsTicker tpsTicker;

    @Override
    public void onEnable() {
        boolean databaseEnabled = getConfig().getBoolean("Database.enabled");
        getConfig().set("Database.enabled", databaseEnabled);
        saveConfig();

        GlobalManager.setDatabaseEnable(databaseEnabled);
        GlobalManager.setInstance(this);
        GlobalManager.setPluginManager(Bukkit.getPluginManager());
        GlobalManager.setBoard(Bukkit.getScoreboardManager().getMainScoreboard());
        GlobalManager.setInvAPI(new InvAPI());
        GlobalManager.setSubpluginManager(new SubpluginManager());
        GlobalManager.setInventoryManager(new InventoryManager());
        GlobalManager.setPermissionManager(new PermissionManager());
        GlobalManager.setListenerManager(new ListenerManager());
        GlobalManager.setInputManager(new InputManager());
        GlobalManager.setDataStoreAPI(new DataStoreAPI(GlobalManager.isDatabaseEnable(), new ConfigAPI(this)));

        enableMain();

        HashMap<String, Boolean> plugins = GlobalManager.getDataStoreAPI().loadStringBooleanHashMap(DataKey.SMPPlugins);

        SubpluginManager subpluginManager = GlobalManager.getSubpluginManager();
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Msg.key) != null ? plugins.get(Subplugin.Msg.key) : false, false, Subplugin.Msg, MsgMain::onEnable, MsgMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Sanddupe.key) != null ? plugins.get(Subplugin.Sanddupe.key) : false, false, Subplugin.Sanddupe, SanddupeMain::onEnable, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Status.key) != null ? plugins.get(Subplugin.Status.key) : false, false, Subplugin.Status, StatusMain::onEnable, StatusMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Worldtracker.key) != null ? plugins.get(Subplugin.Worldtracker.key) : false, false, Subplugin.Worldtracker, WorldtrackMain::onEnable, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.NPCShops.key) != null ? plugins.get(Subplugin.NPCShops.key) : false, true, Subplugin.NPCShops, NPCShopsMain::onEnable, NPCShopsMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.CarpetDuper.key) != null ? plugins.get(Subplugin.CarpetDuper.key) : false, false, Subplugin.CarpetDuper, CarpetDuperMain::onEnable, CarpetDuperMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.SpawnElytra.key) != null ? plugins.get(Subplugin.SpawnElytra.key) : false, false, Subplugin.SpawnElytra, SpawnElytraMain::onEnable, SpawnElytraMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Homes.key) != null ? plugins.get(Subplugin.Homes.key) : false, false, Subplugin.Homes, HomesMain::onEnable, HomesMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Spawn.key) != null ? plugins.get(Subplugin.Spawn.key) : false, false, Subplugin.Spawn, SpawnMain::onEnable, null));
        subpluginManager.startAllEnabledSubplugins();

        registerCommandsAndListeners();
    }

    @Override
    public void onDisable() {
        disableMain();
        GlobalManager.getSubpluginManager().disableAllSubplugins();
        saveConfig();
        GlobalManager.getDataStoreAPI().disconnect();
    }

    private void registerCommandsAndListeners() {
        //MAIN
        getCommand("smp").setExecutor(new PluginCommand());
        getCommand("smpupdate").setExecutor(new PluginUpdateCommand());
        getCommand("smphelp").setExecutor(new HelpCommand());
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("calc").setExecutor(new CalculatorCommand());
        getCommand("portal").setExecutor(new PortalCommand());

        //SUBPLUGIN
        getCommand("status").setExecutor(new StatusCommand());
        getCommand("setmsgcolor").setExecutor(new SetMsgColorCommand());
        getCommand("r").setExecutor(new RCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("npc").setExecutor(new NPCCommand());
        getCommand("spawn").setExecutor(new Spawn());

        PluginManager pluginManager = GlobalManager.getPluginManager();
        JavaPlugin instance = GlobalManager.getInstance();
        pluginManager.registerEvents(new Join(), instance);
        pluginManager.registerEvents(new Quit(), instance);
        pluginManager.registerEvents(new InvClick(), instance);
        pluginManager.registerEvents(new Portal(), instance);
        pluginManager.registerEvents(new WorldChange(), instance);
        pluginManager.registerEvents(new Swap(), instance);
        pluginManager.registerEvents(new Death(), instance);
        pluginManager.registerEvents(new BlockBreak(), instance);
        pluginManager.registerEvents(new BlockPlace(), instance);
        pluginManager.registerEvents(new BlockDispense(), instance);
        pluginManager.registerEvents(new CommandPreprocess(), instance);
        pluginManager.registerEvents(new EntityClick(), instance);
        pluginManager.registerEvents(new Click(), instance);
        pluginManager.registerEvents(new PlayerMove(), instance);

        SMPInvs.registerPluginInvClicks();
    }

    private void enableMain() {
        GlobalManager.getListenerManager().addAlwaysCallback(Listeners.Join, SMPJoin::onJoin);

        joined = new ArrayList<>();
        joined.addAll(GlobalManager.getDataStoreAPI().loadUUIDList(DataKey.SMPJoined));
        tpsTicker = new TpsTicker();

        HashMap<UUID, List<Permission>> permissions = GlobalManager.getDataStoreAPI().loadUUIDPermissionListHashMap(DataKey.SMPPermissions);
        if(!permissions.isEmpty())
            GlobalManager.setPermissionManager(new PermissionManager(permissions));
    }

    private void disableMain() {
        DataStoreAPI dataStoreAPI = GlobalManager.getDataStoreAPI();
        dataStoreAPI.saveStringBooleanHashMap(DataKey.SMPPlugins, GlobalManager.getSubpluginManager().getAllSubpluginsStatus());
        dataStoreAPI.saveUUIDList(DataKey.SMPJoined, joined);
        dataStoreAPI.saveUUIDPermissionListHashMap(DataKey.SMPPermissions, GlobalManager.getPermissionManager().getPermissions());
    }

    public static void setJoined(List<UUID> joined) {
        Main.joined = joined;
    }

    public static List<UUID> getJoined() {
        return joined;
    }
}
