package me.alphamode.portabletables.tables;

import me.alphamode.portabletables.PortableTable;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.Entity;
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

    @Override
    protected ItemInstance getContext(Level world, Player player, ItemInstance item) {
        return item;
    }
}
