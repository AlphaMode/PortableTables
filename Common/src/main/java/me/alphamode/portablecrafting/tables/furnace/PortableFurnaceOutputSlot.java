package me.alphamode.portablecrafting.tables.furnace;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

public class PortableFurnaceOutputSlot extends Slot {
    private final PlayerEntity player;
    private final ItemStack pFurnace;
    private int amount;

    public PortableFurnaceOutputSlot(PlayerEntity player, Inventory inventory, int index, int x, int y, ItemStack pFurnace) {
        super(inventory, index, x, y);
        this.pFurnace = pFurnace;
        this.player = player;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getStack().getCount());
        }

        return super.takeStack(amount);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
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
    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    protected void onCrafted(ItemStack stack) {
        stack.onCraft(this.player.getWorld(), this.player, this.amount);
        if (this.player instanceof ServerPlayerEntity && this.inventory instanceof AbstractFurnaceBlockEntity) {
            ((AbstractFurnaceBlockEntity)this.inventory).dropExperienceForRecipesUsed((ServerPlayerEntity)this.player);
        }

        this.amount = 0;
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        DefaultedList<ItemStack> itemInv = DefaultedList.ofSize(3, ItemStack.EMPTY);
        Inventories.readNbt(pFurnace.getOrCreateNbt(), itemInv);
        itemInv.set(getIndex(), stack);
        Inventories.writeNbt(pFurnace.getNbt(), itemInv);
    }

    @Override
    public ItemStack takeStackRange(int min, int max, PlayerEntity player) {
        return super.takeStackRange(min, max, player);
    }
}
