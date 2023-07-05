package me.alphamode.portablecrafting.forge.data;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortableTables.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Data
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        DataOutput output = gen.getPackOutput();
        gen.addProvider(event.includeServer(), new TagGen(output, event.getLookupProvider(), helper));
        gen.addProvider(event.includeServer(), new RecipeGen(output));

        // Assets
        gen.addProvider(event.includeClient(), new ModelsGen(output, helper));
    }
}
