package de.benji.naturoforumsmp.CarpetDuper.Listener;

import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import de.benji.naturoforumsmp.CarpetDuper.Main.CarpetDuperMain;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

public class CarpetDuperBlockPlace {
    public static void onBlockPlace(Event event) {
        BlockPlaceEvent e = (BlockPlaceEvent) event;
        if(!e.getItemInHand().getItemMeta().getDisplayName().equals(ItemTitles.carpetDuper_Duper))
            return;

        CarpetDuperMain.getDupers().add(e.getBlock().getLocation());
    }
}
