package de.benji.naturoforumsmp.Worldtracker.Listener;

import de.benji.naturoforumsmp.Status.Main.StatusManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldtrackerJoin {
    public static void onJoin(Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;

        Player p = e.getPlayer();
        World.Environment en = p.getWorld().getEnvironment();

        switch (en) {
            case NORMAL: {
                StatusManager.setPlayerPrefixSuffix(null, " §7[§aO§7]", p);
                break;
            }
            case NETHER: {
                StatusManager.setPlayerPrefixSuffix(null, " §7[§cN§7]", p);
                break;
            }
            case THE_END: {
                StatusManager.setPlayerPrefixSuffix(null, " §7[§eE§7]", p);
                break;
            }
        }
    }
}
