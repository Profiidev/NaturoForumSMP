package de.benji.naturoforumsmp.Msg.Listener;

import de.benji.naturoforumsmp.Msg.Main.MsgColorManager;
import de.benji.naturoforumsmp.Msg.Main.MsgMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MsgCommandPreprocess {
    public static void onCommand(Event event) {
        PlayerCommandPreprocessEvent e = (PlayerCommandPreprocessEvent) event;

        String[] args = e.getMessage().split(" ");
        Player p = e.getPlayer();

        if(!args[0].equalsIgnoreCase("/msg"))
            return;

        e.setCancelled(true);

        if(args.length <= 2) {
            p.sendMessage("§cPlease use §6/msg <Player> <Message> §c!");
            return;
        }

        Player t = Bukkit.getPlayer(args[1]);
        if(t == null) {
            p.sendMessage("§cPlayer not found!");
            return;
        }

        MsgColorManager mcm = MsgMain.getMsgColorManager();
        mcm.setLastMsg(p.getUniqueId(), t.getUniqueId());
        mcm.setLastMsg(t.getUniqueId(), p.getUniqueId());

        String[] msgArray = new String[args.length - 2];
        System.arraycopy(args, 2, msgArray, 0, args.length - 2);

        p.sendMessage(mcm.colorMsg(p.getUniqueId(), String.join(" ", msgArray), true, p.getName(), t.getName()));
        t.sendMessage(mcm.colorMsg(t.getUniqueId(), String.join(" ", msgArray), false, p.getName(), t.getName()));
    }
}
