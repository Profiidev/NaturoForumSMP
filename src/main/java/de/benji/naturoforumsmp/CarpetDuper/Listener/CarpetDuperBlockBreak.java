package de.benji.naturoforumsmp.CarpetDuper.Listener;

import de.benji.naturoforumsmp.CarpetDuper.Main.CarpetDuperMain;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class CarpetDuperBlockBreak {
    public static void onBlockBreak(Event event) {
        BlockBreakEvent e = (BlockBreakEvent) event;
        if(!CarpetDuperMain.getDupers().contains(e.getBlock().getLocation()) || !e.getBlock().getType().equals(Material.DROPPER))
            return;

        CarpetDuperMain.getDupers().remove(e.getBlock().getLocation());
    }
}
