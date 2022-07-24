package me.alphamode.portablecrafting.network;

import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPacket {
    private final int syncId;
    private final short burnTime, fuelTime, cookTime, totalCookTime;

    public SyncPacket(int syncId, short burnTime, short fuelTime, short cookTime, short totalCookTime) {
        this.syncId = syncId;
        this.burnTime = burnTime;
        this.fuelTime = fuelTime;
        this.cookTime = cookTime;
        this.totalCookTime = totalCookTime;
    }

    public SyncPacket(FriendlyByteBuf buf) {
        this.syncId = buf.readInt();
        this.burnTime = buf.readShort(); // Burn Time
        this.fuelTime = buf.readShort(); // Fuel Time
        this.cookTime = buf.readShort(); // Cook Time
        this.totalCookTime = buf.readShort(); // Total Cook Time
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(syncId);
        buf.writeShort(burnTime);
        buf.writeShort(fuelTime);
        buf.writeShort(cookTime);
        buf.writeShort(totalCookTime);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        var propertyDelegate = new SimpleContainerData(4);
        propertyDelegate.set(0, burnTime); // Burn Time
        propertyDelegate.set(1, fuelTime); // Fuel Time
        propertyDelegate.set(2, cookTime); // Cook Time
        propertyDelegate.set(3, totalCookTime); // Total Cook Time
        PortableFurnaceHandler.propertySync.put(syncId, propertyDelegate);
        context.get().setPacketHandled(true);
    }
}
