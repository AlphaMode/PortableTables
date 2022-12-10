package me.alphamode.portablecrafting.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.registry.Registries;

public class ModelsGen extends FabricModelProvider {
    public ModelsGen(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        Registries.ITEM.forEach(item -> {
            if (Registries.ITEM.getId(item).getNamespace().equals("portable_tables"))
                gen.register(item, Models.GENERATED);
        });
    }
}
