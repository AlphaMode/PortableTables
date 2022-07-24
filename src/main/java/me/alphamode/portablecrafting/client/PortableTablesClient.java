package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.network.PortableNetwork;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.furnace.client.FurnaceTooltipComponent;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceScreen;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = PortableTables.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PortableTablesClient {
    private static final KeyMapping craftingKeyBind = new KeyMapping("key.portable_tables.open_craft", GLFW.GLFW_KEY_V, "key.categories.portable_tables");
    private static final KeyMapping furnaceKeyBind = new KeyMapping("key.portable_tables.open_furnace", GLFW.GLFW_KEY_B, "key.categories.portable_tables");

    @SubscribeEvent
    static void onInitializeClient(FMLClientSetupEvent event) {
        MenuScreens.<PortableFurnaceScreenHandler, PortableFurnaceScreen>register(PortableTables.PORTABLE_FURNACE_HANDLER.get(), PortableFurnaceScreen::new);

        PortableFurnaceHandler.init();

        MinecraftForge.EVENT_BUS.register(new PortableTablesClient());
    }

    @SubscribeEvent
    static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(craftingKeyBind);
        event.register(furnaceKeyBind);
    }

    @SubscribeEvent
    void keyInput(InputEvent.Key event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null)
            return;
        if (event.getKey() == craftingKeyBind.getKey().getValue() && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            PortableNetwork.CHANNEL.send(PacketDistributor.SERVER.noArg(), new OpenPacket(AllTables.CRAFTING));
        }
        if (event.getKey() == furnaceKeyBind.getKey().getValue() && client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
            PortableNetwork.CHANNEL.send(PacketDistributor.SERVER.noArg(), new OpenPacket(AllTables.FURNACE));
        }
    }

    @SubscribeEvent
    void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        Minecraft client = Minecraft.getInstance();
        if(client.player != null && client.screen != null && client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            client.screen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(true);
            });
        } else if(client.player != null && client.screen != null && !client.player.getInventory().contains(PortableTags.PORTABLE_WORKBENCH)) {
            client.screen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.CRAFTING) portableWidget.setVisible(false);
            });
        }
        if(client.player != null && client.screen != null && client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
            client.screen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.FURNACE) portableWidget.setVisible(true);
            });
        } else if(client.player != null && client.screen != null && !client.player.getInventory().contains(PortableTags.PORTABLE_FURNACE)) {
            client.screen.children().forEach(clickableWidget -> {
                if(clickableWidget instanceof PortableWidget portableWidget && portableWidget.getTableType() == AllTables.FURNACE) portableWidget.setVisible(false);
            });
        }
    }

    @SubscribeEvent
    void afterScreenInit(ScreenEvent.Init.Post event) {
        if(event.getScreen() instanceof InventoryScreen inventoryScreen) {
            PortableWidget craftingWidget = new PortableWidget(Items.CRAFTING_TABLE, AllTables.CRAFTING);
            PortableWidget furnaceWidget = new PortableWidget(Items.FURNACE, AllTables.FURNACE);
            if(inventoryScreen.getRecipeBookComponent().isVisible()) {
                craftingWidget.setPos(inventoryScreen.width / 2 + 120, inventoryScreen.height / 2 - 21);
                furnaceWidget.setPos(inventoryScreen.width / 2 + 140, inventoryScreen.height / 2 - 21);
            } else {
                craftingWidget.setPos(inventoryScreen.width / 2 + 40, inventoryScreen.height / 2 - 21);
                furnaceWidget.setPos(inventoryScreen.width / 2 + 60, inventoryScreen.height / 2 - 21);
            }
            event.addListener(craftingWidget);
            event.addListener(furnaceWidget);
        }
    }

    @SubscribeEvent
    static void tooltipData(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(PortableFurnaceTooltipData.class, FurnaceTooltipComponent::new);
    }

}
