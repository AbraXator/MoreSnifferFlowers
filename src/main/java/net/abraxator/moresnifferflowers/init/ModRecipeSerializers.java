package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.abraxator.moresnifferflowers.recipes.serializers.CropressingSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, CropressingSerializer> CROPRESSING = RECIPE_SERIALIZERS.register("cropressing", CropressingSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RebrewedTippedArrowRecipe>> REBREWED_TIPPED_ARROW = RECIPE_SERIALIZERS.register("rebrewed_tipped_arrow", () -> new SimpleCraftingRecipeSerializer<RebrewedTippedArrowRecipe>(RebrewedTippedArrowRecipe::new));
}
