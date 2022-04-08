package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableCrafting;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;

import java.util.function.Consumer;

public class RecipeGen extends FabricRecipesProvider {
    public RecipeGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(PortableCrafting.PORTABLECRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('C', PortableTags.WORKBENCH)
                .input('S', PortableTags.STICKS)
                .pattern(" C")
                .pattern("S ")
                .offerTo(exporter, PortableCrafting.asResource("portable_crafting_table_short"));
        ShapedRecipeJsonBuilder.create(PortableCrafting.PORTABLECRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('W', ItemTags.PLANKS)
                .input('S', PortableTags.STICKS)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .offerTo(exporter, PortableCrafting.asResource("portable_crafting_table"));
    }
}
