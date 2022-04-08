package me.alphamode.portablecrafting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PortableCrafting implements ModInitializer {

    public static PortableTable PORTABLECRAFTING;

    public static final TagKey<Item> WORKBENCH = TagKey.of(Registry.ITEM_KEY, new Identifier("c:portable_workbench"));
    public static final TagKey<Item> FURNACE = TagKey.of(Registry.ITEM_KEY, new Identifier("c:portable_furnace"));

    @Override
    public void onInitialize() {
        PORTABLECRAFTING = Registry.register(Registry.ITEM, asResource("portable_crafting_table"), new PortableTable(PortableCraftingHandler::openTable));
        ServerPlayNetworking.registerGlobalReceiver(asResource("open"), (server, player, handler, buf, responseSender) -> PortableCraftingHandler.openTable(player));
        PortableTags.registerTags();
    }

    public static Identifier asResource(String path) {
        return new Identifier("portable_tables", path);
    }
}
