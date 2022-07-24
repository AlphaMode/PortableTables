package me.alphamode.portablecrafting.tables.furnace;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

public class PortableFurnaceFuelSlot extends Slot {
    private final PortableFurnaceScreenHandler handler;
    private final ItemStack pFurnace;

    public PortableFurnaceFuelSlot(PortableFurnaceScreenHandler handler, Container inventory, int index, int x, int y, ItemStack pFurnace) {
        super(inventory, index, x, y);
        this.handler = handler;
        this.pFurnace = pFurnace;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null) > 0 || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.is(Items.BUCKET);
    }

    @Override
    public void set(ItemStack stack) {
        super.set(stack);
        NonNullList<ItemStack> itemInv = NonNullList.withSize(3, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pFurnace.getOrCreateTag(), itemInv);
        itemInv.set(getSlotIndex(), stack);
        ContainerHelper.saveAllItems(pFurnace.getTag(), itemInv);
    }
}
