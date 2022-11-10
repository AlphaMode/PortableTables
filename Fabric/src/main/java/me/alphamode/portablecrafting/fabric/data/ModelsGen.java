package me.alphamode.portablecrafting.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.registry.Registry;

public class ModelsGen extends FabricModelProvider {
    public ModelsGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        Registry.ITEM.forEach(item -> {
            if (Registry.ITEM.getId(item).getNamespace().equals("portable_tables"))
                gen.register(item, Models.GENERATED);
        });
    }
}
