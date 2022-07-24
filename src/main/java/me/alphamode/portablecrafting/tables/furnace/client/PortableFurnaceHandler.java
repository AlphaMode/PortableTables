package me.alphamode.portablecrafting.tables.furnace.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PortableFurnaceHandler {
    public static final Int2ObjectArrayMap<SimpleContainerData> propertySync = new Int2ObjectArrayMap<>();

    public static void init() {
        propertySync.defaultReturnValue(new SimpleContainerData(4));
    }

    @OnlyIn(Dist.CLIENT)
    public static SimpleContainerData getSyncedPropertyDelegate(PortableFurnaceScreen screen) {
        return propertySync.get(screen.getMenu().containerId);
    }
}
