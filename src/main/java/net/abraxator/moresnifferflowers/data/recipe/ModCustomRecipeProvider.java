package net.abraxator.moresnifferflowers.data.recipe;

import net.abraxator.moresnifferflowers.data.recipe.builder.CropressingRecipeBuilder;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Consumer;

public class ModCustomRecipeProvider extends RecipeProvider {

    public ModCustomRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    public static void createRecipes(Consumer<FinishedRecipe> pWriter) {
        createCropressing(pWriter, ModItems.CROPRESSED_CARROT.get(), Items.CARROT);
        createCropressing(pWriter, ModItems.CROPRESSED_POTATO.get(), Items.POTATO);
        createCropressing(pWriter, ModItems.CROPRESSED_NETHERWART.get(), Items.NETHER_WART);
        createCropressing(pWriter, ModItems.CROPRESSED_BEETROOT.get(), Items.BEETROOT);
        createCropressing(pWriter, ModItems.CROPRESSED_WHEAT.get(), Items.WHEAT);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

    }

    public static void createCropressing(Consumer<FinishedRecipe> writer, ItemLike result, ItemLike crop) {
        new CropressingRecipeBuilder(result).requiresCrop(crop.asItem()).unlockedBy("has_cropressor", has(ModBlocks.CROPRESSOR_OUT.get())).save(writer);
    }
}
