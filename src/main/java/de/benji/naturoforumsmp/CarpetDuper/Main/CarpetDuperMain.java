package de.benji.naturoforumsmp.CarpetDuper.Main;

import de.benji.naturoforumsmp.API.DataStoreAPI.DataKey;
import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.ListenerAPI.ListenerManager;
import de.benji.naturoforumsmp.API.ListenerAPI.Listeners;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import de.benji.naturoforumsmp.CarpetDuper.Listener.CarpetDuperBlockBreak;
import de.benji.naturoforumsmp.CarpetDuper.Listener.CarpetDuperBlockDispense;
import de.benji.naturoforumsmp.CarpetDuper.Listener.CarpetDuperBlockPlace;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class CarpetDuperMain {
    static List<Location> dupers;

    public static void onEnable() {
        ListenerManager lm = GlobalManager.getListenerManager();
        lm.addCallback(Subplugin.CarpetDuper, Listeners.BlockBreak, CarpetDuperBlockBreak::onBlockBreak);
        lm.addCallback(Subplugin.CarpetDuper, Listeners.BlockPlace, CarpetDuperBlockPlace::onBlockPlace);
        lm.addCallback(Subplugin.CarpetDuper, Listeners.BlockDispense, CarpetDuperBlockDispense::onBlockDispense);

        dupers = GlobalManager.getDataStoreAPI().loadLocationList(DataKey.CarpetDuperLoc);

        //recipe
        ItemStack i = GlobalManager.getInvAPI().createIS(Material.DROPPER, ItemTitles.carpetDuper_Duper);

        NamespacedKey k = new NamespacedKey(GlobalManager.getInstance(), "1a515f1ga15d4r4ghd655g1as135wafa");

        ShapedRecipe shop = new ShapedRecipe(k, i);
        shop.shape("AB ", "CB ", "   ");
        shop.setIngredient('A', Material.WHITE_CARPET);
        shop.setIngredient('B', Material.SLIME_BLOCK);
        shop.setIngredient('C', Material.STICKY_PISTON);

        try {
            Bukkit.addRecipe(shop);
        } catch(Exception ignored) {}
    }

    public static void onDisable() {
        GlobalManager.getDataStoreAPI().saveLocationList(DataKey.CarpetDuperLoc, dupers);
    }

    public static List<Location> getDupers() {
        return dupers;
    }
}
