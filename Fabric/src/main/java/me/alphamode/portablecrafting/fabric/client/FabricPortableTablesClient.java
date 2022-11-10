package me.alphamode.portablecrafting.fabric.client;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.client.PortableTablesClient;
import me.alphamode.portablecrafting.network.SyncPacket;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.furnace.client.FurnaceTooltipComponent;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceScreen;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
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
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class FabricPortableTablesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PortableTablesClient.init();
        KeyBindingHelper.registerKeyBinding(PortableTablesClient.craftingKeyBind);
        KeyBindingHelper.registerKeyBinding(PortableTablesClient.furnaceKeyBind);

        ClientTickEvents.END_CLIENT_TICK.register(PortableTablesClient::onClientEndTick);
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            PortableTablesClient.onAfterScreenInit(screen, portableWidget -> Screens.getButtons(screen).add(portableWidget));
        });
        HandledScreens.<PortableFurnaceScreenHandler, PortableFurnaceScreen>register(PortableTables.PORTABLE_FURNACE_HANDLER.get(), PortableFurnaceScreen::new);

        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof PortableFurnaceTooltipData furnaceData)
                return new FurnaceTooltipComponent(furnaceData);
            return null;
        });

        ClientPlayNetworking.registerGlobalReceiver(PortableTables.asResource("sync"), (client, handler, buf, responseSender) -> {
            SyncPacket packet = new SyncPacket(buf);
            packet.handle();
        });
    }

}
