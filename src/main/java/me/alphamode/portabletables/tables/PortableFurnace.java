package me.alphamode.portabletables.tables;

import me.alphamode.portabletables.PortableTable;
import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.function.BiConsumer;

public class PortableFurnace extends PortableTable<ItemInstance> {
    public static final FurnaceTileEntity FAKE_FURNACE = new FurnaceTileEntity();
    public PortableFurnace(Identifier id, BiConsumer<Player, ItemInstance> player, AllTables type) {
        super(id, player, type);
    }

    @Override
    public void inventoryTick(ItemInstance item, Level level, Entity entity, int slotId, boolean isSelected) {
        FurnaceTileEntity furnace = ((ItemInstanceExt) (Object) item).pt$getFurnaceOrCreate();
        furnace.level = level;
        furnace.tick();
    }

    public static ItemInstance findFirstFurnace(Player player) {
        Inventory inv = player.inventory;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemInstance item = inv.getItem(i);
            if (item != null && item.isOf(PortableTables.PORTABLE_FURNACE)) {
                return item;
            }
        }
        return null;
    }

    @Override
    protected ItemInstance getContext(Level world, Player player, ItemInstance item) {
        return item;
    }
}
