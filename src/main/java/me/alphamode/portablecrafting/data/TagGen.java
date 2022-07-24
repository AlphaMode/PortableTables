package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TagGen extends ItemTagsProvider {
    protected TagGen(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, new BlockTagsProvider(dataGenerator, PortableTables.MOD_ID, existingFileHelper), PortableTables.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(PortableTags.PORTABLE_WORKBENCH).add(PortableTables.PORTABLE_CRAFTING.get());
        tag(PortableTags.WOODEN_RODS);
        tag(PortableTags.STICKS).add(Items.STICK).addTag(PortableTags.WOODEN_RODS);
        tag(PortableTags.WORKBENCHES);
        tag(PortableTags.WORKBENCH).add(Items.CRAFTING_TABLE).addTag(PortableTags.WORKBENCHES);
        tag(PortableTags.PORTABLE_FURNACE).add(PortableTables.PORTABLE_FURNACE.get());
        tag(PortableTags.PORTABLE_TABLES)
                .addTag(PortableTags.PORTABLE_WORKBENCH)
                .addTag(PortableTags.PORTABLE_FURNACE);
    }
}
