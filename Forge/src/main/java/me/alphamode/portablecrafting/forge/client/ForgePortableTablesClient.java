package me.alphamode.portablecrafting.forge.client;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.client.PortableTablesClient;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import me.alphamode.portablecrafting.tables.furnace.client.FurnaceTooltipComponent;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceHandler;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceScreen;
import me.alphamode.portablecrafting.tables.furnace.client.PortableFurnaceTooltipData;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PortableTables.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgePortableTablesClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        HandledScreens.<PortableFurnaceScreenHandler, PortableFurnaceScreen>register(PortableTables.PORTABLE_FURNACE_HANDLER.get(), PortableFurnaceScreen::new);

        PortableFurnaceHandler.init();

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @SubscribeEvent
    static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(PortableTablesClient.craftingKeyBind);
        event.register(PortableTablesClient.furnaceKeyBind);
    }

    @SubscribeEvent
    static void tooltipData(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(PortableFurnaceTooltipData.class, FurnaceTooltipComponent::new);
    }

}
