package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.recipe.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<?> CROPRESSOR = RecipeSerializer.register("cropressor", new CropressorRecipe.Serializer());
}
