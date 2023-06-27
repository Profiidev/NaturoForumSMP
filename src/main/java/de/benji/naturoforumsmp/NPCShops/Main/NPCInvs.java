package de.benji.naturoforumsmp.NPCShops.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InputAPI.InputKey;
import de.benji.naturoforumsmp.API.InputAPI.InputManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InvEnums;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class NPCInvs {
    private static final InvAPI invAPI = GlobalManager.getInvAPI();

    public static Inventory getOwnerInv(UUID uuid) {
        Inventory i = invAPI.createInv(4, InvTitles.npcs_Shops, InvEnums.Full, true, 31);
        NPC npc = NPCShopsMain.getNpcManager().findNPCByID(uuid);

        i.setItem(10, invAPI.createIS(npc.getSellItem(), npc.getItemsPerSell(), ItemTitles.npc_ChangeSell));
        i.setItem(11, invAPI.createIS(Material.CHEST, ItemTitles.npc_RefillSell, Collections.singletonList("Current stock: " + npc.getSellStock())));
        i.setItem(19, invAPI.createIS(npc.getPayItem(), npc.getItemsPerPay(), ItemTitles.npc_ChangePay));
        i.setItem(20, invAPI.createIS(Material.DIAMOND, ItemTitles.npc_ClaimPay, Collections.singletonList("Current stock: " + npc.getPayStock())));

        i.setItem(15, invAPI.createIS(Material.COW_SPAWN_EGG, ItemTitles.npc_ChangeMob));
        i.setItem(16, invAPI.createIS(Material.OAK_SIGN, ItemTitles.npc_ChangeName));
        i.setItem(24, invAPI.createIS(Material.COMPASS, ItemTitles.npc_ChangeHeadRot));
        i.setItem(25, invAPI.createIS(Material.BARRIER, ItemTitles.npc_DeleteShop));

        return i;
    }

    public static Inventory getShopInv(UUID uuid) {
        Inventory i = invAPI.createInv(3, InvTitles.npcs_Shops, InvEnums.Full, true, 22);
        NPC npc = NPCShopsMain.getNpcManager().findNPCByID(uuid);

        String sellName = materialToString(npc.getSellItem());
        String payName = materialToString(npc.getPayItem());

        i.setItem(13, invAPI.createIS(npc.getSellItem(), npc.getItemsPerSell(), ItemTitles.npc_Trade, Collections.singletonList("§2Trade §a" + npc.getItemsPerSell() + " x " + sellName + " §2for §b" + npc.getItemsPerPay() + " x " + payName)));

        return i;
    }

    public static Inventory getMobInv(UUID uuid) {
        List<ItemStack> mobs = new ArrayList<>();
        Arrays.asList(NPCType.values()).forEach(npcType -> mobs.add(invAPI.createIS(npcType.spawnEgg, npcType.display)));

        Inventory i = invAPI.createInv(6, InvTitles.npcs_Mob, InvEnums.Circle, true, 49);

        i.setItem(48, invAPI.createIS(Material.ARROW, ItemTitles.backItem));

        List<Inventory> invs = invAPI.createInvPages(i, InvTitles.npcs_Mob, mobs, 45, 53);
        NPCShopsMain.getNpcManager().setMobInv(uuid, invs);
        return invs.get(0);
    }

    public static Inventory getSettingsInv(UUID uuid) {
        Inventory i = invAPI.createInv(3, InvTitles.npcs_Settings, true, 22);

        if(NPCShopsMain.getNpcManager().getRedeemTypes() == null)
            NPCShopsMain.getNpcManager().setRedeemType(uuid, NPCManager.ItemRedeemType.None);

        NPCManager.ItemRedeemType type = NPCShopsMain.getNpcManager().getRedeemType(uuid);

        i.setItem(13, invAPI.createIS(Material.CHEST, ItemTitles.npc_ChangeSettings, Arrays.asList("§" + (type.equals(NPCManager.ItemRedeemType.None) ? "d" : "8") + "None", "§" + (type.equals(NPCManager.ItemRedeemType.EC) ? "d" : "8") + "In Enderchest")));
        return i;
    }

    private static String materialToString(Material mat) {
        String name = mat.name().toLowerCase().replace('_', ' ');
        char[] nameChar = name.toCharArray();
        StringBuilder newName = new StringBuilder();
        for(int i = 0; i < nameChar.length; i++) {
            String upperCase = String.valueOf(nameChar[i]).toUpperCase();
            if(i == 0)
                newName.append(upperCase);
            else if(nameChar[i - 1] == ' ')
                newName.append(upperCase);
            else
                newName.append(nameChar[i]);
        }
        return newName.toString();
    }

    public static void registerListeners() {
        registerOwnerInvListeners();
        registerShopInvClicks();
        registerMobInvClicks();
    }

    private static void registerOwnerInvListeners() {
        InventoryManager im = GlobalManager.getInventoryManager();
        im.addItem(ItemTitles.npc_ChangeSell, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

            if(npc.getSellStock() != 0) {
                p.sendMessage("§cPlease empty Item to Sell first");
                p.closeInventory();
                return;
            }

            p.sendMessage("§6Select Item");
            NPCShopsMain.getNpcManager().setItemInput(e.getWhoClicked().getUniqueId(), NPCManager.ItemInputType.SellItem);
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_RefillSell, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

            boolean itemFound = false;
            for(ItemStack is: p.getInventory().getContents()) {
                if(is == null)
                    continue;
                if(is.getType() == null)
                    continue;
                if(!is.getType().equals(npc.getSellItem()))
                    continue;

                if(is.getAmount() > 0)
                    itemFound = true;
                npc.setSellStock(npc.getSellStock() + is.getAmount());
                is.setAmount(0);
            }

            if(itemFound) {
                p.closeInventory();
                p.openInventory(getOwnerInv(npc.getUUID()));
                return;
            }

            int maxStack = npc.getSellItem().getMaxStackSize();
            for(int i = 0; i < 36; i++) {
                ItemStack is = p.getInventory().getItem(i);

                if(is == null) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getSellItem(), Math.min(maxStack, npc.getSellStock())));
                    npc.setSellStock(npc.getSellStock() - Math.min(maxStack, npc.getSellStock()));
                    continue;
                }

                if(is.getType() == null || is.getType().equals(Material.AIR)) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getSellItem(), Math.min(maxStack, npc.getSellStock())));
                    npc.setSellStock(npc.getSellStock() - Math.min(maxStack, npc.getSellStock()));
                    continue;
                }

                if(!is.getType().equals(npc.getSellItem()))
                    continue;

                int free = npc.getSellItem().getMaxStackSize() - is.getAmount();
                int toAdd = Math.min(free, npc.getSellStock());
                is.setAmount(is.getAmount() + toAdd);
                npc.setSellStock(npc.getSellStock() - toAdd);
                p.getInventory().setItem(i, is);
            }

            p.closeInventory();
            p.openInventory(getOwnerInv(npc.getUUID()));
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_ChangePay, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

            if(npc.getPayStock() != 0) {
                p.sendMessage("§cPlease empty Item to Pay first");
                p.closeInventory();
                return;
            }

            p.sendMessage("§6Select Item");
            NPCShopsMain.getNpcManager().setItemInput(e.getWhoClicked().getUniqueId(), NPCManager.ItemInputType.PayItem);
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_ClaimPay, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));
            int maxStack = npc.getPayItem().getMaxStackSize();

            for(int i = 0; i < 36; i++) {
                ItemStack is = p.getInventory().getItem(i);

                if(is == null) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getPayItem(), Math.min(maxStack, npc.getPayStock())));
                    npc.setPayStock(npc.getPayStock() - Math.min(maxStack, npc.getPayStock()));
                    continue;
                }

                if(is.getType() == null || is.getType().equals(Material.AIR)) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getPayItem(), Math.min(maxStack, npc.getPayStock())));
                    npc.setPayStock(npc.getPayStock() - Math.min(maxStack, npc.getPayStock()));
                    continue;
                }

                if(!is.getType().equals(npc.getPayItem()))
                    continue;

                int free = npc.getPayItem().getMaxStackSize() - is.getAmount();
                int toAdd = Math.min(free, npc.getPayStock());
                is.setAmount(is.getAmount() + toAdd);
                npc.setPayStock(npc.getPayStock() - toAdd);
                p.getInventory().setItem(i, is);
            }
            p.closeInventory();
            p.openInventory(getOwnerInv(npc.getUUID()));
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_ChangeMob, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            p.openInventory(getMobInv(p.getUniqueId()));
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_ChangeName, InvTitles.npcs_Shops, e -> {
            GlobalManager.getInputManager().createInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.NPC_Name, (s, p) -> {
                NPCManager manager = NPCShopsMain.getNpcManager();
                NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));
                npc.setName(s);
                p.openInventory(getOwnerInv(npc.getUUID()));
            }));
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_ChangeHeadRot, InvTitles.npcs_Shops, e -> {
            GlobalManager.getInputManager().createIntInput((Player) e.getWhoClicked(), new InputManager.InputInfo(InputKey.NPC_HeadRot, (s, p) -> {
                NPCManager manager = NPCShopsMain.getNpcManager();
                NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));
                npc.setYHeadRot(Integer.parseInt(s));
                p.openInventory(getOwnerInv(npc.getUUID()));
            }), -180, 180);
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.npc_DeleteShop, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

            if(npc.getPayStock() != 0 || npc.getSellStock() != 0) {
                p.sendMessage("§cPlease empty NPC first");
                p.closeInventory();
                return;
            }

            if(!p.getInventory().addItem(invAPI.createIS(Material.VILLAGER_SPAWN_EGG, 1, InvTitles.npcs_Shops)).isEmpty()) {
                p.sendMessage("§cNo Inventory Space");
                p.closeInventory();
                return;
            }

            manager.closeOpenNPCByID(npc.getUUID());
            manager.deleteNPC(npc);
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.closeItem, InvTitles.npcs_Shops, e -> {
            e.getWhoClicked().closeInventory();
        }, Subplugin.NPCShops);
    }

    private static void registerShopInvClicks() {
        InventoryManager im = GlobalManager.getInventoryManager();
        im.addItem(ItemTitles.npc_Trade, InvTitles.npcs_Shops, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

            if(npc.getSellStock() < npc.getItemsPerSell()) {
                p.sendMessage("§cShop empty");
                p.closeInventory();
                return;
            }

            int itemsForPay = 0;
            for(ItemStack is: p.getInventory().getContents()) {
                if(is == null)
                    continue;
                if(is.getType() == null)
                    continue;
                if(!is.getType().equals(npc.getPayItem()))
                    continue;

                itemsForPay += is.getAmount();
            }

            int spaceForReceive = 0;
            int receiveAmount = npc.getItemsPerSell();
            for(int i = 0; i < 36; i++) {
                ItemStack is = p.getInventory().getItem(i);
                if(is == null) {
                    spaceForReceive = receiveAmount;
                    break;
                }
                if(is.getType() == null) {
                    spaceForReceive = receiveAmount;
                    break;
                }
                if(!is.getType().equals(npc.getSellItem()))
                    continue;

                spaceForReceive += (is.getType().getMaxStackSize() - is.getAmount());
            }

            if(spaceForReceive < receiveAmount) {
                p.sendMessage("§cNot enough Space");
                p.closeInventory();
                return;
            }

            if(itemsForPay < npc.getItemsPerPay()) {
                p.sendMessage("§cNot enough Items");
                p.closeInventory();
                return;
            }

            int itemsToRemove = npc.getItemsPerPay();
            for(ItemStack is: p.getInventory().getContents()) {
                if(is == null)
                    continue;
                if(is.getType() == null)
                    continue;
                if(!is.getType().equals(npc.getPayItem()))
                    continue;

                is.setAmount(is.getAmount() - Math.min(is.getAmount(), itemsToRemove));
                itemsToRemove -= Math.min(is.getAmount(), itemsToRemove);

                if(itemsToRemove == 0)
                    break;
            }

            npc.setPayStock(npc.getPayStock() + npc.getItemsPerPay());

            int receiveLeft = npc.getItemsPerSell();
            for(int i = 0; i < 36; i++) {
                ItemStack is = p.getInventory().getItem(i);

                if(is == null) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getSellItem(), Math.min(npc.getItemsPerSell(), receiveLeft)));
                    npc.setSellStock(npc.getSellStock() - Math.min(npc.getItemsPerSell(), receiveLeft));
                    break;
                }

                if(is.getType() == null || is.getType().equals(Material.AIR)) {
                    p.getInventory().setItem(i, invAPI.createIS(npc.getSellItem(), Math.min(npc.getItemsPerSell(), receiveLeft)));
                    npc.setSellStock(npc.getSellStock() - Math.min(npc.getItemsPerSell(), receiveLeft));
                    break;
                }

                if(!is.getType().equals(npc.getSellItem()))
                    continue;

                int free = npc.getSellItem().getMaxStackSize() - is.getAmount();
                int toAdd = Math.min(free, receiveLeft);
                receiveLeft -= toAdd;
                is.setAmount(is.getAmount() + toAdd);
                npc.setSellStock(npc.getSellStock() - toAdd);
                p.getInventory().setItem(i, is);

                if(receiveLeft == 0)
                    break;
            }
        }, Subplugin.NPCShops);
    }

    private static void registerMobInvClicks() {
        InventoryManager im = GlobalManager.getInventoryManager();
        im.addItem(ItemTitles.closeItem, InvTitles.npcs_Mob, e -> {
            e.getWhoClicked().closeInventory();
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.backItem, InvTitles.npcs_Mob, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));
            p.closeInventory();
            p.openInventory(getOwnerInv(npc.getUUID()));
        }, Subplugin.NPCShops);
        im.addItem(ItemTitles.page, InvTitles.npcs_Mob, e -> {
            Player p = (Player) e.getWhoClicked();
            NPCManager manager = NPCShopsMain.getNpcManager();
            p.closeInventory();
            p.openInventory(invAPI.switchInv(manager.getMobInv(p.getUniqueId()), e.getView().getTopInventory(), e.getSlot()));
        }, Subplugin.NPCShops);
        for(NPCType type: NPCType.values()) {
            im.addItem(type.display, InvTitles.npcs_Mob, e -> {
                Player p = (Player) e.getWhoClicked();
                NPCManager manager = NPCShopsMain.getNpcManager();
                NPC npc = manager.findNPCByID(manager.getLastOpenNPC(p.getUniqueId()));

                manager.createNPC(type.type, npc.getBukkitEntity().getLocation(), npc.getOwner(), npc.getSellItem(), npc.getPayItem(), npc.getItemsPerSell(), npc.getItemsPerPay(), npc.getSellStock(), npc.getPayStock(), npc.getCustomName().getString());
                manager.deleteNPC(npc);

                p.closeInventory();
            }, Subplugin.NPCShops);
        }
    }
}
