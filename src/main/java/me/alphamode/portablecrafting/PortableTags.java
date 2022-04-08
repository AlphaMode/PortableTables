package me.alphamode.portablecrafting;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PortableTags {
    public static final TagKey<Item> PORTABLE_WORKBENCH = TagKey.of(Registry.ITEM_KEY, new Identifier("c:portable_workbench"));
    public static final TagKey<Item> WORKBENCH = TagKey.of(Registry.ITEM_KEY, new Identifier("c:workbench"));
    public static final TagKey<Item> WORKBENCHES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:workbenches"));
    public static final TagKey<Item> FURNACE = TagKey.of(Registry.ITEM_KEY, new Identifier("c:portable_furnace"));
    public static final TagKey<Item> STICKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:wood_sticks"));
    public static final TagKey<Item> WOODEN_RODS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:wooden_rods"));

    public static void registerTags() {}
}
