package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.PortableTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {
    public RecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> exporter) {

        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_CRAFTING.get())
                .unlockedBy("always", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY))
                .define('C', PortableTags.WORKBENCH)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" C")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_crafting_table_short"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_CRAFTING.get())
                .unlockedBy("always", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY))
                .define('W', ItemTags.PLANKS)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .save(exporter, PortableTables.asResource("portable_crafting_table"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_FURNACE.get())
                .unlockedBy("always", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY))
                .define('F', Items.FURNACE)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_furnace"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_SMOKER.get())
                .unlockedBy("always", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY))
                .define('F', Items.SMOKER)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_smoker"));
        ShapedRecipeBuilder.shaped(PortableTables.PORTABLE_BLAST_FURNACE.get())
                .unlockedBy("always", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.ANY))
                .define('F', Items.BLAST_FURNACE)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" F")
                .pattern("S ")
                .save(exporter, PortableTables.asResource("portable_blast_furnace"));
    }
}
