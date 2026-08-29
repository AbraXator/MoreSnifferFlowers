package net.abraxator.moresnifferflowers.datagen.recipe;

import net.abraxator.moresnifferflowers.datagen.recipe.builder.CropressingRecipeBuilder;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModCustomRecipeProvider extends RecipeProvider {

    public ModCustomRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

    }

    public static void createRecipes(RecipeOutput recipeOutput) {
        createCropressing(recipeOutput, MSFItems.CROPRESSED_CARROT.get(), Items.CARROT);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_POTATO.get(), Items.POTATO);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_NETHERWART.get(), Items.NETHER_WART);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_BEETROOT.get(), Items.BEETROOT);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_WHEAT.get(), Items.WHEAT);
    }

    public static void createCropressing(RecipeOutput recipeOutput, ItemLike result, ItemLike crop) {
        new CropressingRecipeBuilder(result).requiresCrop(crop.asItem()).unlockedBy("has_cropressor", has(MSFBlocks.CROPRESSOR_OUT.get())).save(recipeOutput, result.asItem().toString()+"_from_cropressing");
    }
}
