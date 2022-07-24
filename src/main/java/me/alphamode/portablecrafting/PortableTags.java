package me.alphamode.portablecrafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class PortableTags {

    public static final TagKey<Item> PORTABLE_TABLES = ItemTags.create(new ResourceLocation("forge:portable_tables"));
    public static final TagKey<Item> PORTABLE_WORKBENCH = ItemTags.create(new ResourceLocation("forge:portable_workbench"));
    public static final TagKey<Item> PORTABLE_FURNACE = ItemTags.create(new ResourceLocation("forge:portable_furnace"));
    public static final TagKey<Item> WORKBENCH = ItemTags.create(new ResourceLocation("forge:workbench"));

    public static void registerTags() {}
}
