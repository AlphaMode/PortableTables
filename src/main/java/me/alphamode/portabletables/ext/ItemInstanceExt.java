package me.alphamode.portabletables.ext;

import net.minecraft.world.level.tile.entity.FurnaceTileEntity;

public interface ItemInstanceExt {
    FurnaceTileEntity pt$getFurnaceOrCreate();

    void pt$setFurnace(FurnaceTileEntity te);
}
