package me.alphamode.portabletables.network;

import me.alphamode.portabletables.network.packets.OpenTablePacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;

public class PortablePackets {
    @EventListener
    public void registerPackets(PacketRegisterEvent event) {
//        event.register(OpenTablePacket.ID, true, false, OpenTablePacket.class);
    }
}
