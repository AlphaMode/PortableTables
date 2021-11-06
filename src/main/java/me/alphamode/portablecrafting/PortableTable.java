package me.alphamode.portablecrafting;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PortableTable extends Item {
    public PortableTable() {
        super(new FabricItemSettings().group(ItemGroup.MISC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient())
            return TypedActionResult.pass(user.getStackInHand(hand));
        PortableCraftingHandler.openTable(user);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
