package de.benji.naturoforumsmp.Status.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.Status.Util.Status;
import de.benji.naturoforumsmp.Status.Util.StatusCache;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

public class StatusManager {
    private final AFKTicker afkTicker;
    private String afkStatusKey;
    private HashMap<String, Status> statusData;
    private HashMap<UUID, StatusCache> statusCaches;

    public StatusManager() {
        afkTicker = new AFKTicker();
        statusData = new HashMap<>();
        statusCaches = new HashMap<>();
    }

    public void setPlayerStatus(String statusKey, Player p) {
        setPlayerPrefixSuffix("§7[§r" + statusData.get(statusKey).display + "§7] ", null, p);
        statusCaches.get(p.getUniqueId()).currentStatus = statusKey;
    }

    public void setPlayerStatusNotCurrent(String statusKey, Player p) {
        setPlayerPrefixSuffix("§7[§r" + statusData.get(statusKey).display + "§7] ", null, p);
    }

    public static void setPlayerPrefixSuffix(String prefix, String suffix, Player p) {
        try {
            Scoreboard board = GlobalManager.getBoard();
            Team team;

            if(board.getTeam(p.getName()) == null) {
                team = board.registerNewTeam(p.getName());
            } else {
                team = board.getTeam(p.getName());
            }

            if(prefix != null)
                team.setPrefix(prefix);
            if(suffix != null)
                team.setSuffix(suffix);
            team.addPlayer(p);
        } catch (NullPointerException e) {e.printStackTrace();}
    }

    public HashMap<String, Status> getStatusData() {
        return statusData;
    }
    public HashMap<UUID, StatusCache> getStatusCaches() {
        return statusCaches;
    }
    public String getAfkStatusKey() {
        return afkStatusKey;
    }

    public void setStatusData(HashMap<String, Status> statusData) {
        this.statusData = statusData;
    }
    public void setStatusCaches(HashMap<UUID, StatusCache> statusCaches) {
        this.statusCaches = statusCaches;
    }
    public void setAfkStatusKey(String afkStatusKey) {
        if(afkStatusKey == null)
            afkStatusKey = "";
        this.afkStatusKey = afkStatusKey;
    }
}
