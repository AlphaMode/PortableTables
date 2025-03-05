package me.alphamode.portabletables;

import me.alphamode.portabletables.config.PortableConfig;
import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.PortableFurnace;
import me.alphamode.portabletables.tables.menus.PortableCraftingMenu;
import me.alphamode.portabletables.tables.menus.PortableFurnaceMenu;
import net.fabricmc.api.ModInitializer;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.tile.Tile;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.registry.tag.ItemTags;
import net.modificationstation.stationapi.api.util.Namespace;

public class PortableTables implements ModInitializer {
    public static final String MOD_ID = "portable_tables";
    public static final Namespace NAMESPACE = Namespace.of(MOD_ID);
    @ConfigRoot(value = "config", visibleName = "Config")
    public static final PortableConfig CONFIG = new PortableConfig();
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

    @EventListener
    public void addRecipes(RecipeRegisterEvent event) {
        if (event.recipeId != RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED.type())
            return;
        // Portable Crafting
        CraftingRegistry.addShapedRecipe(new ItemInstance(PORTABLE_CRAFTING),
        " WW",
                " WW",
                "S  ",
                'W', ItemTags.PLANKS, 'S', Item.STICK
        );
        CraftingRegistry.addShapedRecipe(new ItemInstance(PORTABLE_CRAFTING),
        " C",
                "S ",
                'C', Tile.WORKBENCH, 'S', Item.STICK
        );

        // Portable Furnace
        CraftingRegistry.addShapedRecipe(new ItemInstance(PORTABLE_FURNACE),
        " F",
                "S ", 'F', Tile.FURNACE, 'S', Item.STICK
        );
    }
}
