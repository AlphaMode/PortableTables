package me.alphamode.portablecrafting.tables.handlers;

import com.google.common.collect.ImmutableList;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.tables.PortableAnvil;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class PortableAnvilHandler extends AnvilScreenHandler {
    protected ItemStack anvilStack;

    public PortableAnvilHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player, ItemStack context) {
        super(syncId, playerInventory, ScreenHandlerContext.create(player.getWorld(), player.getBlockPos()));
        assert !(context.getItem() instanceof PortableAnvil);
        this.anvilStack = context;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(PortableTags.PORTABLE_ANVIL) && anvilStack != null;
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        super.onTakeOutput(player, stack);
        if (!player.getAbilities().creativeMode && player.getRandom().nextFloat() < 0.12F) {
            PortableAnvil anvil = (PortableAnvil) anvilStack.getItem();
            ItemStack newAnvil = anvil.getNextState(anvilStack);
            if (newAnvil == null) {
                player.getInventory().removeOne(anvilStack);
                anvilStack = null;
                this.player.world.playSoundFromEntity(player, player, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0F, player.getRandom().nextFloat() * 0.1F + 0.9F);
            } else {
                for (DefaultedList<ItemStack> itemStacks: ImmutableList.of(player.getInventory().main, player.getInventory().armor, player.getInventory().offHand)) {
                    for(int i = 0; i < itemStacks.size(); ++i) {
                        if (itemStacks.get(i) == anvilStack) {
                            itemStacks.set(i, newAnvil);
                            anvilStack = newAnvil;
                            this.player.world.playSoundFromEntity(player, player, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, player.getRandom().nextFloat() * 0.1F + 0.9F);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void openTable(PlayerEntity player, ItemStack context) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inv, player1) -> new PortableAnvilHandler(syncId, inv, player1, context), Text.translatable("container.repair")));
    }
}
