package de.benji.naturoforumsmp.Msg.Main;

import java.util.HashMap;
import java.util.UUID;

public class MsgColorManager {
    private HashMap<UUID, MsgStyles[]> colors;
    private final HashMap<UUID, UUID> lastMsg;

    public MsgColorManager() {
        colors = new HashMap<>();
        lastMsg = new HashMap<>();
    }

    public void setColor(UUID uuid, MsgInvs.MsgStylePos msgStylePos, boolean isColor, char c) {
        MsgStyles style = MsgStyles.fromString(String.valueOf(c));

        if(!colors.containsKey(uuid))
            colors.put(uuid, new MsgStyles[]{MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None});

        MsgStyles[] color = colors.get(uuid);
        color[(isColor ? 0 : 3) + msgStylePos.id] = style;
        colors.put(uuid, color);
    }

    public String colorMsg(UUID uuid, String msg, boolean isSender, String sender, String target) {
        if(!colors.containsKey(uuid))
            colors.put(uuid, new MsgStyles[]{MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None, MsgStyles.None});

        MsgStyles[] color = colors.get(uuid);
        String fromTo = color[MsgInvs.MsgStylePos.FromTo.id].key + (color[MsgInvs.MsgStylePos.FromTo.id + 3] == MsgStyles.None ? "" : "§" + color[MsgInvs.MsgStylePos.FromTo.id + 3].key);
        String player = color[MsgInvs.MsgStylePos.PlayerName.id].key + (color[MsgInvs.MsgStylePos.PlayerName.id + 3] == MsgStyles.None ? "" : "§" + color[MsgInvs.MsgStylePos.PlayerName.id + 3].key);
        String message = color[MsgInvs.MsgStylePos.Message.id].key + (color[MsgInvs.MsgStylePos.Message.id + 3] == MsgStyles.None ? "" : "§" + color[MsgInvs.MsgStylePos.Message.id + 3].key);

        return "§" + fromTo + (isSender ? "To §" : "From §") + player + (isSender ? target : sender) + "§" + fromTo + ": §" + message + msg;
    }

    public void setLastMsg(UUID player, UUID target) {
        lastMsg.put(player, target);
    }

    public UUID getLastMsg(UUID player) {
        if(lastMsg.containsKey(player))
            return lastMsg.get(player);
        return null;
    }

    public void setColors(HashMap<UUID, MsgStyles[]> colors) {
        this.colors = colors;
    }

    public HashMap<UUID, MsgStyles[]> getColors() {
        return colors;
    }
}
