package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.PortableCrafting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PortableCraftingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBinding keyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.portable_tables.open_craft", GLFW.GLFW_KEY_V, "key.categories.portable_tables"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBind.wasPressed()) {
                ClientPlayNetworking.send(new Identifier("portable_tables", "open"), PacketByteBufs.empty());
            }
            if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().currentScreen != null && MinecraftClient.getInstance().player.getInventory().contains(PortableCrafting.WORKBENCH)) {
                Screens.getButtons(MinecraftClient.getInstance().currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof CraftingWidget portableWidget) portableWidget.setVisible(true);
                });
            } else if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().currentScreen != null && !MinecraftClient.getInstance().player.getInventory().contains(PortableCrafting.WORKBENCH)) {
                Screens.getButtons(MinecraftClient.getInstance().currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof CraftingWidget portableWidget) portableWidget.setVisible(false);
                });
            }
        });
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof InventoryScreen inventoryScreen) {
                CraftingWidget craftingWidget = new CraftingWidget();
                if(inventoryScreen.getRecipeBookWidget().isOpen()) {
                    craftingWidget.setPos(inventoryScreen.width / 2 + 120, inventoryScreen.height / 2 - 21);
                } else {
                    craftingWidget.setPos(inventoryScreen.width / 2 + 40, inventoryScreen.height / 2 - 21);
                }
                Screens.getButtons(inventoryScreen).add(craftingWidget);
            }
        });
    }

}
