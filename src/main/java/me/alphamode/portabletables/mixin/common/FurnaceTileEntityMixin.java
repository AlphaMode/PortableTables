package me.alphamode.portabletables.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.alphamode.portabletables.ext.FurnaceTileEntityExt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceTileEntity.class)
public class FurnaceTileEntityMixin implements FurnaceTileEntityExt {

    private boolean pt$portabl;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/tile/FurnaceTile;setLit(ZLnet/minecraft/world/level/Level;III)V"))
    private void checkIsPortable(boolean lit, Level level, int x, int y, int z, Operation<Void> original) {
        if (!pt$isPortable())
            original.call(lit, level, x, y, z);
    }

    @Override
    public void pt$setPortable(boolean portable) {
        this.pt$portabl = portable;
    }

    @Override
    public boolean pt$isPortable() {
        return this.pt$portabl;
    }
}
