package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;

import java.util.function.Consumer;

public class RecipeGen extends FabricRecipeProvider {
    public RecipeGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(PortableTables.PORTABLE_CRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('C', PortableTags.WORKBENCH)
                .input('S', PortableTags.STICKS)
                .pattern(" C")
                .pattern("S ")
                .offerTo(exporter, PortableTables.asResource("portable_crafting_table_short"));
        ShapedRecipeJsonBuilder.create(PortableTables.PORTABLE_CRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('W', ItemTags.PLANKS)
                .input('S', PortableTags.STICKS)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .offerTo(exporter, PortableTables.asResource("portable_crafting_table"));
        ShapedRecipeJsonBuilder.create(PortableTables.PORTABLE_FURNACE)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('F', Items.FURNACE)
                .input('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .offerTo(exporter, PortableTables.asResource("portable_furnace"));
        ShapedRecipeJsonBuilder.create(PortableTables.PORTABLE_SMOKER)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('F', Items.SMOKER)
                .input('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .offerTo(exporter, PortableTables.asResource("portable_smoker"));
        ShapedRecipeJsonBuilder.create(PortableTables.PORTABLE_BLAST_FURNACE)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('F', Items.BLAST_FURNACE)
                .input('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .offerTo(exporter, PortableTables.asResource("portable_blast_furnace"));
    }
}
