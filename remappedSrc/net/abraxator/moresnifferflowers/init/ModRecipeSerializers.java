package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(RegistryKeys.RECIPE_SERIALIZER, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, CropressorRecipe.Serializer> CROPRESSOR = RECIPE_SERIALIZERS.register("cropressor", CropressorRecipe.Serializer::new);
}
