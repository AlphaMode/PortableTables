package me.alphamode.portablecrafting.tables.furnace.client;

import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class PortableFurnaceTooltipData implements TooltipComponent {
    protected final NonNullList<ItemStack> inventory;
    private final int burnProgress, cookProgress;
    protected final ItemStack furnaceStack;
    private final boolean isBurning;

    public PortableFurnaceTooltipData(ItemStack furnaceStack) {
        CompoundTag nbt = furnaceStack.getOrCreateTag();
        this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
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

    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }
}
