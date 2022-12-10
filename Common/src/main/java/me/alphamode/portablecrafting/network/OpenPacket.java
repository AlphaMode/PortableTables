package me.alphamode.portablecrafting.network;


import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.handlers.PortableCraftingHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

import static me.alphamode.portablecrafting.PortableTables.findFirstTableTypeInInventory;

public class OpenPacket {
    private final AllTables table;

    public OpenPacket(AllTables table) {
        this.table = table;
    }

    public OpenPacket(PacketByteBuf buf) {
        this.table = buf.readEnumConstant(AllTables.class);
    }

    public void encode(PacketByteBuf buf) {
        buf.writeEnumConstant(table);
    }

    public void handle(PlayerEntity player) {
        switch (table) {
            case CRAFTING -> PortableCraftingHandler.openTable(player, null);
            case FURNACE -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.FURNACE));
            case SMOKER -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.SMOKER));
            case BLAST -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.BLAST));
        }
    }
}