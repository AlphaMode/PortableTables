package me.alphamode.portablecrafting.tables.furnace;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class StackSlot extends Slot {

    private final ItemStack pFurnace;
    private final Player player;

    public StackSlot(Player player, Container inventory, int index, int x, int y, ItemStack itemStack) {
        super(inventory, index, x, y);
        this.pFurnace = itemStack;
        this.player = player;
    }

    private static int getCookTime(Level world, RecipeType<? extends AbstractCookingRecipe> recipeType, Container inventory) {
        return world.getRecipeManager().getRecipeFor(recipeType, inventory, world).map(AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    @Override
    public void set(ItemStack stack) {
        super.set(stack);
        NonNullList<ItemStack> itemInv = NonNullList.withSize(3, ItemStack.EMPTY);
        CompoundTag nbt = pFurnace.getOrCreateTag();
        ContainerHelper.loadAllItems(nbt, itemInv);
        itemInv.set(getSlotIndex(), stack);
        if (!pFurnace.isEmpty())
            nbt.putShort("CookTimeTotal", (short) getCookTime(player.getLevel(), ((PortableFurnace)pFurnace.getItem()).getFurnaceType(), container));
        ContainerHelper.saveAllItems(nbt, itemInv);
    }
}
