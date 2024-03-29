package me.alphamode.portablecrafting;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PortableTags {

    public static final TagKey<Item> PORTABLE_TABLES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:portable_tables"));
    public static final TagKey<Item> PORTABLE_WORKBENCH = portableTag("workbench");
    public static final TagKey<Item> PORTABLE_FURNACE = portableTag("furnace");
    public static final TagKey<Item> PORTABLE_SMOKER = portableTag("smoker");
    public static final TagKey<Item> PORTABLE_BLAST_FURNACE= portableTag("blast_furnace");
    public static final TagKey<Item> PORTABLE_ANVIL = portableTag("anvil");
    public static final TagKey<Item> PORTABLE_SMITHING = portableTag("smithing");
    public static final TagKey<Item> PORTABLE_LOOM = portableTag("loom");
    public static final TagKey<Item> PORTABLE_GRINDSTONE = portableTag("grindstone");
    public static final TagKey<Item> PORTABLE_CARTOGRAPHY_TABLE = portableTag("catography_table");
    public static final TagKey<Item> PORTABLE_STONECUTTER = portableTag("stonecutter");
    public static final TagKey<Item> WORKBENCH = TagKey.of(Registry.ITEM_KEY, new Identifier("c:workbench"));
    public static final TagKey<Item> WORKBENCHES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:workbenches"));
    public static final TagKey<Item> STICKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:wood_sticks"));
    public static final TagKey<Item> WOODEN_RODS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:wooden_rods"));

    public static TagKey<Item> portableTag(String path) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("c", "portable/" + path));
    }

    public static void registerTags() {}
}
