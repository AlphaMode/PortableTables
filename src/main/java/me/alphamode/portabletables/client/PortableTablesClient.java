package me.alphamode.portabletables.client;

import me.alphamode.portabletables.PortableTables;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class PortableTablesClient {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        PortableTables.PORTABLE_CRAFTING.setTexture(PortableTables.NAMESPACE.id("item/portable_crafting_table"));
        PortableTables.PORTABLE_FURNACE.setTexture(PortableTables.NAMESPACE.id("item/portable_furnace"));
    }
}
