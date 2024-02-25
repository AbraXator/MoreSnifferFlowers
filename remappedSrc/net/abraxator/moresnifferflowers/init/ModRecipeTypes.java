package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(RegistryKeys.RECIPE_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CropressorRecipe>> CROPRESSOR = register("cropressor");

    static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, RecipeType<T>> register(final String id) {
        return RECIPE_TYPES.register(id, () -> {
            return new RecipeType<T>() {
                @Override
                public String toString() {
                    return id;
                }
            };
        });
    }
}
