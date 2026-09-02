package net.abraxator.moresnifferflowers.datagen.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.datagen.model.MSFBlockFamilies;
import net.abraxator.moresnifferflowers.datagen.recipe.builder.CropressingRecipeBuilder;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class MSFRecipesProvider extends RecipeProvider {
    public MSFRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        generateMsfBlockFamilies(recipeOutput);
        trimSmithing(recipeOutput, MSFItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, MSFItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(MSFItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get())));

        trimCrafting(recipeOutput, MSFItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.AMBER_SHARD.get());
        trimCrafting(recipeOutput, MSFItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.GARNET_SHARD.get());
        trimCrafting(recipeOutput, MSFItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.CROPRESSED_NETHERWART.get());
        trimCrafting(recipeOutput, MSFItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.CROPRESSED_CARROT.get());
        trimCrafting(recipeOutput, MSFItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.CROPRESSED_POTATO.get());
        trimCrafting(recipeOutput, MSFItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.CROPRESSED_WHEAT.get());
        trimCrafting(recipeOutput, MSFItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MSFItems.CROPRESSED_BEETROOT.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSFItems.EXTRACTION_BOTTLE.get())
                .pattern(" A ")
                .pattern("BAB")
                .pattern(" B ")
                .define('A', net.minecraft.world.item.Items.AMETHYST_SHARD)
                .define('B', net.minecraft.world.item.Items.GLASS)
                .unlockedBy("has_amethyst", has(net.minecraft.world.item.Items.AMETHYST_SHARD))
                .save(recipeOutput);

        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.AMBER_MOSAIC.get(), MSFItems.AMBER_SHARD.get());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.GARNET_MOSAIC.get(), MSFItems.GARNET_SHARD.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFItems.CROPRESSOR.get())
                .requires(MSFItems.TUBE_PIECE.get())
                .requires(MSFItems.SCRAP_PIECE.get())
                .requires(MSFItems.ENGINE_PIECE.get())
                .requires(MSFItems.PRESS_PIECE.get())
                .requires(MSFItems.BELT_PIECE.get())
                .unlockedBy("has_cropressor_piece", has(MSFTags.ItemTags.CROPRESSOR_PIECES))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSFItems.REBREWING_STAND.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern("BCB")
                .define('A', MSFItems.CROPRESSED_NETHERWART.get())
                .define('B', MSFItems.BROKEN_REBREWING_STAND.get())
                .define('C', MSFItems.TUBE_PIECE.get())
                .unlockedBy("has_broken_rebrewing_stand", has(MSFItems.BROKEN_REBREWING_STAND.get()))
                .save(recipeOutput);

        partsRecycling(recipeOutput, MSFItems.BELT_PIECE.get(), net.minecraft.world.item.Items.LEATHER, 8);
        partsRecycling(recipeOutput, MSFItems.SCRAP_PIECE.get(), net.minecraft.world.item.Items.COPPER_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.ENGINE_PIECE.get(), net.minecraft.world.item.Items.GOLD_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.TUBE_PIECE.get(), net.minecraft.world.item.Items.IRON_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.PRESS_PIECE.get(), net.minecraft.world.item.Items.NETHERITE_SCRAP, 1);
        partsRecycling(recipeOutput, MSFItems.BROKEN_REBREWING_STAND.get(), MSFItems.CROPRESSED_NETHERWART.get(), 4);

        partsRecycling(recipeOutput, MSFItems.CROPRESSED_BEETROOT.get(), net.minecraft.world.item.Items.BEETROOT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_CARROT.get(), net.minecraft.world.item.Items.CARROT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_POTATO.get(), net.minecraft.world.item.Items.POTATO, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_WHEAT.get(), net.minecraft.world.item.Items.WHEAT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_NETHERWART.get(), net.minecraft.world.item.Items.NETHER_WART, 16);


        planksFromLogs(recipeOutput, MSFBlocks.CORRUPTED_PLANKS, MSFTags.ItemTags.CORRUPTED_LOGS, 4);
        woodFromLogs(recipeOutput, MSFBlocks.CORRUPTED_WOOD, MSFBlocks.CORRUPTED_LOG);
        woodFromLogs(recipeOutput, MSFBlocks.STRIPPED_CORRUPTED_WOOD, MSFBlocks.STRIPPED_CORRUPTED_LOG);
        woodenBoat(recipeOutput, MSFItems.CORRUPTED_BOAT.get(), MSFBlocks.CORRUPTED_PLANKS.get());
        chestBoat(recipeOutput, MSFItems.CORRUPTED_CHEST_BOAT.get(), MSFItems.CORRUPTED_BOAT.get());
        hangingSign(recipeOutput, MSFItems.CORRUPTED_HANGING_SIGN.get(), MSFBlocks.CORRUPTED_PLANKS.get());

        planksFromLogs(recipeOutput, MSFBlocks.VIVICUS_PLANKS, MSFTags.ItemTags.VIVICUS_LOGS, 4);
        woodFromLogs(recipeOutput, MSFBlocks.VIVICUS_WOOD, MSFBlocks.VIVICUS_LOG);
        woodFromLogs(recipeOutput, MSFBlocks.STRIPPED_VIVICUS_WOOD, MSFBlocks.STRIPPED_VIVICUS_LOG);
        woodenBoat(recipeOutput, MSFItems.VIVICUS_BOAT.get(), MSFBlocks.VIVICUS_PLANKS.get());
        chestBoat(recipeOutput, MSFItems.VIVICUS_CHEST_BOAT.get(), MSFItems.VIVICUS_BOAT.get());
        hangingSign(recipeOutput, MSFItems.VIVICUS_HANGING_SIGN.get(), MSFBlocks.VIVICUS_PLANKS.get());
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSFItems.VIVICUS_ANTIDOTE, 1)
                        .pattern(" AB")
                        .pattern("ACA")
                        .pattern("DA ")
                        .define('A', Tags.Items.GLASS_BLOCKS_COLORLESS)
                        .define('B', MSFItems.JAR_OF_ACID)
                        .define('C', MSFItems.CORRUPTED_BOBLING_CORE)
                        .define('D', Tags.Items.INGOTS_IRON)
                        .unlockedBy("has_jar_of_acid", has(MSFItems.JAR_OF_ACID))
                        .save(recipeOutput);

        SpecialRecipeBuilder.special(RebrewedTippedArrowRecipe::new).save(recipeOutput, "rebrewed_tipped_arrow");


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFBlocks.DRIPSALT.get().asItem())
                .requires(MSFItems.SALTY_SPICE.get(), 5)
                .unlockedBy("has_salty_spice", has(MSFItems.SALTY_SPICE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFItems.PATTERNSPRIA.get())
                .requires(Ingredient.of(MSFTags.ItemTags.BLOCK_PATTERNS), 1)
                .requires(Ingredient.of(MSFItems.DYESPRIA.get()), 1)
                .unlockedBy("has_block_pattern", has(MSFTags.ItemTags.BLOCK_PATTERNS))
                .save(recipeOutput);

        partsRecycling(recipeOutput, MSFBlocks.DRIPSALT.get().asItem(), MSFItems.SALTY_SPICE.get(), 5);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSFBlocks.BEROOT_CAULDRON.get().asItem(), 1)
                .pattern("A A")
                .pattern("ABA")
                .pattern("CDC")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('B', MSFItems.CROPRESSED_BEETROOT.get())
                .define('C', MSFItems.FLAVORFUL_ROOTS.get())
                .define('D', MSFItems.SCRAP_PIECE.get())
                .unlockedBy("has_flavorful_roots", has(MSFItems.FLAVORFUL_ROOTS.get()))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFBlocks.TORCHFLAME.get().asItem())
                .requires(Ingredient.of(MSFItems.FIERY_SPICE.get()), 4)
                .unlockedBy("has_fiery_spice", has(MSFItems.FIERY_SPICE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFItems.BEROOT_COOK_BOOK.get())
                .requires(Ingredient.of(net.minecraft.world.item.Items.BOOK), 1)
                .requires(Ingredient.of(MSFItems.CROPRESSED_BEETROOT.get()), 1)
                .unlockedBy("cropressed_beetroot", has(MSFItems.CROPRESSED_BEETROOT.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.TORCHFLOWER)
                .requires(Ingredient.of(MSFBlocks.TORCHFLOWER_AFLAME.asItem()), 1)
                .requires(Ingredient.of(net.minecraft.world.item.Items.BONE_MEAL), 3)
                .unlockedBy("has_torchflower_aflame", has(MSFBlocks.TORCHFLOWER_AFLAME.asItem()))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSFItems.MUSIC_DISC_BOBLING.get(), 1)
                .pattern(" A ")
                .pattern("AXA")
                .pattern(" A ")
                .define('A', MSFItems.DISC_FRAGMENT_BOBLING.get())
                .define('X', MSFItems.CORRUPTED_BOBLING_CORE.get())
                .unlockedBy(getHasName(MSFItems.DISC_FRAGMENT_BOBLING.get()) ,has(MSFItems.DISC_FRAGMENT_BOBLING.get()))
                .save(recipeOutput);



        createCropressing(recipeOutput, MSFItems.CROPRESSED_CARROT.get(), net.minecraft.world.item.Items.CARROT);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_POTATO.get(), net.minecraft.world.item.Items.POTATO);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_NETHERWART.get(), net.minecraft.world.item.Items.NETHER_WART);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_BEETROOT.get(), net.minecraft.world.item.Items.BEETROOT);
        createCropressing(recipeOutput, MSFItems.CROPRESSED_WHEAT.get(), net.minecraft.world.item.Items.WHEAT);
    }

    protected void generateMsfBlockFamilies(RecipeOutput enabledFeatures) {
        MSFBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(enabledFeatures, family, FeatureFlags.DEFAULT_FLAGS));
    }


    private void trimCrafting(RecipeOutput recipeOutput, ItemLike trim, TagKey<Item> ingredient) {
        trimCrafting(recipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(RecipeOutput recipeOutput, ItemLike trim, ItemLike ingredient) {
        trimCrafting(recipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(RecipeOutput recipeOutput, ItemLike trim, Ingredient ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, trim, 2)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .define('A', net.minecraft.world.item.Items.DIAMOND)
                .define('B', trim)
                .define('C', ingredient)
                .unlockedBy("has_" + getItemName(trim) + "_trim_template", has(MSFItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, MoreSnifferFlowers.loc(getItemName(trim) + "_from_trim_crafting"));
    }

    private void partsRecycling(RecipeOutput recipeOutput, ItemLike part, Item result, int count) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
                .requires(part)
                .unlockedBy("has_" + getItemName(part), has(part))
                .save(recipeOutput, MoreSnifferFlowers.loc(getItemName(result) + "_from_part_recycling"));
    }

    public static void createCropressing(RecipeOutput recipeOutput, ItemLike result, ItemLike crop) {
        new CropressingRecipeBuilder(result).requiresCrop(crop.asItem()).unlockedBy("has_cropressor", has(MSFBlocks.CROPRESSOR_OUT.get())).save(recipeOutput, result.asItem().toString()+"_from_cropressing");
    }


}
