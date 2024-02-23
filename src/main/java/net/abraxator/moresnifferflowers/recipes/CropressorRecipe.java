package net.abraxator.moresnifferflowers.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.CompressionDecoder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CropressorRecipe implements Recipe<Container> {
    public final NonNullList<Ingredient> ingredients;
    public final ItemStack result;

    public CropressorRecipe(NonNullList<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        List<ItemStack> list = new ArrayList<>();
        int l = 0;

        for(int i = 0; i < pContainer.getContainerSize(); i++) {
            list.add(pContainer.getItem(i));
            l++;
        }

        return l == ingredients.size() && RecipeMatcher.findMatches(list, ingredients) != null;
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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CROPRESSOR.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CROPRESSOR.get();
    }

    public static class Serializer implements RecipeSerializer<CropressorRecipe> {
        public static final Codec<CropressorRecipe> CODEC = RecordCodecBuilder.create(
                cropressorRecipeInstance -> cropressorRecipeInstance.group(
                        Ingredient.CODEC_NONEMPTY
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(ingredients1 -> {
                                            Ingredient[] ingredients2 = ingredients1.toArray(Ingredient[]::new);
                                            if(ingredients2.length == 0) {
                                                return DataResult.error(() -> "No ingredients for Cropressor recipe!");
                                            } else {
                                                return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients2));
                                            }
                                        },
                                        DataResult::success
                                ).forGetter(o -> o.ingredients),
                        ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(o -> o.result)
                ).apply(cropressorRecipeInstance, CropressorRecipe::new)
        );

        @Override
        public Codec<CropressorRecipe> codec() {
            return null;
        }

        @Override
        public CropressorRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            var l = pBuffer.readVarInt();
            var list = NonNullList.withSize(l, Ingredient.EMPTY);

            for(int i = 0; i < l; i++) {
                list.set(i, Ingredient.fromNetwork(pBuffer));
            }

            return new CropressorRecipe(list, pBuffer.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CropressorRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            pRecipe.ingredients.forEach(ingredient -> {
                ingredient.toNetwork(pBuffer);
            });
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
