package me.alphamode.portablecrafting.mixin;

import me.alphamode.portablecrafting.PortableCrafting;
import me.alphamode.portablecrafting.client.CraftingWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {

    @Shadow @Final private RecipeBookWidget recipeBook;

    @Unique
    private CraftingWidget craftingWidget;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        if(!MinecraftClient.getInstance().player.getInventory().contains(PortableCrafting.WORKBENCH)) {
            return;
        }
        craftingWidget = addDrawableChild(new CraftingWidget());
        if(recipeBook.isOpen()) {
            craftingWidget.setPos(this.width / 2 + 120, this.height / 2 - 21);
        } else {
            craftingWidget.setPos(this.width / 2 + 40, this.height / 2 - 21);
        }
    }

    @Inject(method = "method_19891", at = @At("RETURN"))
    public void onOpen(CallbackInfo ci) {
        if(recipeBook.isOpen()) {
            craftingWidget.setPos(this.width / 2 + 120, this.height / 2 - 20);
        } else {
            craftingWidget.setPos(this.width / 2 + 40, this.height / 2 - 20);
        }
    }
}
