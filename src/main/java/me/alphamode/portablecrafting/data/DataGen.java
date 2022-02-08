package me.alphamode.portablecrafting.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(new TagGen(fabricDataGenerator));
        fabricDataGenerator.addProvider(new RecipeGen(fabricDataGenerator));
    }
}
