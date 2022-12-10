package me.alphamode.portablecrafting.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        // Data
        FabricDataGenerator.Pack pack = gen.createPack();
        pack.addProvider(TagGen::new);
        pack.addProvider(RecipeGen::new);

        // Assets
        pack.addProvider(ModelsGen::new);
    }
}
