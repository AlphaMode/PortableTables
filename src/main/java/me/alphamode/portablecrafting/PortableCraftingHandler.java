package me.alphamode.portablecrafting;

import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class PortableCraftingHandler extends CraftingMenu {

    public PortableCraftingHandler(int i, Inventory playerInventory, ContainerLevelAccess ContainerLevelAccess) {
        super(i, playerInventory, ContainerLevelAccess);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH);
    }

    public static void openTable(Player player, Void context) {
        player.openMenu(new SimpleMenuProvider((syncId, inventory, Player) -> new PortableCraftingHandler(syncId, inventory, ContainerLevelAccess.create(Player.getLevel(), Player.getOnPos())), Component.translatable("container.crafting")));
        player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    }
}
