package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableCrafting;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class RecipeGen extends FabricRecipesProvider {
    public RecipeGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonFactory.create(PortableCrafting.PORTABLECRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('C', PortableTags.WORKBENCH)
                .input('S', PortableTags.STICKS)
                .pattern(" C")
                .pattern("S ")
                .offerTo(exporter, PortableCrafting.asResource("portable_crafting_table_short"));
        ShapedRecipeJsonFactory.create(PortableCrafting.PORTABLECRAFTING)
                .criterion("always", InventoryChangedCriterion.Conditions.items(ItemPredicate.ANY))
                .input('W', ItemTags.PLANKS)
                .input('S', PortableTags.STICKS)
                .pattern(" WW")
                .pattern(" WW")
                .pattern("S  ")
                .offerTo(exporter, PortableCrafting.asResource("portable_crafting_table"));
    }
}
