package me.alphamode.portablecrafting.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SimpleContainer.class)
public interface SimpleInventoryAccessor {
    @Accessor
    NonNullList<ItemStack> getItems();
}
