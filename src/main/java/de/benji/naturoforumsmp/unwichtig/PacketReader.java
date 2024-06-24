package de.benji.naturoforumsmp.unwichtig;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
/*
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.List;
public class PacketReader {
    Player player;
    Channel channel;
    long delay = 0;

    public static HashMap<Player, PacketReader> readers = new HashMap<Player, PacketReader>();

    public static PacketReader getReader(Player player) {
        if (readers.containsKey(player)) {
            PacketReader reader = readers.get(player);
            return reader;
        } else {
            PacketReader reader = new PacketReader(player);
            reader.inject();
            return reader;
        }
    }

    public PacketReader(Player player) {
        this.player = player;
    }

    //On Join
    public void inject() {
        final CraftPlayer player = (CraftPlayer) this.player;
        channel = player.getHandle().connection.connection.channel;
        channel.pipeline().addAfter("decoder", "PacketInjector",
                new MessageToMessageDecoder<Packet<?>>() {
                    @Override
                    protected void decode(ChannelHandlerContext arg0,
                                          Packet<?> packet, List<Object> arg2)
                            throws Exception {
                        arg2.add(packet);
                        readPackets(packet, player);
                    }
                });

        readers.put(this.player, this);
    }

    //On Quit
    public void uninject() {
        if (channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
        if (readers.containsKey(this.player)) {
            readers.remove(player);
        }
    }

    private void readPackets(Packet<?> packet, Player player) {
        if(!packet.toString().contains("Position"))
            return;
        try {
            ServerboundMovePlayerPacket playerPositionPacket = (ServerboundMovePlayerPacket) packet;
            System.out.println(playerPositionPacket.y);
            System.out.println((System.nanoTime() - delay) / 1000000.0);
            delay = System.nanoTime();
        } catch (Exception e1) {e1.printStackTrace();}
    }
//getServer().getMessenger().unregisterOutgoingPluginChannel(this, "minecraft:brand");
    /*getServer().getMessenger().registerOutgoingPluginChannel(this, "minecraft:brand");
        for(Player p: Bukkit.getOnlinePlayers()) {
            ServerPlayer sp = ((CraftPlayer) p).getHandle();
            ClientboundOpenScreenPacket packet = new ClientboundOpenScreenPacket(sp.containerMenu.containerId, MenuType.ANVIL, Component.nullToEmpty("tes3434153453453453453t"));
            sp.connection.send(packet);
            sp.containerMenu.sendAllDataToRemote();

        }*//*
}*/