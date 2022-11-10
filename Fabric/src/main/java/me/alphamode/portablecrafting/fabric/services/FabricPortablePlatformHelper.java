package me.alphamode.portablecrafting.fabric.services;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.fabric.mixin.accessor.AbstractFurnaceBlockEntityAccessor;
import me.alphamode.portablecrafting.network.SyncPacket;
import me.alphamode.portablecrafting.services.PortablePlatformHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableBell;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class FabricPortablePlatformHelper implements PortablePlatformHelper {
    @Override
    public ItemGroup createGroup(Identifier identifier) {
        return FabricItemGroupBuilder.build(identifier, () -> Registry.ITEM.get(PortableTables.asResource("portable_crafting_table")).getDefaultStack());
    }

    @Override
    public ScreenHandlerType<PortableFurnaceScreenHandler> createPortableHandler() {
        return new ScreenHandlerType<>(PortableFurnaceScreenHandler::new);
    }

    @Override
    public int getBurnTime(ItemStack stack) {
        Integer fuel = FuelRegistry.INSTANCE.get(stack.getItem());
        return fuel != null ? fuel : 0;
    }

    @Override
    public boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        return AbstractFurnaceBlockEntityAccessor.callCanAcceptRecipeOutput(recipe, slots, count);
    }

    @Override
    public boolean craftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        return AbstractFurnaceBlockEntityAccessor.callCraftRecipe(recipe, slots, count);
    }

    @Override
    public void sendSyncPacket(ServerPlayerEntity serverPlayer, SyncPacket packet) {
        PacketByteBuf buf = PacketByteBufs.create();
        packet.encode(buf);
        ServerPlayNetworking.send(serverPlayer, PortableTables.asResource("sync"), buf);
    }

    @Override
    public PortableFurnace createNewPortableFurnace(BiConsumer<PlayerEntity, ItemStack> openContext, RecipeType<? extends AbstractCookingRecipe> furnaceType, AllTables type) {
        return new PortableFurnace(openContext, furnaceType, type) {
            @Override
            public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
                return false;
            }
        };
    }

    @Override
    public PortableBell createPortableBell(Item.Settings settings) {
        return new PortableBell(settings) {
            @Override
            public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
                return false;
            }
        };
    }
}
