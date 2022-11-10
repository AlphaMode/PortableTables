package me.alphamode.portablecrafting.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        // Data
        gen.addProvider(TagGen::new);
        gen.addProvider(RecipeGen::new);

        // Assets
        gen.addProvider(ModelsGen::new);
    }
}
