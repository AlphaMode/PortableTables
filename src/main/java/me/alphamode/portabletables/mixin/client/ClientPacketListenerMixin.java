package me.alphamode.portabletables.mixin.client;

import me.alphamode.portabletables.ext.PacketListenerExt;
import me.alphamode.portabletables.network.packets.OpenTablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin implements PacketListenerExt {
    @Shadow private Minecraft minecraft;

    @Override
    public void handlePortableTable(OpenTablePacket packet) {
        this.minecraft.player.openPortableTable(packet.type, null);
        this.minecraft.player.containerMenu.containerId = packet.containerId;
    }
}
