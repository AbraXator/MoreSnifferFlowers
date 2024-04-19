package net.abraxator.moresnifferflowers.data.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        trimSmithing(pWriter, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
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

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRACTION_BOTTLE.get()).pattern(" A ").pattern("BAB").pattern(" B ").define('A', Items.AMETHYST_SHARD).define('B', Items.GLASS).unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD)).save(pWriter);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMBUSH_BANNER_PATTERN.get()).requires(Items.PAPER).requires(ModItems.AMBER_SHARD.get()).unlockedBy("has_amber_shard", has(ModItems.AMBER_SHARD.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AMBER.get()).pattern("AA").pattern("AA").define('A', ModItems.AMBER_SHARD.get()).unlockedBy("has_amber_shard",has(ModItems.AMBER_SHARD.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CROPRESSOR.get()).pattern("AB").pattern("CD").define('A', ModItems.CROPRESSOR_SCRAP.get()).define('B', ModItems.CROPRESSOR_ENGINE.get()).define('C', ModItems.CROPRESSOR_TUBE.get()).define('D', ModItems.CROPRESSOR_BELT.get()).unlockedBy("has_cropressor_piece", has(ModTags.ModItemTags.CROPRESSOR_PIECES)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REBREWING_STAND.get()).pattern("   ").pattern(" A ").pattern("BCB").define('A', ModItems.CROPRESSED_NETHERWART.get()).define('B', ModItems.BROKEN_REBREWING_STAND.get()).define('C', ModItems.CROPRESSOR_TUBE.get()).unlockedBy("has_broken_rebrewing_stand", has(ModItems.BROKEN_REBREWING_STAND.get())).save(pWriter);

        ModCustomRecipeProvider.createRecipes(pWriter);
    }
}
