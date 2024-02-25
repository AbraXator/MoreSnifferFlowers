package net.abraxator.moresnifferflowers.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CropressorRecipe implements Recipe<Inventory> {
    public final DefaultedList<Ingredient> ingredients;
    public final ItemStack result;

    public CropressorRecipe(DefaultedList<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(Inventory pContainer, World pLevel) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        List<ItemStack> list = new ArrayList<>();
        int l = 0;

        for(int i = 0; i < pContainer.size(); i++) {
            list.add(pContainer.getStack(i));
            l++;
        }

        return l == ingredients.size() && recipeMatcher.match(this, ((IntList) null));
    }

    @Override
    public ItemStack craft(Inventory p_44001_, DynamicRegistryManager p_267165_) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager p_267052_) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CROPRESSOR;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CROPRESSOR;
    }

    public static class Serializer implements RecipeSerializer<CropressorRecipe> {
        public static final Codec<CropressorRecipe> CODEC = RecordCodecBuilder.create(
                cropressorRecipeInstance -> cropressorRecipeInstance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(ingredients1 -> {
                                            Ingredient[] ingredients2 = ingredients1.toArray(Ingredient[]::new);
                                            if(ingredients2.length == 0) {
                                                return DataResult.error(() -> "No ingredients for Cropressor recipe!");
                                            } else {
                                                return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
                                            }
                                        },
                                        DataResult::success
                                ).forGetter(o -> o.ingredients),
                        ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter(o -> o.result)
                ).apply(cropressorRecipeInstance, CropressorRecipe::new)
        );

        @Override
        public Codec<CropressorRecipe> codec() {
            return null;
        }

        @Override
        public CropressorRecipe read(PacketByteBuf pBuffer) {
            var l = pBuffer.readVarInt();
            var list = DefaultedList.ofSize(l, Ingredient.EMPTY);

            for(int i = 0; i < l; i++) {
                list.set(i, Ingredient.fromPacket(pBuffer));
            }

            return new CropressorRecipe(list, pBuffer.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, CropressorRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            recipe.ingredients.forEach(ingredient -> {
                ingredient.write(buf);
            });
            buf.writeItemStack(recipe.result);
        }
    }
}
