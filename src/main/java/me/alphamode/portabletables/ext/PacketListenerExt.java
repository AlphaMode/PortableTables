package me.alphamode.portabletables.ext;

import me.alphamode.portabletables.network.packets.OpenTablePacket;
import net.minecraft.network.PacketListener;

public interface PacketListenerExt {
    default void handlePortableTable(OpenTablePacket packet) {
        ((PacketListener) this).onUnhandledPacket(packet);
    }
}
