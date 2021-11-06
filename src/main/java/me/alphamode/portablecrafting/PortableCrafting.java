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

    @Override
    public void onInitialize() {
        PORTABLECRAFTING = Registry.register(Registry.ITEM, new Identifier("portable_tables", "portable_crafting_table"), new PortableTable());
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("portable_tables", "open"), (server, player, handler, buf, responseSender) -> PortableCraftingHandler.openTable(player));
        TagFactory.ITEM.create(new Identifier("c:wood_sticks"));
        TagFactory.ITEM.create(new Identifier("c:wooden_rods"));
        TagFactory.ITEM.create(new Identifier("c:workbench"));
    }
}
