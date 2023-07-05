package me.alphamode.portablecrafting.forge.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.ItemTagProvider;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.data.server.tag.vanilla.VanillaBlockTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class TagGen extends ItemTagProvider {
    protected TagGen(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, ExistingFileHelper helper) {
        super(output, completableFuture, CompletableFuture.completedFuture(TagProvider.TagLookup.empty()), PortableTables.MOD_ID, helper);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(PortableTags.PORTABLE_WORKBENCH).add(PortableTables.PORTABLE_CRAFTING.get());
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
