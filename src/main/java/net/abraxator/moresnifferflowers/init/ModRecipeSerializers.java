package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MoreSnifferFlowers.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<CropressingRecipe>> CROPRESSING = RECIPE_SERIALIZERS.register("cropressing", CropressingSerializer::new);
}
