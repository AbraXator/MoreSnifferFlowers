package net.abraxator.moresnifferflowers.data.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pRecipeOutput) {
        trimSmithing(pRecipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pRecipeOutput, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pRecipeOutput, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pRecipeOutput, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pRecipeOutput, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pRecipeOutput, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get())));

        trimCrafting(pRecipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT);
        trimCrafting(pRecipeOutput, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_NETHERWART.get());
        trimCrafting(pRecipeOutput, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_CARROT.get());
        trimCrafting(pRecipeOutput, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_POTATO.get());
        trimCrafting(pRecipeOutput, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_WHEAT.get());
        trimCrafting(pRecipeOutput, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_BEETROOT.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRACTION_BOTTLE.get())
                .pattern(" A ")
                .pattern("BAB")
                .pattern(" B ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.GLASS)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMBUSH_BANNER_PATTERN.get())
                .requires(Items.PAPER)
                .requires(ModItems.AMBER_SHARD.get())
                .unlockedBy("has_amber_shard", has(ModItems.AMBER_SHARD.get()))
                .save(pRecipeOutput);

        twoByTwoPacker(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER.get(), ModItems.AMBER_SHARD.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CROPRESSOR.get())
                .requires(ModItems.TUBE_PIECE.get())
                .requires(ModItems.SCRAP_PIECE.get())
                .requires(ModItems.ENGINE_PIECE.get())
                .requires(ModItems.PRESS_PIECE.get())
                .requires(ModItems.BELT_PIECE.get())
                .unlockedBy("has_cropressor_piece", has(ModTags.ModItemTags.CROPRESSOR_PIECES))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REBREWING_STAND.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern("BCB")
                .define('A', ModItems.CROPRESSED_NETHERWART.get())
                .define('B', ModItems.BROKEN_REBREWING_STAND.get())
                .define('C', ModItems.TUBE_PIECE.get())
                .unlockedBy("has_broken_rebrewing_stand", has(ModItems.BROKEN_REBREWING_STAND.get()))
                .save(pRecipeOutput);

        partsRecycling(pRecipeOutput, ModItems.BELT_PIECE.get(), Items.LEATHER, 8);
        partsRecycling(pRecipeOutput, ModItems.SCRAP_PIECE.get(), Items.COPPER_INGOT, 8);
        partsRecycling(pRecipeOutput, ModItems.ENGINE_PIECE.get(), Items.GOLD_INGOT, 8);
        partsRecycling(pRecipeOutput, ModItems.TUBE_PIECE.get(), Items.IRON_INGOT, 8);
        partsRecycling(pRecipeOutput, ModItems.PRESS_PIECE.get(), Items.NETHERITE_SCRAP, 1);
        partsRecycling(pRecipeOutput, ModItems.BROKEN_REBREWING_STAND.get(), ModItems.CROPRESSED_NETHERWART.get(), 4);

        SpecialRecipeBuilder.special(ModRecipeSerializers.REBREWED_TIPPED_ARROW.get()).save(pRecipeOutput, "rebrewed_tipped_arrow");

        ModCustomRecipeProvider.createRecipes(pRecipeOutput);
    }

    private void trimCrafting(Consumer<FinishedRecipe> pRecipeOutput, ItemLike trim, TagKey<Item> ingredient) {
        trimCrafting(pRecipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(Consumer<FinishedRecipe> pRecipeOutput, ItemLike trim, ItemLike ingredient) {
        trimCrafting(pRecipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(Consumer<FinishedRecipe> pRecipeOutput, ItemLike trim, Ingredient ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, trim, 2)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .define('A', Items.DIAMOND)
                .define('B', trim)
                .define('C', ingredient)
                .unlockedBy("has_" + getItemName(trim) + "_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(pRecipeOutput, MoreSnifferFlowers.loc(getItemName(trim) + "_from_trim_crafting"));
    }

    private void partsRecycling(Consumer<FinishedRecipe> pRecipeOutput, Item part, Item result, int count) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
                .requires(part)
                .unlockedBy("has_" + getItemName(part), has(ModItems.BELT_PIECE.get()))
                .save(pRecipeOutput, MoreSnifferFlowers.loc(getItemName(result) + "_from_part_recycling"));
    }
}
