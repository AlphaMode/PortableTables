package me.alphamode.portablecrafting.fabric.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class TagGen extends FabricTagProvider<Item> {
    protected TagGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.ITEM);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(PortableTags.PORTABLE_WORKBENCH).add(PortableTables.PORTABLE_CRAFTING.get());
        getOrCreateTagBuilder(PortableTags.WOODEN_RODS);
        getOrCreateTagBuilder(PortableTags.STICKS).add(Items.STICK).addTag(PortableTags.WOODEN_RODS);
        getOrCreateTagBuilder(PortableTags.WORKBENCHES);
        getOrCreateTagBuilder(PortableTags.WORKBENCH).add(Items.CRAFTING_TABLE).addTag(PortableTags.WORKBENCHES);
        getOrCreateTagBuilder(PortableTags.PORTABLE_FURNACE).add(PortableTables.PORTABLE_FURNACE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_ANVIL).add(PortableTables.PORTABLE_ANVIL.get()).add(PortableTables.PORTABLE_CHIPPED_ANVIL.get()).add(PortableTables.PORTABLE_DAMAGED_ANVIL.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_SMITHING).add(PortableTables.PORTABLE_SMITHING.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_LOOM).add(PortableTables.PORTABLE_LOOM.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_GRINDSTONE).add(PortableTables.PORTABLE_GRINDSTONE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_CARTOGRAPHY_TABLE).add(PortableTables.PORTABLE_CARTOGRAPHY_TABLE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_STONECUTTER).add(PortableTables.PORTABLE_STONECUTTER.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_TABLES)
                .addTag(PortableTags.PORTABLE_WORKBENCH)
                .addTag(PortableTags.PORTABLE_FURNACE)
                .addTag(PortableTags.PORTABLE_ANVIL)
                .addTag(PortableTags.PORTABLE_SMITHING)
                .addTag(PortableTags.PORTABLE_LOOM)
                .addTag(PortableTags.PORTABLE_GRINDSTONE)
                .addTag(PortableTags.PORTABLE_CARTOGRAPHY_TABLE)
                .addTag(PortableTags.PORTABLE_STONECUTTER)
                .add(PortableTables.PORTABLE_SMOKER.get())
                .add(PortableTables.PORTABLE_BLAST_FURNACE.get());
    }
}
