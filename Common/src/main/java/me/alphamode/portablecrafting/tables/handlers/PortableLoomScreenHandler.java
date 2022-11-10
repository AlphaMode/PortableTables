package me.alphamode.portablecrafting.tables.handlers;

import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;

public class PortableLoomScreenHandler extends LoomScreenHandler {
    public PortableLoomScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        super(syncId, playerInventory, ScreenHandlerContext.create(player.getWorld(), player.getBlockPos()));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(PortableTags.PORTABLE_LOOM);
    }

    public static void openTable(PlayerEntity player, Void context) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(PortableLoomScreenHandler::new, new TranslatableText("container.loom")));
    }
}
