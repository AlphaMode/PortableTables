package me.alphamode.portablecrafting.mixin;

import me.alphamode.portablecrafting.client.PortableWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractContainerScreen<InventoryMenu> {
    @Shadow @Final private RecipeBookComponent recipeBookComponent;

    public InventoryScreenMixin(InventoryMenu screenHandler, Inventory playerInventory, Component text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "lambda$init$0", at = @At("RETURN"))
    public void onOpen(CallbackInfo ci) {
        for (GuiEventListener widget : children()) {
            if(widget instanceof PortableWidget craftingWidget) {
                if (recipeBookComponent.isVisible()) {
                    craftingWidget.setPos(this.width / 2 + 120, this.height / 2 - 20);
                } else {
                    craftingWidget.setPos(this.width / 2 + 40, this.height / 2 - 20);
                }
            }
        }
    }
}
