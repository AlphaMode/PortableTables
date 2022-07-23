package me.alphamode.portablecrafting.tables.furnace;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class PortableFurnaceFuelSlot extends Slot {
    private final PortableFurnaceScreenHandler handler;
    private final ItemStack pFurnace;

    public PortableFurnaceFuelSlot(PortableFurnaceScreenHandler handler, Inventory inventory, int index, int x, int y, ItemStack pFurnace) {
        super(inventory, index, x, y);
        this.handler = handler;
        this.pFurnace = pFurnace;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return FuelRegistry.INSTANCE.get(stack.getItem()) != null || isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        DefaultedList<ItemStack> itemInv = DefaultedList.ofSize(3, ItemStack.EMPTY);
        Inventories.readNbt(pFurnace.getOrCreateNbt(), itemInv);
        itemInv.set(getIndex(), stack);
        Inventories.writeNbt(pFurnace.getNbt(), itemInv);
    }
}
