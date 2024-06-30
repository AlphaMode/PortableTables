package me.alphamode.portabletables;

import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.PortableFurnace;
import me.alphamode.portabletables.tables.menus.PortableCraftingMenu;
import me.alphamode.portabletables.tables.menus.PortableFurnaceMenu;
import net.fabricmc.api.ModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.util.Namespace;

public class PortableTables implements ModInitializer {
    public static final String MOD_ID = "portable_tables";
    public static final Namespace NAMESPACE = Namespace.of(MOD_ID);
    public static Item PORTABLE_CRAFTING;
    public static Item PORTABLE_FURNACE;

    @Override
    public void onInitialize() {

    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        PORTABLE_CRAFTING = new PortableTable<>(NAMESPACE.id("portable_crafting_table"), PortableCraftingMenu::openTable, AllTables.CRAFTING)
                .setTranslationKey(NAMESPACE, "portable_crafting_table");
        PORTABLE_FURNACE = new PortableFurnace(NAMESPACE.id("portable_furnace"), PortableFurnaceMenu::openTable, AllTables.FURNACE)
                .setTranslationKey(NAMESPACE, "portable_furnace");
    }
}
