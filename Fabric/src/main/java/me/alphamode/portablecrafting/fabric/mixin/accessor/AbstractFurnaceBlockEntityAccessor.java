package me.alphamode.portablecrafting.fabric.mixin.accessor;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Invoker
    static boolean callCanAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        return false;
    }

    @Invoker
    static boolean callCraftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        return false;
    }
}
