package de.benji.naturoforumsmp.Msg.Main;

import de.benji.naturoforumsmp.API.GlobalManager;
import de.benji.naturoforumsmp.API.InvAPI.InvAPI;
import de.benji.naturoforumsmp.API.InvAPI.InvEnums;
import de.benji.naturoforumsmp.API.InvAPI.InventoryManager;
import de.benji.naturoforumsmp.API.PluginAPI.Subplugin;
import de.benji.naturoforumsmp.API.Strings.Invs.InvTitles;
import de.benji.naturoforumsmp.API.Strings.Invs.ItemTitles;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class MsgInvs {
    private static final InvAPI invAPI = GlobalManager.getInvAPI();

    public static Inventory getColorInv(MsgStylePos stylePos) {
        Inventory i = invAPI.createInv(4, InvTitles.msg_ColorInv + stylePos.name, InvEnums.Full, true, 31);

        i.setItem(10, invAPI.createIS(Material.BLACK_DYE, getStringWithColors(stylePos, MsgStyles.Black)));
        i.setItem(11, invAPI.createIS(Material.GRAY_DYE, getStringWithColors(stylePos, MsgStyles.Gray)));
        i.setItem(12, invAPI.createIS(Material.LIGHT_GRAY_DYE, getStringWithColors(stylePos, MsgStyles.Light_Grey)));
        i.setItem(13, invAPI.createIS(Material.WHITE_DYE, getStringWithColors(stylePos, MsgStyles.White)));
        i.setItem(14, invAPI.createIS(Material.BLUE_DYE, getStringWithColors(stylePos, MsgStyles.Blue)));
        i.setItem(15, invAPI.createIS(Material.MAGENTA_DYE, getStringWithColors(stylePos, MsgStyles.Magenta)));
        i.setItem(16, invAPI.createIS(Material.PINK_DYE, getStringWithColors(stylePos, MsgStyles.Pink)));
        i.setItem(19, invAPI.createIS(Material.RED_DYE, getStringWithColors(stylePos, MsgStyles.Red)));
        i.setItem(20, invAPI.createIS(Material.ORANGE_DYE, getStringWithColors(stylePos, MsgStyles.Orange)));
        i.setItem(21, invAPI.createIS(Material.YELLOW_DYE, getStringWithColors(stylePos, MsgStyles.Yellow)));
        i.setItem(22, invAPI.createIS(Material.LIME_DYE, getStringWithColors(stylePos, MsgStyles.Lime)));
        i.setItem(23, invAPI.createIS(Material.GREEN_DYE, getStringWithColors(stylePos, MsgStyles.Green)));
        i.setItem(24, invAPI.createIS(Material.LIGHT_BLUE_DYE, getStringWithColors(stylePos, MsgStyles.Light_Blue)));
        i.setItem(25, invAPI.createIS(Material.CYAN_DYE, getStringWithColors(stylePos, MsgStyles.Cyan)));

        return i;
    }

    public static Inventory getStyleInv(MsgStylePos stylePos) {
        Inventory i = invAPI.createInv(3, InvTitles.msg_StyleInv + stylePos.name, InvEnums.Full, true, 22);

        i.setItem(10, invAPI.createIS(Material.GRAY_DYE, getStringWithColors(stylePos, MsgStyles.Bold)));
        i.setItem(11, invAPI.createIS(Material.GRAY_DYE, getStringWithColors(stylePos, MsgStyles.Italic)));
        i.setItem(12, invAPI.createIS(Material.GRAY_DYE, getStringWithColors(stylePos, MsgStyles.Bold_Italic)));
        i.setItem(13, invAPI.createIS(Material.BARRIER, "§7No Style"));

        return i;
    }

    private static String getStringWithColors(MsgStylePos msgStylePos, MsgStyles style) {
        switch (msgStylePos) {
            case FromTo: {
                return "§" + style.key + "From §rPlayer: Message / §" + style.key + "To §rPlayer: Message";
            }
            case Message: {
                return "From Player: §" + style.key + "Message §r/ To Player: §" + style.key + "Message";
            }
            case PlayerName: {
                return "From §" + style.key + "Player§r: Message / To §" + style.key + "Player§r: Message";
            }
        }
        return "";
    }

    public static void registerClicks() {
        InventoryManager im = GlobalManager.getInventoryManager();
        im.addItem("From §rPlayer: Message / §", InvTitles.msg_ColorInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.FromTo, true, e.getCurrentItem().getItemMeta().getDisplayName().charAt(1));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().openInventory(getStyleInv(MsgStylePos.FromTo));
        }), Subplugin.Msg);
        im.addItem("Message §r/ To Player: §", InvTitles.msg_ColorInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.Message, true, e.getCurrentItem().getItemMeta().getDisplayName().charAt(14));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().openInventory(getStyleInv(MsgStylePos.Message));
        }), Subplugin.Msg);
        im.addItem("Player§r: Message / To §", InvTitles.msg_ColorInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.PlayerName, true, e.getCurrentItem().getItemMeta().getDisplayName().charAt(6));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().openInventory(getStyleInv(MsgStylePos.PlayerName));
        }), Subplugin.Msg);

        im.addItem("From §rPlayer: Message / §", InvTitles.msg_StyleInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.FromTo, false, e.getCurrentItem().getItemMeta().getDisplayName().charAt(1));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().openInventory(getColorInv(MsgStylePos.Message));
        }), Subplugin.Msg);
        im.addItem("Message §r/ To Player: §", InvTitles.msg_StyleInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.Message, false, e.getCurrentItem().getItemMeta().getDisplayName().charAt(14));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().openInventory(getColorInv(MsgStylePos.PlayerName));
        }), Subplugin.Msg);
        im.addItem("Player§r: Message / To §", InvTitles.msg_StyleInv, (e -> {
            MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.PlayerName, false, e.getCurrentItem().getItemMeta().getDisplayName().charAt(6));
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage(MsgMain.getMsgColorManager().colorMsg(e.getWhoClicked().getUniqueId(), "Message", true, "Player", "Player"));
            e.getWhoClicked().sendMessage(MsgMain.getMsgColorManager().colorMsg(e.getWhoClicked().getUniqueId(), "Message", false, "Player", "Player"));
        }), Subplugin.Msg);
        im.addItem("§7No Style", InvTitles.msg_StyleInv, (e -> {
            String title = e.getView().getTitle();
            if(title.contains(MsgStylePos.FromTo.name)) {
                MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.FromTo, false, MsgStyles.None.key.charAt(0));
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(getColorInv(MsgStylePos.Message));
            } else if(title.contains(MsgStylePos.Message.name)) {
                MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.Message, false, MsgStyles.None.key.charAt(0));
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(getColorInv(MsgStylePos.PlayerName));
            } else if(title.contains(MsgStylePos.PlayerName.name)) {
                MsgMain.getMsgColorManager().setColor(e.getWhoClicked().getUniqueId(), MsgStylePos.PlayerName, false, MsgStyles.None.key.charAt(0));
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(MsgMain.getMsgColorManager().colorMsg(e.getWhoClicked().getUniqueId(), "Message", true, "Player", "Player"));
                e.getWhoClicked().sendMessage(MsgMain.getMsgColorManager().colorMsg(e.getWhoClicked().getUniqueId(), "Message", false, "Player", "Player"));
            }
        }), Subplugin.Msg);
        im.addItem(ItemTitles.closeItem, InvTitles.msg_StyleInv, (e -> e.getWhoClicked().closeInventory()), Subplugin.Msg);
        im.addItem(ItemTitles.closeItem, InvTitles.msg_ColorInv, (e -> e.getWhoClicked().closeInventory()), Subplugin.Msg);
    }

    public enum MsgStylePos {
        FromTo("from and to", 0),
        PlayerName("the player's name", 1),
        Message("the message", 2);

        public final String name;
        public final int id;

        MsgStylePos(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }
}
