package me.alphamode.portablecrafting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.tag.TagFactory;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PortableCrafting implements ModInitializer {

    public static PortableTable PORTABLECRAFTING;

    public static final Tag.Identified<Item> WORKBENCH = TagFactory.ITEM.create(new Identifier("c:portable_workbench"));
    public static final Tag.Identified<Item> FURNACE = TagFactory.ITEM.create(new Identifier("c:portable_furnace"));

    @Override
    public void onInitialize() {
        PORTABLECRAFTING = Registry.register(Registry.ITEM, new Identifier("portable_tables", "portable_crafting_table"), new PortableTable(PortableCraftingHandler::openTable));
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("portable_tables", "open"), (server, player, handler, buf, responseSender) -> PortableCraftingHandler.openTable(player));
        PortableTags.registerTags();
    }
}
