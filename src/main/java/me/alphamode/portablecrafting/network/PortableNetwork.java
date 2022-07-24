package me.alphamode.portablecrafting.network;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PortableNetwork {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(PortableTables.asResource("network"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void init() {
        CHANNEL.registerMessage(0, OpenPacket.class, OpenPacket::encode, OpenPacket::new, OpenPacket::handle);
        CHANNEL.registerMessage(1, SyncPacket.class, SyncPacket::encode, SyncPacket::new, SyncPacket::handle);
    }
}
