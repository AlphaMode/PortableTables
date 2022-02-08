package me.alphamode.portablecrafting.data;

import me.alphamode.portablecrafting.PortableCrafting;
import me.alphamode.portablecrafting.PortableTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;

import java.util.function.Consumer;

public class RecipeGen extends FabricRecipesProvider {
    public RecipeGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonFactory.create(PortableCrafting.PORTABLECRAFTING)
                .input('C', PortableTags.WORKBENCH)
                .input('S', PortableTags.STICKS)
                .pattern(" C")
                .pattern("S ")
                .offerTo(exporter);
    }
}
