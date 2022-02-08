package me.alphamode.portablecrafting;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.TranslatableText;

public class PortableFurnaceHandler extends FurnaceScreenHandler {
    public PortableFurnaceHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(PortableCrafting.FURNACE);
    }

    public static void openTable(PlayerEntity player) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerEntity) -> new PortableCraftingHandler(syncId, inventory, ScreenHandlerContext.create(playerEntity.world, playerEntity.getBlockPos())), new TranslatableText("container.crafting")));
        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    }
}
