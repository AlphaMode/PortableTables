package me.alphamode.portablecrafting.tables.furnace.client;

import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import net.minecraft.client.item.TooltipData;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class PortableFurnaceTooltipData implements TooltipData {
    protected final DefaultedList<ItemStack> inventory;
    private final int burnProgress, cookProgress;
    protected final ItemStack furnaceStack;
    private final boolean isBurning;

    public PortableFurnaceTooltipData(ItemStack furnaceStack) {
        NbtCompound nbt = furnaceStack.getOrCreateNbt();
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        short burnTime = nbt.getShort("BurnTime");
        this.isBurning = burnTime > 0;
        int i = PortableFurnace.getFuelTime(inventory.get(1));
        if (i == 0) {
            i = 200;
        }
        this.burnProgress = burnTime * 13 / i;
        short cookTime = nbt.getShort("CookTime");
        short totalCookTime = nbt.getShort("CookTimeTotal");
        this.cookProgress = totalCookTime != 0 && cookTime != 0 ? cookTime * 24 / totalCookTime : 0;
        this.furnaceStack = furnaceStack;
    }

    public boolean isBurning() {
        return this.isBurning;
    }

    public int getFuelProgress() {
        return this.burnProgress;
    }

    public int getCookProgress() {
        return this.cookProgress;
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }
}
