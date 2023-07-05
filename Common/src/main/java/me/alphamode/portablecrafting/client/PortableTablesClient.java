package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.services.ServiceHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class PortableTablesClient {
    public static final KeyBinding craftingKeyBind = new KeyBinding("key.portable_tables.open_craft", GLFW.GLFW_KEY_V, "key.categories.portable_tables");
    public static final KeyBinding furnaceKeyBind = new KeyBinding("key.portable_tables.open_furnace", GLFW.GLFW_KEY_B, "key.categories.portable_tables");

    public static void init() {
        PortableFurnaceHandler.init();
    }

    public static void onClientEndTick(MinecraftClient client) {
        if (client.player == null)
            return;
        while (craftingKeyBind.wasPressed() && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            ServiceHelper.CLIENT_PLATFORM_HELPER.sendOpenPacket(new OpenPacket(AllTables.CRAFTING));
        }
        while (furnaceKeyBind.wasPressed()) {
            if (client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE))
                ServiceHelper.CLIENT_PLATFORM_HELPER.sendOpenPacket(new OpenPacket(AllTables.FURNACE));
            else if (client.player.getInventory().contains(PortableTags.PORTABLE_BLAST_FURNACE))
                ServiceHelper.CLIENT_PLATFORM_HELPER.sendOpenPacket(new OpenPacket(AllTables.BLAST));
            else if (client.player.getInventory().contains(PortableTags.PORTABLE_SMOKER))
                ServiceHelper.CLIENT_PLATFORM_HELPER.sendOpenPacket(new OpenPacket(AllTables.SMOKER));
        }
        if(client.player != null && client.currentScreen != null && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            client.currentScreen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(true);
            });
        } else if(client.player != null && client.currentScreen != null && !client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            client.currentScreen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(false);
            });
        }
        if(client.player != null && client.currentScreen != null && (client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE) || client.player.getInventory().contains(PortableTags.PORTABLE_SMOKER) || client.player.getInventory().contains(PortableTags.PORTABLE_BLAST_FURNACE))) {
            client.currentScreen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType().isFurnaceLike()) {
                    portableWidget.setVisible(true);
                    if (client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE))
                        portableWidget.setType(AllTables.FURNACE);
                    else if (client.player.getInventory().contains(PortableTags.PORTABLE_BLAST_FURNACE))
                        portableWidget.setType(AllTables.BLAST);
                    else if (client.player.getInventory().contains(PortableTags.PORTABLE_SMOKER))
                        portableWidget.setType(AllTables.SMOKER);
                }
            });
        } else if(client.player != null && client.currentScreen != null && !(client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE) || client.player.getInventory().contains(PortableTags.PORTABLE_SMOKER) || client.player.getInventory().contains(PortableTags.PORTABLE_BLAST_FURNACE))) {
            client.currentScreen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.FURNACE) portableWidget.setVisible(false);
            });
        }
    }

    public static void onAfterScreenInit(Screen screen, Consumer<PortableWidget> widgetAdder) {
        if(screen instanceof InventoryScreen inventoryScreen) {
            PortableWidget craftingWidget = new PortableWidget(Items.CRAFTING_TABLE, AllTables.CRAFTING);
            PortableWidget furnaceWidget = new PortableWidget(Items.FURNACE, AllTables.FURNACE);
            if(inventoryScreen.getRecipeBookWidget().isOpen()) {
                craftingWidget.setPosition(inventoryScreen.width / 2 + 120, inventoryScreen.height / 2 - 21);
                furnaceWidget.setPosition(inventoryScreen.width / 2 + 140, inventoryScreen.height / 2 - 21);
            } else {
                craftingWidget.setPosition(inventoryScreen.width / 2 + 40, inventoryScreen.height / 2 - 21);
                furnaceWidget.setPosition(inventoryScreen.width / 2 + 60, inventoryScreen.height / 2 - 21);
            }
            widgetAdder.accept(craftingWidget);
            widgetAdder.accept(furnaceWidget);
        }
    }

}
