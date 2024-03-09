package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmithingRecipe;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> future) {
        super(pOutput, future);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        trimSmithing(pRecipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()) + "_smithing_trim"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get(), 2).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()).define('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).unlockedBy("has_aroma_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get())).save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYESPRIA.get()).pattern("AA").pattern("BA").define('A', Items.PAPER).define('B', ModBlocks.CAULORFLOWER.get().asItem()).unlockedBy("has_caulorflower", has(ModBlocks.CAULORFLOWER.get())).save(pRecipeOutput);

    }
}
