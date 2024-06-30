package me.alphamode.portabletables.tables.menus;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;

public class PortableFurnaceMenu extends FurnaceMenu {
    public PortableFurnaceMenu(Inventory inventory, FurnaceTileEntity furnace) {
        super(inventory, furnace);
    }

    @Override
    public boolean stillValid(Player player) {
        Inventory inventory = player.inventory;
        for(int slot = 0; slot < inventory.items.length; ++slot) {
            if (inventory.items[slot] != null && inventory.items[slot].id == PortableTables.PORTABLE_FURNACE.id) {
                return true;
            }
        }
        return false;
    }

    public static void openTable(Player player, ItemInstance item) {
        player.openPortableTable(AllTables.FURNACE, ((ItemInstanceExt) (Object) item).pt$getFurnaceOrCreate());
//        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    }
}
