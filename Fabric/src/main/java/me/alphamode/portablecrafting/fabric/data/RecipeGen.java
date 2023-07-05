package me.alphamode.portablecrafting.fabric.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.fabric.PortableTagsFabric;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Consumer;

public class RecipeGen extends FabricRecipeProvider {
    public RecipeGen(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, PortableTables.PORTABLE_CRAFTING.get())
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('C', PortableTagsFabric.WORKBENCH)
                .input('S', PortableTagsFabric.STICKS)
                .pattern(" C")
                .pattern("S ")
                .offerTo(exporter, PortableTables.asResource("portable_crafting_table_short"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, PortableTables.PORTABLE_CRAFTING.get())
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('W', ItemTags.PLANKS)
                .input('S', PortableTagsFabric.STICKS)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .offerTo(exporter, PortableTables.asResource("portable_crafting_table"));
        createPortableRecipe(exporter, PortableTables.PORTABLE_FURNACE.get(), Items.FURNACE);
        createPortableRecipe(exporter, PortableTables.PORTABLE_SMOKER.get(), Items.SMOKER);
        createPortableRecipe(exporter, PortableTables.PORTABLE_BLAST_FURNACE.get(), Items.BLAST_FURNACE);
        createPortableRecipe(exporter, PortableTables.PORTABLE_ANVIL.get(), Items.ANVIL);
        createPortableRecipe(exporter, PortableTables.PORTABLE_CHIPPED_ANVIL.get(), Items.CHIPPED_ANVIL);
        createPortableRecipe(exporter, PortableTables.PORTABLE_DAMAGED_ANVIL.get(), Items.DAMAGED_ANVIL);
        createPortableRecipe(exporter, PortableTables.PORTABLE_SMITHING.get(), Items.SMITHING_TABLE);
        createPortableRecipe(exporter, PortableTables.PORTABLE_LOOM.get(), Items.LOOM);
        createPortableRecipe(exporter, PortableTables.PORTABLE_GRINDSTONE.get(), Items.GRINDSTONE);
        createPortableRecipe(exporter, PortableTables.PORTABLE_CARTOGRAPHY_TABLE.get(), Items.CARTOGRAPHY_TABLE);
        createPortableRecipe(exporter, PortableTables.PORTABLE_STONECUTTER.get(), Items.STONECUTTER);
        createPortableRecipe(exporter, PortableTables.PORTABLE_BELL.get(), Items.BELL);
    }

    public void createPortableRecipe(Consumer<RecipeJsonProvider> exporter, Item portableTable, Item table) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, portableTable)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('T', table)
                .input('S', PortableTagsFabric.STICKS)
                .pattern(" T")
                .pattern("S ")
                .offerTo(exporter, Registries.ITEM.getId(portableTable));
    }
}
