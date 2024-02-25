package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.data.DataOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(DataOutput pOutput, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(pOutput, future);
    }

    @Override
    protected void generate(RecipeExporter pRecipeOutput) {
        offerSmithingTrimRecipe(pRecipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get(), MoreSnifferFlowers.loc(getItemPath(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()) + "_smithing_trim"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()).define('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).unlockedBy("has_aroma_trim_template", conditionsFromItem(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get())).save(pRecipeOutput);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DYESPRIA.get()).pattern("AA").pattern("BA").define('A', Items.PAPER).define('B', ModBlocks.CAULORFLOWER.get().asItem()).unlockedBy("has_caulorflower", conditionsFromItem(ModBlocks.CAULORFLOWER.get())).save(pRecipeOutput);

    }
}
