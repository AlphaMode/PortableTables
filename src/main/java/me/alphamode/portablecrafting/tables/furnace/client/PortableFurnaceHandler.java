package me.alphamode.portablecrafting.tables.furnace.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import me.alphamode.portablecrafting.PortableTables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.screen.ArrayPropertyDelegate;

public class PortableFurnaceHandler {
    public static final Int2ObjectArrayMap<ArrayPropertyDelegate> propertySync = new Int2ObjectArrayMap<>();

    public static void init() {
        propertySync.defaultReturnValue(new ArrayPropertyDelegate(4));
        ClientPlayNetworking.registerGlobalReceiver(PortableTables.asResource("sync"), (client, handler, buf, responseSender) -> {
            var propertyDelegate = new ArrayPropertyDelegate(4);
            int id = buf.readInt();
            propertyDelegate.set(0, buf.readShort()); // Burn Time
            propertyDelegate.set(1, buf.readShort()); // Fuel Time
            propertyDelegate.set(2, buf.readShort()); // Cook Time
            propertyDelegate.set(3, buf.readShort()); // Total Cook Time
            propertySync.put(id, propertyDelegate);
        });
    }

    @Environment(EnvType.CLIENT)
    public static ArrayPropertyDelegate getSyncedPropertyDelegate(PortableFurnaceScreen screen) {
        return propertySync.get(screen.getScreenHandler().syncId);
    }
}
