package net.abraxator.moresnifferflowers.recipes.serializers;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CropressingSerializer implements RecipeSerializer<CropressingRecipe> {
    @Override
    public MapCodec<CropressingRecipe> codec() {
        return CropressingRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CropressingRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }
    
    private CropressingRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        return new CropressingRecipe(Ingredient.CONTENTS_STREAM_CODEC.decode(buf), buf.readInt(), ItemStack.STREAM_CODEC.decode(buf));
    } 
    
    private void toNetwork(RegistryFriendlyByteBuf buf, CropressingRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient());
        buf.writeInt(recipe.count());
        ItemStack.STREAM_CODEC.encode(buf, recipe.result());
    }
}