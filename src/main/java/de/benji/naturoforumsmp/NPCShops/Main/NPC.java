package de.benji.naturoforumsmp.NPCShops.Main;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.UUID;

public class NPC extends Mob {
    private Material sellItem;
    private Material payItem;
    private int itemsPerSell;
    private int itemsPerPay;
    private int sellStock;
    private int payStock;
    private final UUID owner;

    protected NPC(EntityType<? extends Mob> type, Location loc, UUID owner) {
        super(type, ((CraftWorld) loc.getWorld()).getHandle());

        this.getBukkitEntity().setPersistent(true);
        this.setCustomNameVisible(true);
        this.setCustomName(Component.nullToEmpty("§6Shop"));
        this.setSilent(true);
        this.setHealth(20000);
        this.setInvulnerable(true);

        this.level().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.DEFAULT);

        CompoundTag tag = new CompoundTag();
        this.save(tag);
        tag.putBoolean("PersistenceRequired", true);
        this.load(tag);

        sellItem = Material.DIRT;
        payItem = Material.DIAMOND;
        itemsPerSell = 1;
        itemsPerPay = 1;
        sellStock = 0;
        payStock = 0;
        this.owner = owner;
    }

    public void loadData(Material sellItem, Material payItem, int itemsPerSell, int itemsPerPay, int sellStock, int payStock, String name) {
        this.sellItem = sellItem;
        this.payItem = payItem;
        this.itemsPerSell = itemsPerSell;
        this.itemsPerPay = itemsPerPay;
        this.sellStock = sellStock;
        this.payStock = payStock;
        setName(name);
    }

    public void setName(String name) {
        setCustomName(Component.nullToEmpty(name.replace('&', '§')));
    }

    public Material getPayItem() {
        return payItem;
    }

    public Material getSellItem() {
        return sellItem;
    }

    public int getItemsPerSell() {
        return itemsPerSell;
    }

    public int getItemsPerPay() {
        return itemsPerPay;
    }

    public int getSellStock() {
        return sellStock;
    }

    public int getPayStock() {
        return payStock;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setSellItem(Material sellItem) {
        this.sellItem = sellItem;
    }

    public void setPayItem(Material payItem) {
        this.payItem = payItem;
    }

    public void setItemsPerSell(int itemsPerSell) {
        this.itemsPerSell = itemsPerSell;
    }

    public void setItemsPerPay(int itemsPerPay) {
        this.itemsPerPay = itemsPerPay;
    }

    public void setSellStock(int sellStock) {
        this.sellStock = sellStock;
    }

    public void setPayStock(int payStock) {
        this.payStock = payStock;
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.singletonList(ItemStack.fromBukkitCopy(new org.bukkit.inventory.ItemStack(Material.AIR)));
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot equipmentSlot) {
        return ItemStack.fromBukkitCopy(new org.bukkit.inventory.ItemStack(Material.AIR));
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot equipmentSlot, @NotNull ItemStack itemStack) {
        this.setItemSlot(equipmentSlot, itemStack, true);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
