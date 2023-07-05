package me.alphamode.portablecrafting.forge.data;

import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registries;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModelsGen extends ItemModelProvider {
    public ModelsGen(DataOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PortableTables.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Registries.ITEM.forEach(item -> {
            if (Registries.ITEM.getId(item).getNamespace().equals("portable_tables"))
                basicItem(item);
        });
    }
}
