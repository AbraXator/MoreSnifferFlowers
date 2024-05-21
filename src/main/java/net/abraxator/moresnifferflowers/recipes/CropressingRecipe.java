package net.abraxator.moresnifferflowers.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record CropressingRecipe(Ingredient ingredient, int count, ItemStack result) implements Recipe<Container> {
    public static final MapCodec<CropressingRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CropressingRecipe::ingredient),
                Codec.INT.fieldOf("count").forGetter(CropressingRecipe::count),
                ItemStack.CODEC.fieldOf("result").forGetter(CropressingRecipe::result)
        ).apply(builder, CropressingRecipe::new);
    });
    
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack itemStack = pContainer.getItem(0);
        return itemStack.getCount() == count && ingredient.test(itemStack.copyWithCount(1));
    }

    @Override
    public ItemStack assemble(Container pCraftingContainer, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result;
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
