package me.alphamode.portablecrafting;

import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableBell;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class PortableTables {
    public static final String MOD_ID = "portable_tables";

    public static final Identifier TABLE_GROUP = PortableTables.asResource("tables");
    
    public static Supplier<PortableTable<Void>> PORTABLE_CRAFTING;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_FURNACE;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_SMOKER;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_BLAST_FURNACE;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_ANVIL;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_CHIPPED_ANVIL;
    public static Supplier<PortableTable<ItemStack>> PORTABLE_DAMAGED_ANVIL;
    public static Supplier<PortableTable<Void>> PORTABLE_SMITHING;
    public static Supplier<PortableTable<Void>> PORTABLE_LOOM;
    public static Supplier<PortableTable<Void>> PORTABLE_GRINDSTONE;
    public static Supplier<PortableTable<Void>> PORTABLE_CARTOGRAPHY_TABLE;
    public static Supplier<PortableTable<Void>> PORTABLE_STONECUTTER;
    public static Supplier<PortableBell> PORTABLE_BELL;

    // Handlers
    public static Supplier<ScreenHandlerType<PortableFurnaceScreenHandler>> PORTABLE_FURNACE_HANDLER;

    public static void init() {}

    public static void buildTabContents(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        entries.add(PORTABLE_CRAFTING.get());
        entries.add(PORTABLE_FURNACE.get());
        entries.add(PORTABLE_SMOKER.get());
        entries.add(PORTABLE_BLAST_FURNACE.get());
        entries.add(PORTABLE_ANVIL.get());
        entries.add(PORTABLE_CHIPPED_ANVIL.get());
        entries.add(PORTABLE_DAMAGED_ANVIL.get());
        entries.add(PORTABLE_SMITHING.get());
        entries.add(PORTABLE_LOOM.get());
        entries.add(PORTABLE_GRINDSTONE.get());
        entries.add(PORTABLE_CARTOGRAPHY_TABLE.get());
        entries.add(PORTABLE_STONECUTTER.get());
        entries.add(PORTABLE_BELL.get());
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
