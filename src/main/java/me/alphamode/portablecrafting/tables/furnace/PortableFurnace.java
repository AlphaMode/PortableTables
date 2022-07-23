package me.alphamode.portablecrafting.tables.furnace;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.mixin.accessor.AbstractFurnaceBlockEntityAccessor;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.BiConsumer;

public class PortableFurnace extends PortableTable<ItemStack> {
    protected final RecipeType<? extends AbstractCookingRecipe> furnaceType;

    public PortableFurnace(BiConsumer<PlayerEntity, ItemStack> openContext, RecipeType<? extends AbstractCookingRecipe> furnaceType, AllTables type) {
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
            Item item = fuel.getItem();
            return FuelRegistry.INSTANCE.get(item);
        }
    }

    public static void markDirty(ServerPlayerEntity serverPlayer, ItemStack furanceStack, DefaultedList<ItemStack> inventory) {
        if (serverPlayer.currentScreenHandler instanceof PortableFurnaceScreenHandler handler && furanceStack.getNbt().getInt("currentSyncId") == handler.syncId) {
            handler.getSlot(0).setStack(inventory.get(0));
            handler.getSlot(1).setStack(inventory.get(1));
            handler.getSlot(2).setStack(inventory.get(2));
        }
    }

    @Override
    public void inventoryTick(ItemStack furanceStack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = furanceStack.getOrCreateNbt();
        DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
        int burnTime = nbt.getShort("BurnTime");
        int cookTime = nbt.getShort("CookTime");
        int cookTimeTotal = nbt.getShort("CookTimeTotal");
        int fuelTime = this.getFuelTime(inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");

        final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
        for(String string : nbtCompound.getKeys()) {
            recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }

        if (isBurning(burnTime)) {
            --burnTime;
        }

        ItemStack fuelStack = inventory.get(1);
        if (isBurning(burnTime) || !fuelStack.isEmpty() && !inventory.get(0).isEmpty()) {
            Recipe<?> recipe = world.getRecipeManager().getFirstMatch(furnaceType, new SimpleInventory(inventory.get(0), inventory.get(1), inventory.get(2)), world).orElse(null);
            int count = 64;
            if (!isBurning(burnTime) && AbstractFurnaceBlockEntityAccessor.callCanAcceptRecipeOutput(recipe, inventory, count)) {
                burnTime = getFuelTime(fuelStack);
                fuelTime = burnTime;
                if (isBurning(burnTime)) {
                    if (!fuelStack.isEmpty()) {
                        Item item = fuelStack.getItem();
                        fuelStack.decrement(1);
                        if (fuelStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                            if (entity instanceof ServerPlayerEntity player)
                                markDirty(player, furanceStack, inventory);
                        }
                    }
                }
            }
            if (isBurning(burnTime) && AbstractFurnaceBlockEntityAccessor.callCanAcceptRecipeOutput(recipe, inventory, count)) {
                ++cookTime;
                if (cookTime == cookTimeTotal) {
                    cookTime = 0;
                    cookTimeTotal = AbstractFurnaceBlockEntityAccessor.callGetCookTime(world, furnaceType, new SimpleInventory(inventory.get(0), inventory.get(1), inventory.get(2)));
                    if (AbstractFurnaceBlockEntityAccessor.callCraftRecipe(recipe, inventory, count)) {
                        if (recipe != null) {
                            Identifier identifier = recipe.getId();
                            recipesUsed.addTo(identifier, 1);
                            if (entity instanceof ServerPlayerEntity player)
                                markDirty(player, furanceStack, inventory);
                        }
                    }
                }
            } else {
                cookTime = 0;
            }
        } else if (!isBurning(burnTime) && cookTime > 0) {
            cookTime = MathHelper.clamp(cookTime - 2, 0, cookTimeTotal);
        }

        nbt.putShort("BurnTime", (short) burnTime);
        nbt.putShort("CookTime", (short) cookTime);
        nbt.putShort("CookTimeTotal", (short) cookTimeTotal);
        Inventories.writeNbt(nbt, inventory);
        NbtCompound nbtCompoundSave = new NbtCompound();
        recipesUsed.forEach((identifier, count) -> nbtCompoundSave.putInt(identifier.toString(), count));
        nbt.put("RecipesUsed", nbtCompoundSave);
        furanceStack.setNbt(nbt);
        int syncID = nbt.getInt("currentSyncId");
        if(entity instanceof ServerPlayerEntity serverPlayer) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(syncID);
            buf.writeShort(burnTime);
            buf.writeShort(fuelTime);
            buf.writeShort(cookTime);
            buf.writeShort(cookTimeTotal);
            ServerPlayNetworking.send(serverPlayer, PortableTables.asResource("sync"), buf);
        }
    }

    public RecipeType<? extends AbstractCookingRecipe> getFurnaceType() {
        return furnaceType;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack furnaceStack) {
        return Optional.of(new PortableFurnaceTooltipData(furnaceStack));
    }

    @Override
    protected ItemStack getContext(ServerWorld world, ServerPlayerEntity player, Hand hand) {
        return player.getStackInHand(hand);
    }
}
