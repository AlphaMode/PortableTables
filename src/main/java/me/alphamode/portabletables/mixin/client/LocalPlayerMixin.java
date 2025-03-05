package me.alphamode.portabletables.mixin.client;

import me.alphamode.portabletables.ext.FurnaceTileEntityExt;
import me.alphamode.portabletables.ext.PlayerExt;
import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.PortableFurnace;
import me.alphamode.portabletables.tables.menus.PortableCraftingMenu;
import me.alphamode.portabletables.tables.menus.PortableFurnaceMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.input.Input;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends Player implements PlayerExt {
    @Shadow protected Minecraft minecraft;

    @Shadow public Input input;

    public LocalPlayerMixin(Level arg) {
        super(arg);
    }

    @Override
    public void openPortableTable(AllTables type, Object data) {
        switch (type) {
            case CRAFTING -> {
                CraftingScreen screen = new CraftingScreen(this.inventory, this.level, 0, 0, 0);
                screen.menu = new PortableCraftingMenu(this.inventory, this.level);
                this.minecraft.setScreen(screen);
            }
            case FURNACE -> {
                FurnaceTileEntity furnace;
                if (data != null)
                    furnace = (FurnaceTileEntity) data;
                else
                    furnace = new FurnaceTileEntity();
                ((FurnaceTileEntityExt)furnace).pt$setPortable(true);
                FurnaceScreen screen = new FurnaceScreen(this.inventory, furnace);
                screen.menu = new PortableFurnaceMenu(this.inventory, furnace);
                this.minecraft.setScreen(screen);
            }
        }

    }
}
