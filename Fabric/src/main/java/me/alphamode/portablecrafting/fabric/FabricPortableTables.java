package me.alphamode.portablecrafting.fabric;

import com.google.common.base.Suppliers;
import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.services.ServiceHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableAnvil;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.handlers.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.function.Supplier;

import static me.alphamode.portablecrafting.PortableTables.asResource;

public class FabricPortableTables implements ModInitializer {
    @Override
    public void onInitialize() {
        PortableTables.init();
        ServerPlayNetworking.registerGlobalReceiver(asResource( "open"), (server, player, handler, buf, responseSender) -> {
            OpenPacket packet = new OpenPacket(buf);
            server.execute(() -> {
                packet.handle(player);
            });
        });

        PortableTables.PORTABLE_CRAFTING = register(Registries.ITEM, "portable_crafting_table", new PortableTable<>(PortableCraftingHandler::openTable, AllTables.CRAFTING, new Item.Settings()));
        PortableTables.PORTABLE_FURNACE = register(Registries.ITEM, "portable_furnace", ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMELTING, AllTables.FURNACE));
        PortableTables.PORTABLE_SMOKER = register(Registries.ITEM, "portable_smoker", ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMOKING, AllTables.SMOKER));
        PortableTables.PORTABLE_BLAST_FURNACE = register(Registries.ITEM, "portable_blast_furnace", ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.BLASTING, AllTables.BLAST));
        PortableTables.PORTABLE_ANVIL = register(Registries.ITEM, "portable_anvil", new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_CHIPPED_ANVIL = register(Registries.ITEM, "portable_chipped_anvil", new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_DAMAGED_ANVIL = register(Registries.ITEM, "portable_damaged_anvil", new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_SMITHING = register(Registries.ITEM, "portable_smithing", new PortableTable<>(PortableSmithingScreenHandler::openTable, AllTables.SMITHING, new Item.Settings()));
        PortableTables.PORTABLE_LOOM = register(Registries.ITEM, "portable_loom", new PortableTable<>(PortableLoomScreenHandler::openTable, AllTables.LOOM, new Item.Settings()));
        PortableTables.PORTABLE_GRINDSTONE = register(Registries.ITEM, "portable_grindstone", new PortableTable<>(PortableGrindstoneScreenHandler::openTable, AllTables.GRINDSTONE, new Item.Settings()));
        PortableTables.PORTABLE_CARTOGRAPHY_TABLE = register(Registries.ITEM, "portable_cartography_table", new PortableTable<>(PortableCartographyTableScreenHandler::openTable, AllTables.CARTOGRAPHY, new Item.Settings()));
        PortableTables.PORTABLE_STONECUTTER = register(Registries.ITEM, "portable_stonecutter", new PortableTable<>(PortableStonecutterScreenHandler::openTable, AllTables.STONECUTTER, new Item.Settings()));
        PortableTables.PORTABLE_BELL = register(Registries.ITEM, "portable_bell", ServiceHelper.PLATFORM_HELPER.createPortableBell(new Item.Settings()));
        
        PortableTables.PORTABLE_FURNACE_HANDLER = register(Registries.SCREEN_HANDLER, "portable_furnace", ServiceHelper.PLATFORM_HELPER.createPortableHandler());

        ResourceManagerHelper.registerBuiltinResourcePack(PortableTables.asResource("classic"), FabricLoader.getInstance().getModContainer(PortableTables.MOD_ID).orElseThrow(), Text.translatable("pack.portable_tables.pack_name"), ResourcePackActivationType.NORMAL);

        FabricItemGroup.builder(PortableTables.TABLE_GROUP)
                .icon(() -> PortableTables.PORTABLE_CRAFTING.get().getDefaultStack())
                .entries(PortableTables::buildTabContents)
                .build();
    }

    public static <V, T extends V> Supplier<T> register(Registry<V> registry, String id, T entry) {
        T registered = Registry.register(registry, PortableTables.asResource(id), entry);
        return Suppliers.memoize(() -> registered);
    }
}
