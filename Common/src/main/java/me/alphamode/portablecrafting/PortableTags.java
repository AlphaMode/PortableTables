package me.alphamode.portablecrafting;

import me.alphamode.portablecrafting.services.ServiceHelper;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public class PortableTags {
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

    public static TagKey<Item> portableTag(String path) {
        return ServiceHelper.PLATFORM_HELPER.createPortableTag(path);
    }
}
