package me.alphamode.portablecrafting.mixin.accessor;

import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BellBlockEntity.class)
public interface BellBlockEntityAccessor {
    @Invoker
    static boolean callRaidersHearBell(BlockPos pos, List<LivingEntity> hearingEntities) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callApplyGlowToRaiders(World world, BlockPos pos, List<LivingEntity> hearingEntities) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callApplyParticlesToRaiders(World world, BlockPos pos, List<LivingEntity> hearingEntities) {
        throw new UnsupportedOperationException();
    }
}
