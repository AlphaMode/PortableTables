package me.alphamode.portablecrafting.fabric.services;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.services.ClientPlatformHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class FabricClientPlatformHelper implements ClientPlatformHelper {
    @Override
    public void sendOpenPacket(OpenPacket packet) {
        PacketByteBuf buf = PacketByteBufs.create();
        packet.encode(buf);
        ClientPlayNetworking.send(PortableTables.asResource( "open"), buf);
    }
}
