package me.alphamode.portablecrafting.tables;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class PortableAnvil extends PortableTable<ItemStack> {
    public PortableAnvil(BiConsumer<PlayerEntity, ItemStack> player, AllTables type, Settings settings) {
        super(player, type, settings);
    }

    @Override
    protected ItemStack getContext(ServerWorld world, ServerPlayerEntity player, Hand hand) {
        return player.getStackInHand(hand);
    }

    @Nullable
    public ItemStack getNextState(ItemStack prev) {
        if (prev.isOf(PortableTables.PORTABLE_ANVIL.get())) {
            return PortableTables.PORTABLE_CHIPPED_ANVIL.get().getDefaultStack();
        } else {
            return prev.isOf(PortableTables.PORTABLE_CHIPPED_ANVIL.get()) ? PortableTables.PORTABLE_DAMAGED_ANVIL.get().getDefaultStack() : null;
        }
    }
}
