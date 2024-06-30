package me.alphamode.portabletables.mixin.common;

import me.alphamode.portabletables.ext.FurnaceTileEntityExt;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInstance.class)
public class ItemInstanceMixin implements ItemInstanceExt {
    private static final String FURNACE_KEY = "PT$Furnace";

    private FurnaceTileEntity pt$furnace;

    @Override
    public FurnaceTileEntity pt$getFurnaceOrCreate() {
        if (this.pt$furnace == null) {
            this.pt$furnace = new FurnaceTileEntity();
            ((FurnaceTileEntityExt)this.pt$furnace).pt$setPortable(true);
        }
        return this.pt$furnace;
    }

    @Override
    public void pt$setFurnace(FurnaceTileEntity te) {
        this.pt$furnace = te;
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void saveFurnace(CompoundTag base, CallbackInfoReturnable<CompoundTag> cir) {
        if (pt$furnace != null) {
            CompoundTag furnace = new CompoundTag();
            pt$furnace.save(furnace);
            base.putTag(FURNACE_KEY, furnace);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void loadFurnace(CompoundTag base, CallbackInfo ci) {
        if (base.hasKey(FURNACE_KEY)) {
            this.pt$furnace = new FurnaceTileEntity();
            ((FurnaceTileEntityExt)this.pt$furnace).pt$setPortable(true);
            this.pt$furnace.load(base.getCompoundTag(FURNACE_KEY));
        }
    }
}
