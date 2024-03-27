package net.abraxator.moresnifferflowers.recipes;

import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public record CropressingRecipe(ResourceLocation id, Ingredient ingredient, int count, ItemStack result) implements Recipe<Container> {
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack itemStack = pContainer.getItem(0);
        return itemStack.getCount() == count && ingredient.test(itemStack.copyWithCount(1));
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CROPRESSING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CROPRESSING.get();
    }
}
