package me.alphamode.portabletables.client;

import me.alphamode.portabletables.PortableTables;
import me.alphamode.portabletables.ext.ItemInstanceExt;
import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.PortableFurnace;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.ItemInstance;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import org.lwjgl.input.Keyboard;

public class PortableTablesClient {
    public static final KeyMapping CRAFTING_KEYBIND = new KeyMapping("key.portable_tables.open_craft", Keyboard.KEY_V);
    public static final KeyMapping FURNACE_KEYBIND = new KeyMapping("key.portable_tables.open_furnace", Keyboard.KEY_B);

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        PortableTables.PORTABLE_CRAFTING.setTexture(PortableTables.NAMESPACE.id("item/portable_crafting_table"));
        PortableTables.PORTABLE_FURNACE.setTexture(PortableTables.NAMESPACE.id("item/portable_furnace"));
    }

    @EventListener
    public void registerKeyBindings(KeyBindingRegisterEvent event) {
        event.keyBindings.add(CRAFTING_KEYBIND);
        event.keyBindings.add(FURNACE_KEYBIND);
    }

    @EventListener
    public void handleKeybinds(KeyStateChangedEvent event) {
        Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
        if (event.environment == KeyStateChangedEvent.Environment.IN_GAME) {
            if (Keyboard.getEventKey() == CRAFTING_KEYBIND.key) {
                minecraft.player.openPortableTable(AllTables.CRAFTING, null);
            }

            if (Keyboard.getEventKey() == FURNACE_KEYBIND.key) {
                ItemInstance furnace = PortableFurnace.findFirstFurnace(minecraft.player);
                if (furnace != null)
                    minecraft.player.openPortableTable(AllTables.FURNACE, ((ItemInstanceExt) (Object) furnace).pt$getFurnaceOrCreate());
            }
        }
    }
}
