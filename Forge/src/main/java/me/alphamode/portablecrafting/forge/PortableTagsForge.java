package me.alphamode.portablecrafting.forge;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PortableTagsForge {
    public static final TagKey<Item> WORKBENCH = TagKey.of(RegistryKeys.ITEM, new Identifier("forge:workbench"));
}
