package me.alphamode.portablecrafting.tables.furnace;

import me.alphamode.portablecrafting.mixin.accessor.AbstractFurnaceBlockEntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class StackSlot extends Slot {

    private final ItemStack pFurnace;
    private final PlayerEntity player;

    public StackSlot(PlayerEntity player, Inventory inventory, int index, int x, int y, ItemStack itemStack) {
        super(inventory, index, x, y);
        this.pFurnace = itemStack;
        this.player = player;
    }

    private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        return world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        DefaultedList<ItemStack> itemInv = DefaultedList.ofSize(3, ItemStack.EMPTY);
        NbtCompound nbt = pFurnace.getOrCreateNbt();
        Inventories.readNbt(nbt, itemInv);
        itemInv.set(getIndex(), stack);
        if (!pFurnace.isEmpty())
            nbt.putShort("CookTimeTotal", (short) getCookTime(player.getWorld(), ((PortableFurnace)pFurnace.getItem()).getFurnaceType(), inventory));
        Inventories.writeNbt(nbt, itemInv);
    }
}
