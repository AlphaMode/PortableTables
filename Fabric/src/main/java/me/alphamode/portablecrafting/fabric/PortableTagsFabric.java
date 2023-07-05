package me.alphamode.portablecrafting.fabric;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PortableTagsFabric {
    public static final TagKey<Item> WORKBENCH = TagKey.of(RegistryKeys.ITEM, new Identifier("c:workbench"));
    public static final TagKey<Item> WORKBENCHES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:workbenches"));
    public static final TagKey<Item> STICKS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:wood_sticks"));
    public static final TagKey<Item> WOODEN_RODS = TagKey.of(RegistryKeys.ITEM, new Identifier("c:wooden_rods"));
}
