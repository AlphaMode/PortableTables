package me.alphamode.portablecrafting;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;

public class PortableFurnace extends Item {

    public PortableFurnace(Settings settings) {
        super(settings);

    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        ServerTickEvents.END_SERVER_TICK.register(this::tickInventory);
        return stack;
    }

    public void tickInventory(MinecraftServer server) {

    }

    public void createInventory(NbtCompound data) {

    }
}
