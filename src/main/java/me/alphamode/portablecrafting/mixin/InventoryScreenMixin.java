package me.alphamode.portablecrafting.mixin;

import me.alphamode.portablecrafting.client.PortableWidget;
import me.alphamode.portablecrafting.tables.AllTables;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {

    @Shadow @Final private RecipeBookWidget recipeBook;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "method_19891", at = @At("RETURN"))
    public void onOpen(CallbackInfo ci) {
        for (ClickableWidget widget : Screens.getButtons(this)) {
            if(widget instanceof PortableWidget craftingWidget) {
                if (recipeBook.isOpen()) {
                    craftingWidget.setPos(this.width / 2 + (craftingWidget.getTableType() == AllTables.CRAFTING ? 120 : 140), this.height / 2 - 20);
                } else {
                    craftingWidget.setPos(this.width / 2 + (craftingWidget.getTableType() == AllTables.CRAFTING ? 40 : 60), this.height / 2 - 20);
                }
            }
        }
    }
}
