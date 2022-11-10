package me.alphamode.portablecrafting.tables.furnace.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.ArrayPropertyDelegate;

public class PortableFurnaceHandler {
    public static final Int2ObjectArrayMap<ArrayPropertyDelegate> propertySync = new Int2ObjectArrayMap<>();

    public static void init() {
        propertySync.defaultReturnValue(new ArrayPropertyDelegate(4));
    }

    @Environment(EnvType.CLIENT)
    public static ArrayPropertyDelegate getSyncedPropertyDelegate(PortableFurnaceScreen screen) {
        return propertySync.get(screen.getScreenHandler().syncId);
    }
}
