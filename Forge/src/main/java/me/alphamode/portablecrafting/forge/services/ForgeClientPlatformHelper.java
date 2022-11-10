package me.alphamode.portablecrafting.forge.services;

import me.alphamode.portablecrafting.forge.network.ForgeNetwork;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.services.ClientPlatformHelper;
import net.minecraftforge.network.PacketDistributor;

public class ForgeClientPlatformHelper implements ClientPlatformHelper {
    @Override
    public void sendOpenPacket(OpenPacket packet) {
        ForgeNetwork.CHANNEL.send(PacketDistributor.SERVER.noArg(), packet);
    }
}
