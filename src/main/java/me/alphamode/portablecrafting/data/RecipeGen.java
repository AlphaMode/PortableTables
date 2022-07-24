package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {
    public RecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> exporter) {

        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_CRAFTING.get())
//                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .define('C', PortableTags.WORKBENCH)
                .define('S', PortableTags.STICKS)
                .pattern(" C")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_crafting_table_short"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_CRAFTING.get())
//                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .define('W', ItemTags.PLANKS)
                .define('S', PortableTags.STICKS)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .save(exporter, PortableTables.asResource("portable_crafting_table"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_FURNACE.get())
//                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .define('F', Items.FURNACE)
                .define('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_furnace"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_SMOKER.get())
//                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .define('F', Items.SMOKER)
                .define('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_smoker"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_BLAST_FURNACE.get())
//                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .define('F', Items.BLAST_FURNACE)
                .define('S', PortableTags.STICKS)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_blast_furnace"));
    }
}
