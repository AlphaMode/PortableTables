package me.alphamode.portablecrafting;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class PortableTags {
    public static final Tag.Identified<Item> PORTABLE_WORKBENCH = TagFactory.ITEM.create(new Identifier("c:portable_workbench"));
    public static final Tag.Identified<Item> WORKBENCH = TagFactory.ITEM.create(new Identifier("c:workbench"));
    public static final Tag.Identified<Item> WORKBENCHES = TagFactory.ITEM.create(new Identifier("c:workbenches"));
    public static final Tag.Identified<Item> FURNACE = TagFactory.ITEM.create(new Identifier("c:portable_furnace"));
    public static final Tag.Identified<Item> STICKS = TagFactory.ITEM.create(new Identifier("c:wood_sticks"));
    public static final Tag.Identified<Item> WOODEN_RODS = TagFactory.ITEM.create(new Identifier("c:wooden_rods"));

    public static void registerTags() {}
}
