package me.alphamode.portablecrafting;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class PortableTags {
    public static final Tag.Identified<Item> WORKBENCH = TagFactory.ITEM.create(new Identifier("c:portable_workbench"));
    public static final Tag.Identified<Item> FURNACE = TagFactory.ITEM.create(new Identifier("c:portable_furnace"));
    public static final Tag.Identified<Item> STICKS = TagFactory.ITEM.create(new Identifier("c:wood_sticks"));

    public static void registerTags() {
        TagFactory.ITEM.create(new Identifier("c:wooden_rods"));
        TagFactory.ITEM.create(new Identifier("c:workbench"));
        TagFactory.ITEM.create(new Identifier("c:workbenches"));
    }
}
