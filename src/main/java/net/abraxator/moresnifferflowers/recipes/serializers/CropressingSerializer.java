package net.abraxator.moresnifferflowers.recipes.serializers;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import org.jetbrains.annotations.Nullable;

public class CropressingSerializer implements RecipeSerializer<CropressingRecipe> {
    @Override
    public Codec<CropressingRecipe> codec() {
        return RecordCodecBuilder.create(instance -> 
                instance.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(CropressingRecipe::ingredient),
                        Codec.INT.fieldOf("count").forGetter(CropressingRecipe::count),
                        ItemStack.CODEC.fieldOf("result").forGetter(CropressingRecipe::result)
                ).apply(instance, CropressingRecipe::new)
        );
    }

    @Override
    public CropressingRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        return new CropressingRecipe(Ingredient.fromNetwork(pBuffer), pBuffer.readVarInt(), pBuffer.readItem());
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, CropressingRecipe pRecipe) {
        pRecipe.ingredient().toNetwork(pBuffer);
        pBuffer.writeVarInt(pRecipe.count());
        pBuffer.writeItem(pRecipe.result());
    }
}