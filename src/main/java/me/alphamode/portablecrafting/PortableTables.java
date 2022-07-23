package me.alphamode.portablecrafting;

import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PortableTables implements ModInitializer {
    public static final ItemGroup TABLE_GROUP = FabricItemGroupBuilder.build(asResource("tables"), () -> Registry.ITEM.get(asResource("portable_crafting_table")).getDefaultStack());

    public static final PortableTable<Void> PORTABLE_CRAFTING = Registry.register(Registry.ITEM, asResource("portable_crafting_table"), new PortableTable<>(PortableCraftingHandler::openTable, AllTables.CRAFTING));
    public static final PortableTable<ItemStack> PORTABLE_FURNACE = Registry.register(Registry.ITEM, asResource("portable_furnace"), new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMELTING, AllTables.FURNACE));
    public static final PortableTable<ItemStack> PORTABLE_SMOKER = Registry.register(Registry.ITEM, asResource("portable_smoker"), new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMOKING, AllTables.SMOKER));
    public static final PortableTable<ItemStack> PORTABLE_BLAST_FURNACE = Registry.register(Registry.ITEM, asResource("portable_blast_furnace"), new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.BLASTING, AllTables.BLAST));

    // Handlers
    public static ScreenHandlerType<PortableFurnaceScreenHandler> PORTABLE_FURNACE_HANDLER;

    @Override
    public void onInitialize() {
        PORTABLE_FURNACE_HANDLER = Registry.register(Registry.SCREEN_HANDLER, asResource("portable_furnace"), new ScreenHandlerType<>(PortableFurnaceScreenHandler::new));
        ServerPlayNetworking.registerGlobalReceiver(asResource("open"), (server, player, handler, buf, responseSender) -> {
            AllTables table = buf.readEnumConstant(AllTables.class);
            switch (table) {
                case CRAFTING -> PortableCraftingHandler.openTable(player, null);
                case FURNACE -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.FURNACE));
                case SMOKER -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.SMOKER));
                case BLAST -> PortableFurnaceScreenHandler.openTable(player, findFirstTableTypeInInventory(player.getInventory(), AllTables.BLAST));
            }

        });
        PortableTags.registerTags();
    }

    public static ItemStack findFirstTableTypeInInventory(Inventory inventory, AllTables type) {
        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.getItem() instanceof PortableTable<?> table && table.getType() == type)
                return itemStack;
        }
        return ItemStack.EMPTY;
    }

    public static Identifier asResource(String path) {
        return new Identifier("portable_tables", path);
    }
}
