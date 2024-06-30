package me.alphamode.portabletables.mixin.common;

import me.alphamode.portabletables.ext.PacketListenerExt;
import net.minecraft.network.PacketListener;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PacketListener.class)
public class PacketListenerMixin implements PacketListenerExt {
}
