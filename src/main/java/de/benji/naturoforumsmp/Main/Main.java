package de.benji.naturoforumsmp.Main;

import de.benji.naturoforumsmp.API.ConfigAPI.ConfigAPI;
import de.benji.naturoforumsmp.API.ConfigAPI.ConfigKey;
import de.benji.naturoforumsmp.API.DatsBaseAPI.DatabaseAPI;
import de.benji.naturoforumsmp.API.DatsBaseAPI.MySQL;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PermissionAPI.PermissionManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginInfo;
import de.benji.naturoforumsmp.API.PluginAPI.SubpluginManager;
import de.benji.naturoforumsmp.CarpetDuper.Main.CarpetDuperMain;
import de.benji.naturoforumsmp.Listener.*;
import de.benji.naturoforumsmp.Main.SMP.Commands.*;
import de.benji.naturoforumsmp.Main.SMP.Listeners.SMPJoin;
import de.benji.naturoforumsmp.Main.SMP.TPS.TpsTicker;
import de.benji.naturoforumsmp.Msg.Command.R;
import de.benji.naturoforumsmp.Msg.Command.SetMsgColor;
import de.benji.naturoforumsmp.Msg.Main.MsgMain;
import de.benji.naturoforumsmp.Sanddupe.Main.SanddupeMain;
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
        GlobalManager.setInstance(this);
        GlobalManager.setPluginManager(Bukkit.getPluginManager());
        GlobalManager.setBoard(Bukkit.getScoreboardManager().getMainScoreboard());
        GlobalManager.setInvAPI(new InvAPI());
        GlobalManager.setConfigAPI(new ConfigAPI(this));
        GlobalManager.setSubpluginManager(new SubpluginManager());
        GlobalManager.setInventoryManager(new InventoryManager());
        GlobalManager.setPermissionManager(new PermissionManager());
        GlobalManager.setListenerManager(new ListenerManager());
        GlobalManager.setInputManager(new InputManager());
        if(GlobalManager.isDatabaseEnable())
            GlobalManager.setDatabaseAPI(new DatabaseAPI(new MySQL("localhost", 3306, "naturoforumsmp", "root", "69420")));

        enableMain();

        HashMap<String, Boolean> plugins = GlobalManager.getConfigAPI().loadStringBooleanHashMap(ConfigKey.SMPPlugins.key);
        //Brausebad Headshop NPCShops Homes
        SubpluginManager subpluginManager = GlobalManager.getSubpluginManager();
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Brausebad.key) != null ? plugins.get(Subplugin.Brausebad.key) : false, true, Subplugin.Brausebad, null, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Headshop.key) != null ? plugins.get(Subplugin.Headshop.key) : false, true, Subplugin.Headshop, null, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Msg.key) != null ? plugins.get(Subplugin.Msg.key) : false, true, Subplugin.Msg, MsgMain::onEnable, MsgMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Sanddupe.key) != null ? plugins.get(Subplugin.Sanddupe.key) : false, false, Subplugin.Sanddupe, SanddupeMain::onEnable, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Status.key) != null ? plugins.get(Subplugin.Status.key) : false, false, Subplugin.Status, StatusMain::onEnable, StatusMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Worldtracker.key) != null ? plugins.get(Subplugin.Worldtracker.key) : false, false, Subplugin.Worldtracker, WorldtrackMain::onEnable, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.NPCShops.key) != null ? plugins.get(Subplugin.NPCShops.key) : false, true, Subplugin.NPCShops, null, null));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.CarpetDuper.key) != null ? plugins.get(Subplugin.CarpetDuper.key) : false, false, Subplugin.CarpetDuper, CarpetDuperMain::onEnable, CarpetDuperMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.SpawnElytra.key) != null ? plugins.get(Subplugin.SpawnElytra.key) : false, false, Subplugin.SpawnElytra, SpawnElytraMain::onEnable, SpawnElytraMain::onDisable));
        subpluginManager.addSubplugin(new SubpluginInfo(plugins.get(Subplugin.Homes.key) != null ? plugins.get(Subplugin.Homes.key) : false, true, Subplugin.Homes, null, null));
        subpluginManager.startAllEnabledSubplugins();

        registerCommandsAndListeners();
    }

    @Override
    public void onDisable() {
        disableMain();
        GlobalManager.getSubpluginManager().disableAllSubplugins();
        saveConfig();
    }

    private void registerCommandsAndListeners() {
        //MAIN
        getCommand("smp").setExecutor(new PluginCommand());
        getCommand("smphelp").setExecutor(new HelpCommand());
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("calc").setExecutor(new CalculatorCommand());
        getCommand("portal").setExecutor(new PortalCommand());
        getCommand("setmsgcolor").setExecutor(new SetMsgColor());
        getCommand("r").setExecutor(new R());

        //SUBPLUGIN
        getCommand("status").setExecutor(new StatusCommand());

        PluginManager pluginManager = GlobalManager.getPluginManager();
        pluginManager.registerEvents(new Join(), GlobalManager.getInstance());
        pluginManager.registerEvents(new Quit(), GlobalManager.getInstance());
        pluginManager.registerEvents(new InvClick(), GlobalManager.getInstance());
        pluginManager.registerEvents(new Portal(), GlobalManager.getInstance());
        pluginManager.registerEvents(new WorldChange(), GlobalManager.getInstance());
        pluginManager.registerEvents(new Swap(), GlobalManager.getInstance());
        pluginManager.registerEvents(new Death(), GlobalManager.getInstance());
        pluginManager.registerEvents(new BlockBreak(), GlobalManager.getInstance());
        pluginManager.registerEvents(new BlockPlace(), GlobalManager.getInstance());
        pluginManager.registerEvents(new BlockDispense(), GlobalManager.getInstance());
        pluginManager.registerEvents(new CommandPreprocess(), GlobalManager.getInstance());
    }

    private void enableMain() {
        GlobalManager.getListenerManager().addAlwaysCallback(Listeners.Join, SMPJoin::onJoin);

        joined = new ArrayList<>();
        joined.addAll(GlobalManager.getConfigAPI().loadUUIDList(ConfigKey.SMPJoined.key));
        tpsTicker = new TpsTicker();

        HashMap<UUID, List<String>> permissions = GlobalManager.getConfigAPI().loadUUIDStringListHashMap(ConfigKey.SMPPermissions.key);
        if(!permissions.isEmpty())
            GlobalManager.setPermissionManager(new PermissionManager(permissions));
    }

    private void disableMain() {
        ConfigAPI configAPI = GlobalManager.getConfigAPI();
        configAPI.saveStringBooleanHashMap(ConfigKey.SMPPlugins.key, GlobalManager.getSubpluginManager().getAllSubpluginsStatus());
        configAPI.saveUUIDList(ConfigKey.SMPJoined.key, joined);
        configAPI.saveUUIDStringListHashMap(ConfigKey.SMPPermissions.key, GlobalManager.getPermissionManager().getPermissions());
    }

    public static void setJoined(List<UUID> joined) {
        Main.joined = joined;
    }

    public static List<UUID> getJoined() {
        return joined;
    }
}