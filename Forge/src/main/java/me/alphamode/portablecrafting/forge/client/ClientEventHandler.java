package me.alphamode.portablecrafting.forge.client;

import me.alphamode.portablecrafting.client.PortableTablesClient;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        PortableTablesClient.onClientEndTick(MinecraftClient.getInstance());
    }

    @SubscribeEvent
    void afterScreenInit(ScreenEvent.Init.Post event) {
        PortableTablesClient.onAfterScreenInit(event.getScreen(), event::addListener);
    }
}
