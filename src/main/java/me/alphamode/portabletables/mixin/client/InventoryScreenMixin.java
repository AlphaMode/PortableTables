package me.alphamode.portabletables.mixin.client;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.client.PortableButton;
import me.alphamode.portabletables.ext.InventoryScreenExt;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.PortableFurnace;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends Screen implements InventoryScreenExt {
    private int pt$craftingWidgetId;
    private int pt$furnanceWidgetId;
    @Inject(method = "init", at = @At("TAIL"))
    private void addPortableWidget(CallbackInfo ci) {
        PortableButton craftingButton = new PortableButton(this.buttons.size() + 2, new ItemInstance(Tile.WORKBENCH), AllTables.CRAFTING);
        craftingButton.setPos(this.width / 2 + 40, this.height / 2 - 21);
        pt$craftingWidgetId = craftingButton.id;
        this.buttons.add(craftingButton);

        PortableButton furnanceBUtton = new PortableButton(this.buttons.size() + 2, new ItemInstance(Tile.FURNACE), AllTables.FURNACE);
        furnanceBUtton.setPos(this.width / 2 + 60, this.height / 2 - 21);
        pt$furnanceWidgetId = furnanceBUtton.id;
        this.buttons.add(furnanceBUtton);
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    private void onClick(Button button, CallbackInfo ci) {
        if (button.id == pt$craftingWidgetId || button.id == pt$furnanceWidgetId) {
            PortableButton portableButton = (PortableButton) button;
            AllTables type = portableButton.getType();
            Object data = null;
            if (type == AllTables.FURNACE) {
                ItemInstance furnace = PortableFurnace.findFirstFurnace(this.minecraft.player);
                if (furnace != null) {
                    data = ((ItemInstanceExt) (Object) furnace).pt$getFurnaceOrCreate();
                }
            }
            this.minecraft.player.openPortableTable(type, data);
        }
    }

    @Override
    public int pt$getCraftingWidgetId() {
        return this.pt$craftingWidgetId;
    }

    @Override
    public int pt$getFurnaceWidgetId() {
        return this.pt$furnanceWidgetId;
    }
}
