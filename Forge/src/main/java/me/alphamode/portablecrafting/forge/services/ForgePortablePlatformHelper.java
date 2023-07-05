package me.alphamode.portablecrafting.forge.services;

import me.alphamode.portablecrafting.forge.network.ForgeNetwork;
import me.alphamode.portablecrafting.network.SyncPacket;
import me.alphamode.portablecrafting.services.PortablePlatformHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableBell;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class ForgePortablePlatformHelper implements PortablePlatformHelper {
    @Override
    public ScreenHandlerType<PortableFurnaceScreenHandler> createPortableHandler() {
        return new ScreenHandlerType<>(PortableFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }

    @Override
    public int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    @Override
    public boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (!((ItemStack)slots.get(0)).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.getOutput(registryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = (ItemStack)slots.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsEqual(itemStack2, itemStack)) {
                    return false;
                } else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack itemStack = (ItemStack)slots.get(0);
            ItemStack itemStack2 = recipe.getOutput(registryManager);
            ItemStack itemStack3 = (ItemStack)slots.get(2);
            if (itemStack3.isEmpty()) {
                slots.set(2, itemStack2.copy());
            } else if (itemStack3.isOf(itemStack2.getItem())) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !((ItemStack)slots.get(1)).isEmpty() && ((ItemStack)slots.get(1)).isOf(Items.BUCKET)) {
                slots.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void sendSyncPacket(ServerPlayerEntity serverPlayer, SyncPacket packet) {
        ForgeNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
    }

    @Override
    public PortableFurnace createNewPortableFurnace(BiConsumer<PlayerEntity, ItemStack> openContext, RecipeType<? extends AbstractCookingRecipe> furnaceType, AllTables type) {
        return new PortableFurnace(openContext, furnaceType, type) {
            @Override
            public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
                return false;
            }
        };
    }

    @Override
    public PortableBell createPortableBell(Item.Settings settings) {
        return new PortableBell(settings) {
            @Override
            public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
                return false;
            }
        };
    }

    @Override
    public TagKey<Item> createPortableTag(String path) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("forge", "portable/" + path));
    }
}
