package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<Recipe<?>>> CROPRESSOR = register("cropressor");

    static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, RecipeType<Recipe<?>>> register(final String id) {
        return RECIPE_TYPES.register(id, () -> RecipeType.simple(MoreSnifferFlowers.loc(id)));
    }
}
