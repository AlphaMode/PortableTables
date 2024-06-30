package me.alphamode.portabletables.mixin.common;

import me.alphamode.portabletables.ext.PlayerExt;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin implements PlayerExt {
}
