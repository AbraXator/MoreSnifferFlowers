package net.abraxator.moresnifferflowers.datagen.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
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
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.GLASS)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);

        //threeByThreePacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER_BLOCK.get(), ModItems.AMBER_SHARD.get());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.AMBER_MOSAIC.get(), MSFItems.AMBER_SHARD.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.AMBER_MOSAIC_SLAB.get(), MSFBlocks.AMBER_MOSAIC.get());
        stairBuilder(MSFBlocks.AMBER_MOSAIC_STAIRS, Ingredient.of(MSFBlocks.AMBER_MOSAIC))
                .unlockedBy("has_amber_mosaic", has(MSFBlocks.AMBER_MOSAIC))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.AMBER_MOSAIC_WALL.get(), MSFBlocks.AMBER_MOSAIC.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CHISELED_AMBER.get(), MSFBlocks.AMBER_MOSAIC_SLAB.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CHISELED_AMBER_SLAB.get(), MSFBlocks.CHISELED_AMBER.get());
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MSFBlocks.AMBER_MOSAIC.get()), RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CRACKED_AMBER.get().asItem(), 0.1F, 200)
                .unlockedBy("has_amber_mosaic", has(MSFBlocks.AMBER_MOSAIC))
                .save(recipeOutput);

        //threeByThreePacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GARNET_BLOCK.get(), ModItems.GARNET_SHARD.get());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.GARNET_MOSAIC.get(), MSFItems.GARNET_SHARD.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.GARNET_MOSAIC_SLAB.get(), MSFBlocks.GARNET_MOSAIC.get());
        stairBuilder(MSFBlocks.GARNET_MOSAIC_STAIRS, Ingredient.of(MSFBlocks.GARNET_MOSAIC))
                .unlockedBy("has_garnet_mosaic", has(MSFBlocks.GARNET_MOSAIC))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.GARNET_MOSAIC_WALL.get(), MSFBlocks.GARNET_MOSAIC.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CHISELED_GARNET.get(), MSFBlocks.GARNET_MOSAIC_SLAB.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CHISELED_GARNET_SLAB.get(), MSFBlocks.CHISELED_GARNET.get());
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MSFBlocks.GARNET_MOSAIC.get()), RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CRACKED_GARNET.get().asItem(), 0.1F, 200)
                .unlockedBy("has_garnet_mosaic", has(MSFBlocks.GARNET_MOSAIC))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MSFItems.CROPRESSOR.get())
                .requires(MSFItems.TUBE_PIECE.get())
                .requires(MSFItems.SCRAP_PIECE.get())
                .requires(MSFItems.ENGINE_PIECE.get())
                .requires(MSFItems.PRESS_PIECE.get())
                .requires(MSFItems.BELT_PIECE.get())
                .unlockedBy("has_cropressor_piece", has(MSFTags.ModItemTags.CROPRESSOR_PIECES))
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

        partsRecycling(recipeOutput, MSFItems.BELT_PIECE.get(), Items.LEATHER, 8);
        partsRecycling(recipeOutput, MSFItems.SCRAP_PIECE.get(), Items.COPPER_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.ENGINE_PIECE.get(), Items.GOLD_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.TUBE_PIECE.get(), Items.IRON_INGOT, 8);
        partsRecycling(recipeOutput, MSFItems.PRESS_PIECE.get(), Items.NETHERITE_SCRAP, 1);
        partsRecycling(recipeOutput, MSFItems.BROKEN_REBREWING_STAND.get(), MSFItems.CROPRESSED_NETHERWART.get(), 4);

        partsRecycling(recipeOutput, MSFItems.CROPRESSED_BEETROOT.get(), Items.BEETROOT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_CARROT.get(), Items.CARROT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_POTATO.get(), Items.POTATO, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_WHEAT.get(), Items.WHEAT, 16);
        partsRecycling(recipeOutput, MSFItems.CROPRESSED_NETHERWART.get(), Items.NETHER_WART, 16);



        planksFromLogs(recipeOutput, MSFBlocks.CORRUPTED_PLANKS, MSFTags.ModItemTags.CORRUPTED_LOGS, 4);
        woodFromLogs(recipeOutput, MSFBlocks.CORRUPTED_WOOD, MSFBlocks.CORRUPTED_LOG);
        woodFromLogs(recipeOutput, MSFBlocks.STRIPPED_CORRUPTED_WOOD, MSFBlocks.STRIPPED_CORRUPTED_LOG);
        stairBuilder(MSFBlocks.CORRUPTED_STAIRS, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.CORRUPTED_SLAB, MSFBlocks.CORRUPTED_PLANKS);
        fenceBuilder(MSFBlocks.CORRUPTED_FENCE, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        fenceGateBuilder(MSFBlocks.CORRUPTED_FENCE_GATE, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        doorBuilder(MSFBlocks.CORRUPTED_DOOR, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        trapdoorBuilder(MSFBlocks.CORRUPTED_TRAPDOOR, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        pressurePlate(recipeOutput, MSFBlocks.CORRUPTED_PRESSURE_PLATE, MSFBlocks.CORRUPTED_PLANKS);
        buttonBuilder(MSFBlocks.CORRUPTED_BUTTON, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        woodenBoat(recipeOutput, MSFItems.CORRUPTED_BOAT.get(), MSFBlocks.CORRUPTED_PLANKS.get());
        chestBoat(recipeOutput, MSFItems.CORRUPTED_CHEST_BOAT.get(), MSFItems.CORRUPTED_BOAT.get());
        signBuilder(MSFBlocks.CORRUPTED_SIGN, Ingredient.of(MSFBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(MSFBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        hangingSign(recipeOutput, MSFItems.CORRUPTED_HANGING_SIGN.get(), MSFBlocks.CORRUPTED_PLANKS.get());

        planksFromLogs(recipeOutput, MSFBlocks.VIVICUS_PLANKS, MSFTags.ModItemTags.VIVICUS_LOGS, 4);
        woodFromLogs(recipeOutput, MSFBlocks.VIVICUS_WOOD, MSFBlocks.VIVICUS_LOG);
        woodFromLogs(recipeOutput, MSFBlocks.STRIPPED_VIVICUS_WOOD, MSFBlocks.STRIPPED_VIVICUS_LOG);
        stairBuilder(MSFBlocks.VIVICUS_STAIRS, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, MSFBlocks.VIVICUS_SLAB, MSFBlocks.VIVICUS_PLANKS);
        fenceBuilder(MSFBlocks.VIVICUS_FENCE, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        fenceGateBuilder(MSFBlocks.VIVICUS_FENCE_GATE, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        doorBuilder(MSFBlocks.VIVICUS_DOOR, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        trapdoorBuilder(MSFBlocks.VIVICUS_TRAPDOOR, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        pressurePlate(recipeOutput, MSFBlocks.VIVICUS_PRESSURE_PLATE, MSFBlocks.VIVICUS_PLANKS);
        buttonBuilder(MSFBlocks.VIVICUS_BUTTON, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        woodenBoat(recipeOutput, MSFItems.VIVICUS_BOAT.get(), MSFBlocks.VIVICUS_PLANKS.get());
        chestBoat(recipeOutput, MSFItems.VIVICUS_CHEST_BOAT.get(), MSFItems.VIVICUS_BOAT.get());
        signBuilder(MSFBlocks.VIVICUS_SIGN, Ingredient.of(MSFBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_vivicus_planks", has(MSFBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
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
                .requires(Ingredient.of(MSFTags.ModItemTags.BLOCK_PATTERNS), 1)
                .requires(Ingredient.of(MSFItems.DYESPRIA.get()), 1)
                .unlockedBy("has_block_pattern", has(MSFTags.ModItemTags.BLOCK_PATTERNS))
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
                .requires(Ingredient.of(Items.BOOK), 1)
                .requires(Ingredient.of(MSFItems.CROPRESSED_BEETROOT.get()), 1)
                .unlockedBy("cropressed_beetroot", has(MSFItems.CROPRESSED_BEETROOT.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.TORCHFLOWER)
                .requires(Ingredient.of(MSFBlocks.TORCHFLOWER_AFLAME.asItem()), 1)
                .requires(Ingredient.of(Items.BONE_MEAL), 3)
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



        ModCustomRecipeProvider.createRecipes(recipeOutput);
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
                .define('A', Items.DIAMOND)
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

}
