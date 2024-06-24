package de.benji.naturoforumsmp.NPCShops.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class NPCManager {
    private final List<NPC> npcs;
    private final HashMap<UUID, UUID> lastOpenNPC;
    private HashMap<UUID, ItemRedeemType> redeemType;
    private final HashMap<UUID, List<Inventory>> mobInvs;
    private final HashMap<UUID, ItemInputType> itemInput;

    public NPCManager() {
        npcs = new ArrayList<>();
        lastOpenNPC = new HashMap<>();
        redeemType = new HashMap<>();
        mobInvs = new HashMap<>();
        itemInput = new HashMap<>();
    }

    public void createNPC(EntityType<? extends Mob> type, Location loc, UUID owner, Material sellItem, Material payItem, int itemsPerSell, int itemsPerPay, int sellStock, int payStock, String name, float yHeadRot) {
        NPC npc = new NPC(type, loc, owner);
        npc.loadData(sellItem, payItem, itemsPerSell, itemsPerPay, sellStock, payStock, name);
        npcs.add(npc);
        npc.setPosRaw(loc.x(), loc.y(), loc.z());
        npc.setDeltaMovement(0, 1, 0);
        npc.setYHeadRot(yHeadRot);
        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftLivingEntity) npc.getBukkitEntity()).setAI(false);
                npc.setPosRaw(loc.x(), loc.y(), loc.z());
            }
        }.runTaskLater(GlobalManager.getInstance(), 2);
    }

    public void deleteNPC(NPC npc) {
        npc.kill();
        npcs.remove(npc);
    }

    public boolean npcExists(UUID uuid) {
        NPC result = null;
        for(NPC npc: npcs) {
            if(npc.getUUID().equals(uuid))
                result = npc;
        }
        return result != null;
    }

    public NPC findNPCByID(UUID uuid) {
        NPC result = null;
        for(NPC npc: npcs) {
            if(npc.getUUID().equals(uuid))
                result = npc;
        }
        return result;
    }

    public void closeAllOpenNPCs() {
        for(Player p: Bukkit.getOnlinePlayers()) {
            if(p.getOpenInventory().getTitle().equals(InvTitles.npcs_Shops))
                p.closeInventory();
        }
    }

    public void closeOpenNPCByID(UUID uuid) {
        for(UUID player: lastOpenNPC.keySet()) {
            if(uuid.equals(lastOpenNPC.get(player)) && Bukkit.getPlayer(player) != null)
                Objects.requireNonNull(Bukkit.getPlayer(player)).closeInventory();
        }
    }

    public void setRedeemType(UUID uuid, ItemRedeemType type) {
        redeemType.put(uuid, type);
    }

    public ItemRedeemType getRedeemType(UUID uuid) {
        return redeemType.get(uuid);
    }

    public void setMobInv(UUID uuid, List<Inventory> invs) {
        mobInvs.put(uuid, invs);
    }

    public List<Inventory> getMobInv(UUID uuid) {
        return mobInvs.get(uuid);
    }

    public UUID getLastOpenNPC(UUID player) {
        return lastOpenNPC.get(player);
    }

    public void putLastOpenNPC(UUID player, UUID npc) {
        lastOpenNPC.put(player, npc);
    }

    public void setItemInput(UUID uuid, ItemInputType type) {
        itemInput.put(uuid, type);
    }

    public ItemInputType getItemInput(UUID uuid) {
        return itemInput.get(uuid) == null ? ItemInputType.None : itemInput.get(uuid);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public HashMap<UUID, ItemRedeemType> getRedeemTypes() {
        return redeemType;
    }

    public void setRedeemTypes(HashMap<UUID, ItemRedeemType> redeemType) {
        this.redeemType = redeemType;
    }

    public enum ItemRedeemType {
        None,
        EC
    }

    public enum ItemInputType {
        SellItem,
        PayItem,
        None
    }
}
