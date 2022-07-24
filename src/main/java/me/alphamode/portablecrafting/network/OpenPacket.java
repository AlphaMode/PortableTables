package me.alphamode.portablecrafting.network;

import me.alphamode.portablecrafting.PortableCraftingHandler;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static me.alphamode.portablecrafting.PortableTables.findFirstTableTypeInInventory;

public class OpenPacket {
    private final AllTables table;

    public OpenPacket(AllTables table) {
        this.table = table;
    }

    public OpenPacket(FriendlyByteBuf buf) {
        this.table = buf.readEnum(AllTables.class);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeEnum(table);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        Player player = context.get().getSender();
        switch (table) {
            case CRAFTING -> PortableCraftingHandler.openTable(player, null);
            case FURNACE -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.FURNACE));
            case SMOKER -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.SMOKER));
            case BLAST -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.BLAST));
        }
        context.get().setPacketHandled(true);
    }
}
