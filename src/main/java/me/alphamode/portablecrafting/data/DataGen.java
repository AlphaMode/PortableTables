package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = PortableTables.MOD_ID)
public class DataGen {
    public void onInitializeDataGenerator(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        gen.addProvider(event.includeServer(), new TagGen(gen, helper));
        gen.addProvider(event.includeServer(), new RecipeGen(gen));
    }
}
