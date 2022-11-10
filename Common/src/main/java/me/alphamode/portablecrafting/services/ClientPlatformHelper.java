package me.alphamode.portablecrafting.services;

import me.alphamode.portablecrafting.network.OpenPacket;

public interface ClientPlatformHelper {
    void sendOpenPacket(OpenPacket packet);
}
