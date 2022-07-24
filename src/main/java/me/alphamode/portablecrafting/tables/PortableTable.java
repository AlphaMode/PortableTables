package me.alphamode.portablecrafting.tables;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class PortableTable<C> extends Item {

    protected final BiConsumer<Player, C> open;
    private final AllTables type;

    public PortableTable(BiConsumer<Player, C> player, AllTables type) {
        super(new Item.Properties().tab(PortableTables.TABLE_GROUP));
        this.open = player;
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(world.isClientSide())
            return InteractionResultHolder.pass(user.getItemInHand(hand));
        open.accept(user, getContext((ServerLevel) world, (ServerPlayer) user, hand));
        return InteractionResultHolder.success(user.getItemInHand(hand));
    }

    @Nullable
    protected C getContext(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        return null;
    }

    public AllTables getType() {
        return type;
    }
}
