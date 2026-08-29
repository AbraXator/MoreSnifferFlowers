package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFRecipes {
     interface Serializer {
         DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
                DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MoreSnifferFlowers.MOD_ID);

         DeferredHolder<RecipeSerializer<?>, CropressingRecipe.CropressingSerializer> CROPRESSING =
                 RECIPE_SERIALIZERS.register("cropressing", CropressingRecipe.CropressingSerializer::new);

         DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RebrewedTippedArrowRecipe>> REBREWED_TIPPED_ARROW =
                 RECIPE_SERIALIZERS.register("rebrewed_tipped_arrow", () -> new SimpleCraftingRecipeSerializer<>(RebrewedTippedArrowRecipe::new));
    }

     interface Types {
         DeferredRegister<RecipeType<?>> RECIPE_TYPES =
                DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MoreSnifferFlowers.MOD_ID);

         DeferredHolder<RecipeType<?>, RecipeType<CropressingRecipe>> CROPRESSING = RECIPE_TYPES.register("cropressing", () -> RecipeType.simple(MoreSnifferFlowers.loc("cropressing")));
    }
}
