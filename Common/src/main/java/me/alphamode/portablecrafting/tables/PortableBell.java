package me.alphamode.portablecrafting.tables;

import me.alphamode.portablecrafting.mixin.accessor.BellBlockEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;

public class PortableBell extends Item {
    public PortableBell(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack bell = user.getStackInHand(hand);
        NbtCompound nbt = bell.getOrCreateNbt();


            if (nbt.getBoolean("ringing")) {
                nbt.putInt("ringTicks", 0);
            } else {
                nbt.putBoolean("ringing", true);
            }
            notifyMemoriesOfBell(world, user, hand);
            nbt.putInt("resonateTime", 0);
            nbt.putInt("ringTicks", 0);
            nbt.putBoolean("ringing", true);
            bell.setNbt(nbt);

            world.playSoundFromEntity(user, user, SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 2.0F, 1.0F);
            world.emitGameEvent(user, GameEvent.ENTITY_INTERACT, user.getBlockPos());
            user.incrementStat(Stats.BELL_RING);
            return TypedActionResult.success(bell);

    }

    private void notifyMemoriesOfBell(World world, PlayerEntity user, Hand hand) {
        ItemStack bell = user.getStackInHand(hand);
        NbtCompound nbt = bell.getOrCreateNbt();
        BlockPos blockPos = user.getBlockPos();
        world.getTime();
        nbt.getInt("lastRingTime");
        nbt.putLong("lastRingTime", world.getTime());
        Box box = (new Box(blockPos)).expand(48.0);
        List<LivingEntity> hearingEntities = world.getNonSpectatingEntities(LivingEntity.class, box);

        if (!world.isClient) {
            Iterator var4 = hearingEntities.iterator();

            while(var4.hasNext()) {
                LivingEntity livingEntity = (LivingEntity)var4.next();
                if (livingEntity.isAlive() && !livingEntity.isRemoved() && blockPos.isWithinDistance(livingEntity.getPos(), 32.0)) {
                    livingEntity.getBrain().remember(MemoryModuleType.HEARD_BELL_TIME, world.getTime());
                }
            }
        }
        bell.setNbt(nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Box box = (new Box(entity.getBlockPos())).expand(48.0);
        List<LivingEntity> hearingEntities = world.getNonSpectatingEntities(LivingEntity.class, box);
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.getBoolean("ringing")) {
            nbt.putInt("ringTicks", 1 + nbt.getInt("ringTicks"));
        }

        if (nbt.getInt("ringTicks") >= 50) {
            nbt.putBoolean("ringing", false);
            nbt.putInt("ringTicks", 0);
        }

        if (nbt.getInt("ringTicks") >= 5 && nbt.getInt("resonateTime") == 0 && BellBlockEntityAccessor.callRaidersHearBell(entity.getBlockPos(), hearingEntities)) {
            nbt.putBoolean("resonating", true);
            world.playSoundFromEntity(entity instanceof PlayerEntity player ? player : null, entity, SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        if (nbt.getBoolean("resonating")) {
            if (nbt.getInt("resonateTime") < 40) {
                nbt.putInt("resonateTime", nbt.getInt("resonateTime") + 1);
            } else {
                if (world.isClient()) {
                    BellBlockEntityAccessor.callApplyParticlesToRaiders(world, entity.getBlockPos(), hearingEntities);
                } else {
                    BellBlockEntityAccessor.callApplyGlowToRaiders(world, entity.getBlockPos(), hearingEntities);
                }
                nbt.putBoolean("resonating", false);
            }
        }
        stack.setNbt(nbt);
    }
}
