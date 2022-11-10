package me.alphamode.portablecrafting.tables.furnace;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.OptionalInt;

public class PortableFurnaceScreenHandler extends AbstractRecipeScreenHandler<Inventory> {

    protected final ItemStack pFurnace;
    public Inventory inventory;
    private final PropertyDelegate propertyDelegate = new ArrayPropertyDelegate(4);
    protected final World world;

    public PortableFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), ItemStack.EMPTY);
    }

    public PortableFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ItemStack pFurnace) {
        super(PortableTables.PORTABLE_FURNACE_HANDLER.get(), syncId);
        checkSize(inventory, 3);
        checkDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
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

        this.addProperties(propertyDelegate);
        this.pFurnace = pFurnace;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);

        PortableFurnaceHandler.propertySync.remove(syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(PortableTags.PORTABLE_FURNACE);
    }

    private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        return world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static void openTable(PlayerEntity player, ItemStack stack) {
        if(stack.getNbt() == null)
            return;
        DefaultedList<ItemStack> itemInv = DefaultedList.ofSize(3, ItemStack.EMPTY);
        Inventories.readNbt(stack.getNbt(), itemInv);
        Inventory furnaceInv = new SimpleInventory(itemInv.get(0), itemInv.get(1), itemInv.get(2));
        OptionalInt syncID = player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerEntity) -> new PortableFurnaceScreenHandler(syncId, player.getInventory(), furnaceInv, stack), Text.translatable("container.furnace")));
        player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        stack.getNbt().putInt("currentSyncId", syncID.orElseThrow());
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(0).setStack(ItemStack.EMPTY);
        this.getSlot(2).setStack(ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 2;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 3;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.FURNACE;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index != 1 && index != 0) {
                if (this.isSmeltable(itemStack2)) {
                    NbtCompound nbt = pFurnace.getNbt();
                    nbt.putShort("CookTimeTotal", (short) getCookTime(player.getWorld(), ((PortableFurnace)pFurnace.getItem()).getFurnaceType(), this.inventory));
                    nbt.putShort("CookTime", (short) 0);
                    if (!this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceBlockEntity.canUseAsFuel(itemStack2)) {
                    if (!this.insertItem(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.insertItem(itemStack2, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.insertItem(itemStack2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    protected boolean isSmeltable(ItemStack itemStack) {
        if (!pFurnace.isEmpty())
            return this.world.getRecipeManager().getFirstMatch(((PortableFurnace)pFurnace.getItem()).getFurnaceType(), new SimpleInventory(itemStack), this.world).isPresent();
        return false;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 1;
    }
}
