package me.alphamode.portablecrafting.tables.furnace;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alphamode.portablecrafting.network.PortableNetwork;
import me.alphamode.portablecrafting.network.SyncPacket;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiConsumer;

public class PortableFurnace extends PortableTable<ItemStack> {
    protected final RecipeType<? extends AbstractCookingRecipe> furnaceType;

    public PortableFurnace(BiConsumer<Player, ItemStack> openContext, RecipeType<? extends AbstractCookingRecipe> furnaceType, AllTables type) {
        super(openContext, type);
        this.furnaceType = furnaceType;
    }

    private boolean isBurning(int burnTime) {
        return burnTime > 0;
    }

    public static int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel, null);
        }
    }

    public static void markDirty(ServerPlayer serverPlayer, ItemStack furanceStack, NonNullList<ItemStack> inventory) {
        if (serverPlayer.containerMenu instanceof PortableFurnaceScreenHandler handler && furanceStack.getTag().getInt("currentSyncId") == handler.containerId) {
            handler.getSlot(0).set(inventory.get(0));
            handler.getSlot(1).set(inventory.get(1));
            handler.getSlot(2).set(inventory.get(2));
        }
    }

    private static int getCookTime(Level world, RecipeType<? extends AbstractCookingRecipe> recipeType, Container inventory) {
        return world.getRecipeManager().getRecipeFor(recipeType, inventory, world).map(AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    private boolean canBurn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> slots, int count) {
        if (!slots.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.getResultItem();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = slots.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.sameItem(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= count && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private boolean burn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i) {
        if (recipe != null && canBurn(recipe, nonNullList, i)) {
            ItemStack itemStack = nonNullList.get(0);
            ItemStack itemStack2 = recipe.getResultItem();
            ItemStack itemStack3 = nonNullList.get(2);
            if (itemStack3.isEmpty()) {
                nonNullList.set(2, itemStack2.copy());
            } else if (itemStack3.is(itemStack2.getItem())) {
                itemStack3.grow(1);
            }

            if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !nonNullList.get(1).isEmpty() && nonNullList.get(1).is(Items.BUCKET)) {
                nonNullList.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack furanceStack, Level world, Entity entity, int slot, boolean selected) {
        CompoundTag nbt = furanceStack.getOrCreateTag();
        NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, inventory);
        int burnTime = nbt.getShort("BurnTime");
        int cookTime = nbt.getShort("CookTime");
        int cookTimeTotal = nbt.getShort("CookTimeTotal");
        int fuelTime = getFuelTime(inventory.get(1));
        CompoundTag nbtCompound = nbt.getCompound("RecipesUsed");

        final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
        for(String string : nbtCompound.getAllKeys()) {
            recipesUsed.put(new ResourceLocation(string), nbtCompound.getInt(string));
        }

        if (isBurning(burnTime)) {
            --burnTime;
        }

        ItemStack fuelStack = inventory.get(1);
        if (isBurning(burnTime) || !fuelStack.isEmpty() && !inventory.get(0).isEmpty()) {
            Recipe<?> recipe = world.getRecipeManager().getRecipeFor(furnaceType, new SimpleContainer(inventory.get(0), inventory.get(1), inventory.get(2)), world).orElse(null);
            int count = 64;
            if (!isBurning(burnTime) && canBurn(recipe, inventory, count)) {
                burnTime = getFuelTime(fuelStack);
                fuelTime = burnTime;
                if (isBurning(burnTime)) {
                    if (!fuelStack.isEmpty()) {
                        fuelStack.shrink(1);
                        if (fuelStack.isEmpty()) {
                            inventory.set(1, fuelStack.hasCraftingRemainingItem() ? fuelStack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            if (entity instanceof ServerPlayer player)
                                markDirty(player, furanceStack, inventory);
                        }
                    }
                }
            }
            if (isBurning(burnTime) && canBurn(recipe, inventory, count)) {
                ++cookTime;
                if (cookTime == cookTimeTotal) {
                    cookTime = 0;
                    cookTimeTotal = getCookTime(world, furnaceType, new SimpleContainer(inventory.get(0), inventory.get(1), inventory.get(2)));
                    if (burn(recipe, inventory, count)) {
                        if (recipe != null) {
                            ResourceLocation identifier = recipe.getId();
                            recipesUsed.addTo(identifier, 1);
                            if (entity instanceof ServerPlayer player)
                                markDirty(player, furanceStack, inventory);
                        }
                    }
                }
            } else {
                cookTime = 0;
            }
        } else if (!isBurning(burnTime) && cookTime > 0) {
            cookTime = Mth.clamp(cookTime - 2, 0, cookTimeTotal);
        }

        nbt.putShort("BurnTime", (short) burnTime);
        nbt.putShort("CookTime", (short) cookTime);
        nbt.putShort("CookTimeTotal", (short) cookTimeTotal);
        ContainerHelper.saveAllItems(nbt, inventory);
        CompoundTag nbtCompoundSave = new CompoundTag();
        recipesUsed.forEach((identifier, count) -> nbtCompoundSave.putInt(identifier.toString(), count));
        nbt.put("RecipesUsed", nbtCompoundSave);
        furanceStack.setTag(nbt);
        int syncID = nbt.getInt("currentSyncId");
        if(entity instanceof ServerPlayer serverPlayer) {
            PortableNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                    new SyncPacket(syncID, (short) burnTime, (short) fuelTime, (short) cookTime, (short) cookTimeTotal)
            );
        }
    }

    public RecipeType<? extends AbstractCookingRecipe> getFurnaceType() {
        return furnaceType;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack furnaceStack) {
        return Optional.of(new PortableFurnaceTooltipData(furnaceStack));
    }

    @Override
    protected ItemStack getContext(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        return player.getItemInHand(hand);
    }
}
