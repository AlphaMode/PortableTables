package me.alphamode.portabletables.tables.menus;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;

public class PortableCraftingMenu extends CraftingMenu {
    public PortableCraftingMenu(Inventory inventory, Level level) {
        super(inventory, level, 0, 0, 0);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.inventory.contains(new ItemInstance(PortableTables.PORTABLE_CRAFTING)) || PortableTables.CONFIG.craftingWidgetAlwaysEnabled;
    }

    public static void openTable(Player player, Void v) {
        player.openPortableTable(AllTables.CRAFTING, null);
//        player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    }
}
