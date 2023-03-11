package me.alphamode.portablecrafting.tables;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class PortableTable<C> extends Item {

    protected final BiConsumer<PlayerEntity, C> open;
    private final AllTables type;

    public PortableTable(BiConsumer<PlayerEntity, C> player, AllTables type, Item.Settings settings) {
        super(settings.group(PortableTables.TABLE_GROUP).maxCount(1));
        this.open = player;
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient())
            return TypedActionResult.pass(user.getStackInHand(hand));
        open.accept(user, getContext((ServerWorld) world, (ServerPlayerEntity) user, hand));
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Nullable
    protected C getContext(ServerWorld world, ServerPlayerEntity player, Hand hand) {
        return null;
    }

    public AllTables getType() {
        return type;
    }
}
