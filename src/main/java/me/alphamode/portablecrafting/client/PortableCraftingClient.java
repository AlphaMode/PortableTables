package me.alphamode.portablecrafting.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
        });
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof InventoryScreen inventoryScreen) {
                //Screens.getButtons(inventoryScreen).add(new CraftingWidget());
            }
        });
    }

}
