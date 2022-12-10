package me.alphamode.portablecrafting.services;

import me.alphamode.portablecrafting.network.SyncPacket;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableBell;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public interface PortablePlatformHelper {
    ScreenHandlerType<PortableFurnaceScreenHandler> createPortableHandler();

    int getBurnTime(ItemStack stack);

    boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count);

    boolean craftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count);

    void sendSyncPacket(ServerPlayerEntity serverPlayer, SyncPacket packet);

    PortableFurnace createNewPortableFurnace(BiConsumer<PlayerEntity, ItemStack> openContext, RecipeType<? extends AbstractCookingRecipe> furnaceType, AllTables type);

    PortableBell createPortableBell(Item.Settings settings);
}
