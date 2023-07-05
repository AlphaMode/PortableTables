package me.alphamode.portablecrafting.fabric.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import me.alphamode.portablecrafting.fabric.PortableTagsFabric;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TagGen extends FabricTagProvider.ItemTagProvider {
    protected TagGen(FabricDataOutput dataGenerator, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(dataGenerator, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(PortableTags.PORTABLE_WORKBENCH).add(PortableTables.PORTABLE_CRAFTING.get());
        getOrCreateTagBuilder(PortableTagsFabric.WOODEN_RODS);
        getOrCreateTagBuilder(PortableTagsFabric.STICKS).add(Items.STICK).addTag(PortableTagsFabric.WOODEN_RODS);
        getOrCreateTagBuilder(PortableTagsFabric.WORKBENCHES);
        getOrCreateTagBuilder(PortableTagsFabric.WORKBENCH).add(Items.CRAFTING_TABLE).addTag(PortableTagsFabric.WORKBENCHES);
        getOrCreateTagBuilder(PortableTags.PORTABLE_FURNACE).add(PortableTables.PORTABLE_FURNACE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_SMOKER).add(PortableTables.PORTABLE_SMOKER.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_BLAST_FURNACE).add(PortableTables.PORTABLE_BLAST_FURNACE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_ANVIL).add(PortableTables.PORTABLE_ANVIL.get()).add(PortableTables.PORTABLE_CHIPPED_ANVIL.get()).add(PortableTables.PORTABLE_DAMAGED_ANVIL.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_SMITHING).add(PortableTables.PORTABLE_SMITHING.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_LOOM).add(PortableTables.PORTABLE_LOOM.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_GRINDSTONE).add(PortableTables.PORTABLE_GRINDSTONE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_CARTOGRAPHY_TABLE).add(PortableTables.PORTABLE_CARTOGRAPHY_TABLE.get());
        getOrCreateTagBuilder(PortableTags.PORTABLE_STONECUTTER).add(PortableTables.PORTABLE_STONECUTTER.get());
    }
}
