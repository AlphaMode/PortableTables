package me.alphamode.portabletables.mixin.client;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.client.PortableButton;
import me.alphamode.portabletables.config.PortableConfig;
import me.alphamode.portabletables.ext.InventoryScreenExt;
import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin extends Screen {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onInventoryTick(CallbackInfo ci) {
        if (((Object) this) instanceof InventoryScreenExt inventoryScreen) {
            PortableButton crafting = null;
            PortableButton furnace = null;
            for (Object button : this.buttons) {
                if (button instanceof PortableButton portableButton) {
                    if (portableButton.getType() == AllTables.CRAFTING)
                        crafting = portableButton;
                    else if (portableButton.getType() == AllTables.FURNACE)
                        furnace = portableButton;
                }
            }

            Inventory inventory = this.minecraft.player.inventory;
            boolean craftingVisible = false;
            boolean furnaceVisible = false;
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemInstance item = inventory.getItem(i);

                if (item != null) {
                    if (item.isOf(PortableTables.PORTABLE_CRAFTING))
                        craftingVisible = true;
                    if (item.isOf(PortableTables.PORTABLE_FURNACE))
                        furnaceVisible = true;
                }
            }
            crafting.visible = craftingVisible || PortableTables.CONFIG.craftingWidgetAlwaysEnabled;
            crafting.active = craftingVisible || PortableTables.CONFIG.craftingWidgetAlwaysEnabled;
            furnace.visible = furnaceVisible;
            furnace.active = furnaceVisible;
        }
    }
}
