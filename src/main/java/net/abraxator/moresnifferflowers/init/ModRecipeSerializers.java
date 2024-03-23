package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.abraxator.moresnifferflowers.recipes.serializers.CropressingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CropressingRecipe>> CROPRESSING = RECIPE_SERIALIZERS.register("cropressing", CropressingSerializer::new);
}
