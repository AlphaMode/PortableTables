package me.alphamode.portablecrafting.forge.network;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.network.SyncPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ForgeNetwork {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(PortableTables.asResource("network"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void init() {
        CHANNEL.registerMessage(0, SyncPacket.class, SyncPacket::encode, SyncPacket::new, (syncPacket, contextSupplier) -> {
            contextSupplier.get().enqueueWork(syncPacket::handle);
            contextSupplier.get().setPacketHandled(true);
        }, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(1, OpenPacket.class, OpenPacket::encode, OpenPacket::new, (syncPacket, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                syncPacket.handle(context.getSender());
            });
            context.setPacketHandled(true);
        }, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
