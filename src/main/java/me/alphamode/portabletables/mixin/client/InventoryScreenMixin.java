package me.alphamode.portabletables.mixin.client;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.client.PortableButton;
import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends Screen {
    private int pt$widgetId;
    @Inject(method = "init", at = @At("TAIL"))
    private void addPortableWidget(CallbackInfo ci) {
        PortableButton button = new PortableButton(this.buttons.size() + 2, new ItemInstance(Tile.WORKBENCH), AllTables.CRAFTING);
        button.setPos(this.width / 2 + 40, this.height / 2 - 21);
        pt$widgetId = button.id;
        this.buttons.add(button);
    }

    @Inject(method = "onButtonClick", at = @At("TAIL"))
    private void onClick(Button button, CallbackInfo ci) {
        if (button.id == pt$widgetId) {
            PortableButton portableButton = (PortableButton) button;
            this.minecraft.player.openPortableTable(portableButton.getType(), null);
        }
    }
}
