package net.abraxator.moresnifferflowers.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CropressorRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    public final NonNullList<Ingredient> ingredients;
    public final ItemStack result;

    public CropressorRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.id = id;
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
    public ResourceLocation getId() {
        return id;
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
        @Override
        public CropressorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            var list = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> nonNullList = NonNullList.create();
            ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            for(JsonElement jsonElement : list) {
                Ingredient ingredient = Ingredient.fromJson(jsonElement);
                if(!ingredient.isEmpty()) {
                    nonNullList.add(ingredient);
                }
            }

            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }

            return new CropressorRecipe(pRecipeId, nonNullList, itemStack);
        }

        @Override
        public @Nullable CropressorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            var l = pBuffer.readVarInt();
            var list = NonNullList.withSize(l, Ingredient.EMPTY);

            for(int i = 0; i < l; i++) {
                list.set(i, Ingredient.fromNetwork(pBuffer));
            }

            return new CropressorRecipe(pRecipeId, list, pBuffer.readItem());
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
