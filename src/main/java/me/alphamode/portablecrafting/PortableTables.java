package me.alphamode.portablecrafting;

import me.alphamode.portablecrafting.client.PortableTablesClient;
import me.alphamode.portablecrafting.network.PortableNetwork;
import me.alphamode.portablecrafting.tables.AllTables;
import me.alphamode.portablecrafting.tables.PortableTable;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnace;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.alphamode.portablecrafting.PortableTables.MOD_ID;

@Mod(MOD_ID)
public class PortableTables {
    public static final String MOD_ID = "portable_tables";
    public static final CreativeModeTab TABLE_GROUP = new CreativeModeTab("tables") {
        @Override
        public ItemStack makeIcon() {
            return PORTABLE_CRAFTING.get().getDefaultInstance();
        }
    };

    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<PortableTable<Void>> PORTABLE_CRAFTING = ITEMS.register("portable_crafting_table", () -> new PortableTable<>(PortableCraftingHandler::openTable, AllTables.CRAFTING));
    public static final RegistryObject<PortableTable<ItemStack>> PORTABLE_FURNACE = ITEMS.register("portable_furnace", () -> new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMELTING, AllTables.FURNACE));
    public static final RegistryObject<PortableTable<ItemStack>> PORTABLE_SMOKER = ITEMS.register("portable_smoker", () -> new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.SMOKING, AllTables.SMOKER));
    public static final RegistryObject<PortableTable<ItemStack>> PORTABLE_BLAST_FURNACE = ITEMS.register("portable_blast_furnace", () -> new PortableFurnace(PortableFurnaceScreenHandler::openTable, RecipeType.BLASTING, AllTables.BLAST));

    // Handlers
    public static RegistryObject<MenuType<PortableFurnaceScreenHandler>> PORTABLE_FURNACE_HANDLER = MENUS.register("portable_furnace", () -> new MenuType<>(PortableFurnaceScreenHandler::new));

    public PortableTables() {
        PortableTags.registerTags();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MENUS.register(bus);
        ITEMS.register(bus);

        PortableNetwork.init();
    }

    public static ItemStack findFirstTableTypeInInventory(Container inventory, AllTables type) {
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof PortableTable<?> table && table.getType() == type)
                return itemStack;
        }
        return ItemStack.EMPTY;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation("portable_tables", path);
    }
}
