package net.abraxator.moresnifferflowers.data.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        trimSmithing(pWriter, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
<<<<<<< HEAD
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CROPRESSED_POTATO.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).unlockedBy("has_aroma_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(pWriter);
=======
        trimSmithing(pWriter, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        trimSmithing(pWriter, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        trimSmithing(pWriter, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        trimSmithing(pWriter, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        trimSmithing(pWriter, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).unlockedBy("has_aroma_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModItems.CROPRESSED_NETHERWART.get()).unlockedBy("has_crop_smithing_templates", has(ModTags.ModItemTags.CROP_SMITHING_TEMPLATES)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModItems.CROPRESSED_CARROT.get()).unlockedBy("has_crop_smithing_templates", has(ModTags.ModItemTags.CROP_SMITHING_TEMPLATES)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModItems.CROPRESSED_POTATO.get()).unlockedBy("has_crop_smithing_templates", has(ModTags.ModItemTags.CROP_SMITHING_TEMPLATES)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModItems.CROPRESSED_WHEAT.get()).unlockedBy("has_crop_smithing_templates", has(ModTags.ModItemTags.CROP_SMITHING_TEMPLATES)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModItems.CROPRESSED_BEETROOT.get()).unlockedBy("has_crop_smithing_templates", has(ModTags.ModItemTags.CROP_SMITHING_TEMPLATES)).save(pWriter);

>>>>>>> a00c1bc047a9ed294aa16580f3aaf24b831b63be
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYESPRIA.get()).pattern("AA").pattern("BA").define('A', Items.PAPER).define('B', ModBlocks.CAULORFLOWER.get().asItem()).unlockedBy("has_caulorflower", has(ModBlocks.CAULORFLOWER.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMBUSH_BANNER_PATTERN.get()).requires(Items.PAPER).requires(ModItems.AMBER_SHARD.get()).unlockedBy("has_amber_shard", has(ModItems.AMBER_SHARD.get())).save(pWriter);
        ModCustomRecipeProvider.createRecipes(pWriter);
    }
}
