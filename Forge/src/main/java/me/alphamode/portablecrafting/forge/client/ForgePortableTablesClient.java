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
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
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
        ClientRegistry.registerKeyBinding(PortableTablesClient.craftingKeyBind);
        ClientRegistry.registerKeyBinding(PortableTablesClient.furnaceKeyBind);
        MinecraftForgeClient.registerTooltipComponentFactory(PortableFurnaceTooltipData.class, FurnaceTooltipComponent::new);

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
