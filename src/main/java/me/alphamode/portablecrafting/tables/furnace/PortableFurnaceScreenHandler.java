package me.alphamode.portablecrafting.tables.furnace;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.OptionalInt;

public class PortableFurnaceScreenHandler extends RecipeBookMenu<Container> {

    protected final ItemStack pFurnace;
    public Container inventory;
    private final ContainerData propertyDelegate = new SimpleContainerData(4);
    protected final Level world;

    public PortableFurnaceScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3), ItemStack.EMPTY);
    }

    public PortableFurnaceScreenHandler(int syncId, Inventory playerInventory, Container inventory, ItemStack pFurnace) {
        super(PortableTables.PORTABLE_FURNACE_HANDLER.get(), syncId);
        checkContainerSize(inventory, 3);
        checkContainerDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.world = playerInventory.player.getLevel();
        this.addSlot(new StackSlot(playerInventory.player, inventory, 0, 56, 17, pFurnace));
        this.addSlot(new PortableFurnaceFuelSlot(this, inventory, 1, 56, 53, pFurnace));
        this.addSlot(new PortableFurnaceOutputSlot(playerInventory.player, inventory, 2, 116, 35, pFurnace));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(propertyDelegate);
        this.pFurnace = pFurnace;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        PortableFurnaceHandler.propertySync.remove(containerId);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().contains(PortableTags.PORTABLE_FURNACE);
    }

    private static int getCookTime(Level world, RecipeType<? extends AbstractCookingRecipe> recipeType, Container inventory) {
        return world.getRecipeManager().getRecipeFor(recipeType, inventory, world).map(AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    public static void openTable(Player player, ItemStack stack) {
        if(stack.getTag() == null)
            return;
        NonNullList<ItemStack> itemInv = NonNullList.withSize(3, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(stack.getTag(), itemInv);
        Container furnaceInv = new SimpleContainer(itemInv.get(0), itemInv.get(1), itemInv.get(2));
        OptionalInt syncID = player.openMenu(new SimpleMenuProvider((syncId, inventory, Player) -> new PortableFurnaceScreenHandler(syncId, player.getInventory(), furnaceInv, stack), Component.translatable("container.furnace")));
        player.awardStat(Stats.INTERACT_WITH_FURNACE);
        stack.getTag().putInt("currentSyncId", syncID.orElseThrow());
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents finder) {
        if (this.inventory instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)this.inventory).fillStackedContents(finder);
        }
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe<? super Container> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getResultSlotIndex() {
        return 2;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.FURNACE;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index != 1 && index != 0) {
                if (this.isSmeltable(itemStack2)) {
                    CompoundTag nbt = pFurnace.getTag();
                    nbt.putShort("CookTimeTotal", (short) getCookTime(player.getLevel(), ((PortableFurnace)pFurnace.getItem()).getFurnaceType(), this.inventory));
                    nbt.putShort("CookTime", (short) 0);
                    if (!this.moveItemStackTo(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceBlockEntity.isFuel(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(itemStack2, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemStack2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    protected boolean isSmeltable(ItemStack itemStack) {
        if (!pFurnace.isEmpty())
            return this.world.getRecipeManager().getRecipeFor(((PortableFurnace)pFurnace.getItem()).getFurnaceType(), new SimpleContainer(itemStack), this.world).isPresent();
        return false;
    }

    @Override
    public boolean shouldMoveToInventory(int index) {
        return index != 1;
    }
}
