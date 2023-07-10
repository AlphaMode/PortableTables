package me.alphamode.portablecrafting.forge.data;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.data.DataOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModelsGen extends ItemModelProvider {
    public ModelsGen(DataOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PortableTables.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ForgeRegistries.ITEMS.forEach(item -> {
            if (ForgeRegistries.ITEMS.getKey(item).getNamespace().equals("portable_tables"))
                basicItem(item);
        });
    }
}
