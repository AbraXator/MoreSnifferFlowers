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
        trimSmithing(pWriter, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        trimSmithing(pWriter, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get()) + "_smithing_trim"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).pattern("ABA").pattern("ACA").pattern("AAA").define('A', Items.DIAMOND).define('B', ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).define('C', ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).unlockedBy("has_aroma_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYESPRIA.get()).pattern("AA").pattern("BA").define('A', Items.PAPER).define('B', ModBlocks.CAULORFLOWER.get().asItem()).unlockedBy("has_caulorflower", has(ModBlocks.CAULORFLOWER.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMBUSH_BANNER_PATTERN.get()).requires(Items.PAPER).requires(ModItems.AMBER_SHARD.get()).unlockedBy("has_amber_shard", has(ModItems.AMBER_SHARD.get())).save(pWriter);
        ModCustomRecipeProvider.createRecipes(pWriter);
    }
}
