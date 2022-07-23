package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceScreen;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
import me.alphamode.portablecrafting.tables.furnace.client.FurnaceTooltipComponent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PortableTablesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBinding craftingKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.portable_tables.open_craft", GLFW.GLFW_KEY_V, "key.categories.portable_tables"));
        KeyBinding furnaceKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.portable_tables.open_furnace", GLFW.GLFW_KEY_B, "key.categories.portable_tables"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null)
                return;
            while (craftingKeyBind.wasPressed() && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
                ClientPlayNetworking.send(PortableTables.asResource( "open"), PacketByteBufs.create().writeEnumConstant(AllTables.CRAFTING));
            }
            while (furnaceKeyBind.wasPressed() && client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
                ClientPlayNetworking.send(PortableTables.asResource( "open"), PacketByteBufs.create().writeEnumConstant(AllTables.FURNACE));
            }
            if(client.player != null && client.currentScreen != null && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
                Screens.getButtons(client.currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(true);
                });
            } else if(client.player != null && client.currentScreen != null && !client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
                Screens.getButtons(client.currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(false);
                });
            }
            if(client.player != null && client.currentScreen != null && client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
                Screens.getButtons(client.currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.FURNACE) portableWidget.setVisible(true);
                });
            } else if(client.player != null && client.currentScreen != null && !client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
                Screens.getButtons(client.currentScreen).forEach(clickableWidget -> {
                    if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.FURNACE) portableWidget.setVisible(false);
                });
            }
        });
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof InventoryScreen inventoryScreen) {
                PortableWidget craftingWidget = new PortableWidget(Items.CRAFTING_TABLE, AllTables.CRAFTING);
                PortableWidget furnaceWidget = new PortableWidget(Items.FURNACE, AllTables.FURNACE);
                if(inventoryScreen.getRecipeBookWidget().isOpen()) {
                    craftingWidget.setPos(inventoryScreen.width / 2 + 120, inventoryScreen.height / 2 - 21);
                    furnaceWidget.setPos(inventoryScreen.width / 2 + 140, inventoryScreen.height / 2 - 21);
                } else {
                    craftingWidget.setPos(inventoryScreen.width / 2 + 40, inventoryScreen.height / 2 - 21);
                    furnaceWidget.setPos(inventoryScreen.width / 2 + 60, inventoryScreen.height / 2 - 21);
                }
                Screens.getButtons(inventoryScreen).addAll(List.of(craftingWidget, furnaceWidget));
            }
        });
        HandledScreens.<PortableFurnaceScreenHandler, PortableFurnaceScreen>register(PortableTables.PORTABLE_FURNACE_HANDLER, PortableFurnaceScreen::new);

        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof PortableFurnaceTooltipData furnaceData)
                return new FurnaceTooltipComponent(furnaceData);
            return null;
        });

        PortableFurnaceHandler.init();
    }

}
