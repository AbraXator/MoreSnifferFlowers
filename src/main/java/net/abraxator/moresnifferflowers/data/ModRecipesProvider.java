package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter pRecipeOutput) {
     //   offerSmithingTrimRecipe(pRecipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE, MoreSnifferFlowers.loc(getItemPath(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE) + "_smithing_trim"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE).pattern("ABA").pattern("ACA").pattern("AAA").input('A', Items.DIAMOND).input('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE).input('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).criterion("has_aroma_trim_template", conditionsFromItem(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE)).offerTo(pRecipeOutput);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DYESPRIA).pattern("AA").pattern("BA").input('A', Items.PAPER).input('B', ModBlocks.CAULORFLOWER.asItem()).criterion("has_caulorflower", conditionsFromItem(ModBlocks.CAULORFLOWER)).offerTo(pRecipeOutput);

    }
    public static void registerModRecipes() {
        MoreSnifferFlowers.LOGGER.info("Registering Recipes for" + MoreSnifferFlowers.MOD_ID);

    }
}
