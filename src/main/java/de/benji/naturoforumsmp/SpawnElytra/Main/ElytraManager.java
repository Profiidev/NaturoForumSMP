package de.benji.naturoforumsmp.SpawnElytra.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ElytraManager {
    List<UUID> hasElytra;
    List<UUID> hasBoosted;
    HashMap<UUID, ItemStack> chestplates;

    SpawnElytraTicker spawnElytraTicker;

    public ElytraManager() {
        hasElytra = new ArrayList<>();
        hasBoosted = new ArrayList<>();
        chestplates = new HashMap<>();
        spawnElytraTicker = new SpawnElytraTicker();
    }

    public boolean hasSpawnElytra(UUID uuid) {
        return hasElytra.contains(uuid);
    }

    public void giveElytra(Player p) {
        InvAPI invAPI = GlobalManager.getInvAPI();
        ItemStack is = invAPI.createIS(Material.ELYTRA, "§bSpawn Elytra", Collections.singletonList("§7Your Chestplate was saved and will be back"));

        if(p.getInventory().getChestplate() != null)
            chestplates.put(p.getUniqueId(), p.getInventory().getChestplate());
        hasElytra.add(p.getUniqueId());

        ItemMeta im = is.getItemMeta();
        im.addEnchant(Enchantment.BINDING_CURSE, 2, true);
        im.addEnchant(Enchantment.VANISHING_CURSE, 2, true);
        im.addEnchant(Enchantment.DURABILITY, 500, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);

        p.getInventory().setChestplate(is);
    }

    public void removeElytra(Player p) {
        hasElytra.remove(p.getUniqueId());

        if(chestplates.containsKey(p.getUniqueId())) {
            p.getInventory().setChestplate(chestplates.get(p.getUniqueId()));
            chestplates.remove(p.getUniqueId());
        } else {
            p.getInventory().setChestplate(null);
        }
    }

    public void dropChestplate(Player p) {
        hasElytra.remove(p.getUniqueId());

        if(!chestplates.containsKey(p.getUniqueId()))
            return;

        Location deathLoc = p.getLocation();
        deathLoc.getWorld().dropItem(deathLoc, chestplates.get(p.getUniqueId()));
        chestplates.remove(p.getUniqueId());
    }

    public boolean hasBoosted(UUID uuid) {
        return hasBoosted.contains(uuid);
    }

    public void resetBoost(UUID uuid) {
        hasBoosted.remove(uuid);
    }

    public void boosted(UUID uuid) {
        hasBoosted.add(uuid);
    }
}
