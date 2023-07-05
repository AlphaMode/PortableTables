package me.alphamode.portablecrafting.forge;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.forge.network.ForgeNetwork;
import me.alphamode.portablecrafting.services.ServiceHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableAnvil;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.handlers.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;

@Mod(PortableTables.MOD_ID)
public class ForgePortableTables {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PortableTables.MOD_ID);
    private static final DeferredRegister<ScreenHandlerType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PortableTables.MOD_ID);

    public ForgePortableTables() {
        PortableTables.init();
        ForgeNetwork.init();

        PortableTables.PORTABLE_CRAFTING = ITEMS.register("portable_crafting_table", () -> new PortableTable<>(PortableCraftingHandler::openTable, AllTables.CRAFTING, new Item.Settings()));
        PortableTables.PORTABLE_FURNACE = ITEMS.register("portable_furnace", () -> ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMELTING, AllTables.FURNACE));
        PortableTables.PORTABLE_SMOKER = ITEMS.register("portable_smoker", () -> ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMOKING, AllTables.SMOKER));
        PortableTables.PORTABLE_BLAST_FURNACE = ITEMS.register("portable_blast_furnace", () -> ServiceHelper.PLATFORM_HELPER.createNewPortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.BLASTING, AllTables.BLAST));
        PortableTables.PORTABLE_ANVIL = ITEMS.register("portable_anvil", () -> new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_CHIPPED_ANVIL = ITEMS.register("portable_chipped_anvil", () -> new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_DAMAGED_ANVIL = ITEMS.register("portable_damaged_anvil", () -> new PortableAnvil(PortableAnvilHandler::openTable, AllTables.ANVIL, new Item.Settings()));
        PortableTables.PORTABLE_SMITHING = ITEMS.register("portable_smithing", () -> new PortableTable<>(PortableSmithingScreenHandler::openTable, AllTables.SMITHING, new Item.Settings()));
        PortableTables.PORTABLE_LOOM = ITEMS.register("portable_loom", () -> new PortableTable<>(PortableLoomScreenHandler::openTable, AllTables.LOOM, new Item.Settings()));
        PortableTables.PORTABLE_GRINDSTONE = ITEMS.register("portable_grindstone", () -> new PortableTable<>(PortableGrindstoneScreenHandler::openTable, AllTables.GRINDSTONE, new Item.Settings()));
        PortableTables.PORTABLE_CARTOGRAPHY_TABLE = ITEMS.register("portable_cartography_table", () -> new PortableTable<>(PortableCartographyTableScreenHandler::openTable, AllTables.CARTOGRAPHY, new Item.Settings()));
        PortableTables.PORTABLE_STONECUTTER = ITEMS.register("portable_stonecutter", () -> new PortableTable<>(PortableStonecutterScreenHandler::openTable, AllTables.STONECUTTER, new Item.Settings()));
        PortableTables.PORTABLE_BELL = ITEMS.register("portable_bell", () -> ServiceHelper.PLATFORM_HELPER.createPortableBell(new Item.Settings()));

        PortableTables.PORTABLE_FURNACE_HANDLER = MENUS.register("portable_furnace", () -> ServiceHelper.PLATFORM_HELPER.createPortableHandler());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(bus);
        MENUS.register(bus);

        bus.addListener(ForgePortableTables::addClassicPack);
        bus.addListener(ForgePortableTables::registerPortableTab);
    }

    public static void registerPortableTab(RegisterEvent event) {
        if (event.getRegistryKey().equals(RegistryKeys.ITEM_GROUP)) {
            Identifier tabId = PortableTables.asResource("tables");
            event.register(RegistryKeys.ITEM_GROUP, tabId, () -> ItemGroup.builder()
                        .icon(() -> PortableTables.PORTABLE_CRAFTING.get().getDefaultStack())
                        .displayName(Text.translatable("itemGroup.portable_tables.tables"))
                        .entries(PortableTables::buildTabContents)
                        .build());
        }
    }

    public static void addClassicPack(AddPackFindersEvent event) {
        if (event.getPackType() == ResourceType.CLIENT_RESOURCES) {
            var resourcePath = ModList.get().getModFileById(PortableTables.MOD_ID).getFile().findResource("classic");
            event.addRepositorySource(source ->
                    source.accept(ResourcePackProfile.create(
                            PortableTables.MOD_ID + ":classic", Text.translatable("pack.portable_tables.pack_name"), false,
                            (path) -> new PathPackResources(path, true, resourcePath), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN)));
        }
    }
}
