package me.alphamode.portablecrafting.tables.handlers;

import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;

public class PortableGrindstoneScreenHandler extends GrindstoneScreenHandler {
    public PortableGrindstoneScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        super(syncId, playerInventory, ScreenHandlerContext.create(player.getWorld(), player.getBlockPos()));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(PortableTags.PORTABLE_GRINDSTONE);
    }

    public static void openTable(PlayerEntity player, Void context) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(PortableGrindstoneScreenHandler::new, new TranslatableText("container.grindstone_title")));
    }
}
