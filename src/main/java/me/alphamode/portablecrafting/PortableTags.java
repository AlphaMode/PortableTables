package me.alphamode.portablecrafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class PortableTags {

    public static final TagKey<Item> PORTABLE_TABLES = ItemTags.create(new ResourceLocation("c:portable_tables"));
    public static final TagKey<Item> PORTABLE_WORKBENCH = ItemTags.create(new ResourceLocation("c:portable_workbench"));
    public static final TagKey<Item> PORTABLE_FURNACE = ItemTags.create(new ResourceLocation("c:portable_furnace"));
    public static final TagKey<Item> WORKBENCH = ItemTags.create(new ResourceLocation("c:workbench"));
    public static final TagKey<Item> WORKBENCHES = ItemTags.create(new ResourceLocation("c:workbenches"));
    public static final TagKey<Item> STICKS = ItemTags.create(new ResourceLocation("c:wood_sticks"));
    public static final TagKey<Item> WOODEN_RODS = ItemTags.create(new ResourceLocation("c:wooden_rods"));

    public static void registerTags() {}
}
