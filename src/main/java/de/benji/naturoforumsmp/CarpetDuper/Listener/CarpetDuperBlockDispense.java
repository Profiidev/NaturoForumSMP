package de.benji.naturoforumsmp.CarpetDuper.Listener;

import de.benji.naturoforumsmp.CarpetDuper.Main.CarpetDuperMain;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;

import java.util.Arrays;

public class CarpetDuperBlockDispense {
    public static void onBlockDispense(Event event) {
        BlockDispenseEvent e = (BlockDispenseEvent) event;
        Block b = e.getBlock();

        if(!(b.getState() instanceof Dropper))
            return;

        Dropper dropper = (Dropper) b.getState();
        if(!CarpetDuperMain.getDupers().contains(dropper.getLocation()))
            return;

        Arrays.asList(dropper.getInventory().getContents()).forEach(is -> {
            if(is != null)
                if(is.getType().equals(e.getItem().getType()))
                    is.setAmount(is.getAmount() + 1);
        });
    }
}
