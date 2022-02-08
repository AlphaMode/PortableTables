package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableCrafting;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class TagGen extends FabricTagProvider<Item> {
    /**
     * Construct a new {@link FabricTagProvider}.
     *
     * <p>Common implementations of this class are provided. For example @see BlockTagProvider
     *
     * @param dataGenerator The data generator instance
     */
    protected TagGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.ITEM, "items", "Portable Tables: Item Tags");
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(PortableTags.WORKBENCH).add(PortableCrafting.PORTABLECRAFTING);
//        getOrCreateTagBuilder(PortableTags.FURNACE).add(PortableCrafting.PORTABLEFURNACE);
    }
}
