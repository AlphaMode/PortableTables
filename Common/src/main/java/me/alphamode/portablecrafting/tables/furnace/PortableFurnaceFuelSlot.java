package me.alphamode.portablecrafting.tables.furnace;

import me.alphamode.portablecrafting.services.ServiceHelper;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
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
        return ServiceHelper.PLATFORM_HELPER.getBurnTime(stack) > 0 || isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        DefaultedList<ItemStack> itemInv = DefaultedList.ofSize(3, ItemStack.EMPTY);
        NbtCompound nbt = pFurnace.getOrCreateNbt();
        Inventories.readNbt(nbt, itemInv);
        itemInv.set(getIndex(), getStack());
        Inventories.writeNbt(nbt, itemInv);
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
