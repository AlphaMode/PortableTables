package me.alphamode.portablecrafting.tables.furnace;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class PortableFurnaceOutputSlot extends Slot {
    private final Player player;
    private final ItemStack pFurnace;
    private int amount;

    public PortableFurnaceOutputSlot(Player player, Container inventory, int index, int x, int y, ItemStack pFurnace) {
        super(inventory, index, x, y);
        this.pFurnace = pFurnace;
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.amount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.amount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.getLevel(), this.player, this.amount);
        if (this.player instanceof ServerPlayer && this.container instanceof AbstractFurnaceBlockEntity) {
            ((AbstractFurnaceBlockEntity)this.container).awardUsedRecipesAndPopExperience((ServerPlayer)this.player);
        }

        this.amount = 0;
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
