package net.abraxator.moresnifferflowers.data.recipe;

import com.google.gson.JsonObject;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.recipes.RebrewedTippedArrowRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        trimSmithing(recipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(recipeOutput, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get())));

        trimCrafting(recipeOutput, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.AMBER_SHARD.get());
        trimCrafting(recipeOutput, ModItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.GARNET_SHARD.get());
        trimCrafting(recipeOutput, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_NETHERWART.get());
        trimCrafting(recipeOutput, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_CARROT.get());
        trimCrafting(recipeOutput, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_POTATO.get());
        trimCrafting(recipeOutput, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_WHEAT.get());
        trimCrafting(recipeOutput, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_BEETROOT.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRACTION_BOTTLE.get())
                .pattern(" A ")
                .pattern("BAB")
                .pattern(" B ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.GLASS)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .save(recipeOutput);

        //threeByThreePacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER_BLOCK.get(), ModItems.AMBER_SHARD.get());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER_MOSAIC.get(), ModItems.AMBER_SHARD.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER_MOSAIC_SLAB.get(), ModBlocks.AMBER_MOSAIC.get());
        stairBuilder(ModBlocks.AMBER_MOSAIC_STAIRS, Ingredient.of(ModBlocks.AMBER_MOSAIC))
                .unlockedBy("has_amber_mosaic", has(ModBlocks.AMBER_MOSAIC))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER_MOSAIC_WALL.get(), ModBlocks.AMBER_MOSAIC.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_AMBER.get(), ModBlocks.AMBER_MOSAIC_SLAB.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_AMBER_SLAB.get(), ModBlocks.CHISELED_AMBER.get());
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.AMBER_MOSAIC.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_AMBER.get().asItem(), 0.1F, 200)
                .unlockedBy("has_amber_mosaic", has(ModBlocks.AMBER_MOSAIC))
                .save(recipeOutput);

        //threeByThreePacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GARNET_BLOCK.get(), ModItems.GARNET_SHARD.get());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GARNET_MOSAIC.get(), ModItems.GARNET_SHARD.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GARNET_MOSAIC_SLAB.get(), ModBlocks.GARNET_MOSAIC.get());
        stairBuilder(ModBlocks.GARNET_MOSAIC_STAIRS, Ingredient.of(ModBlocks.GARNET_MOSAIC))
                .unlockedBy("has_garnet_mosaic", has(ModBlocks.GARNET_MOSAIC))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GARNET_MOSAIC_WALL.get(), ModBlocks.GARNET_MOSAIC.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_GARNET.get(), ModBlocks.GARNET_MOSAIC_SLAB.get());
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_GARNET_SLAB.get(), ModBlocks.CHISELED_GARNET.get());
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.GARNET_MOSAIC.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_GARNET.get().asItem(), 0.1F, 200)
                .unlockedBy("has_garnet_mosaic", has(ModBlocks.GARNET_MOSAIC))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CROPRESSOR.get())
                .requires(ModItems.TUBE_PIECE.get())
                .requires(ModItems.SCRAP_PIECE.get())
                .requires(ModItems.ENGINE_PIECE.get())
                .requires(ModItems.PRESS_PIECE.get())
                .requires(ModItems.BELT_PIECE.get())
                .unlockedBy("has_cropressor_piece", has(ModTags.ModItemTags.CROPRESSOR_PIECES))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REBREWING_STAND.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern("BCB")
                .define('A', ModItems.CROPRESSED_NETHERWART.get())
                .define('B', ModItems.BROKEN_REBREWING_STAND.get())
                .define('C', ModItems.TUBE_PIECE.get())
                .unlockedBy("has_broken_rebrewing_stand", has(ModItems.BROKEN_REBREWING_STAND.get()))
                .save(recipeOutput);

        partsRecycling(recipeOutput, ModItems.BELT_PIECE.get(), Items.LEATHER, 8);
        partsRecycling(recipeOutput, ModItems.SCRAP_PIECE.get(), Items.COPPER_INGOT, 8);
        partsRecycling(recipeOutput, ModItems.ENGINE_PIECE.get(), Items.GOLD_INGOT, 8);
        partsRecycling(recipeOutput, ModItems.TUBE_PIECE.get(), Items.IRON_INGOT, 8);
        partsRecycling(recipeOutput, ModItems.PRESS_PIECE.get(), Items.NETHERITE_SCRAP, 1);
        partsRecycling(recipeOutput, ModItems.BROKEN_REBREWING_STAND.get(), ModItems.CROPRESSED_NETHERWART.get(), 4);

        partsRecycling(recipeOutput, ModItems.CROPRESSED_BEETROOT.get(), Items.BEETROOT, 16);
        partsRecycling(recipeOutput, ModItems.CROPRESSED_CARROT.get(), Items.CARROT, 16);
        partsRecycling(recipeOutput, ModItems.CROPRESSED_POTATO.get(), Items.POTATO, 16);
        partsRecycling(recipeOutput, ModItems.CROPRESSED_WHEAT.get(), Items.WHEAT, 16);
        partsRecycling(recipeOutput, ModItems.CROPRESSED_NETHERWART.get(), Items.NETHER_WART, 16);



        planksFromLogs(recipeOutput, ModBlocks.CORRUPTED_PLANKS, ModTags.ModItemTags.CORRUPTED_LOGS, 4);
        woodFromLogs(recipeOutput, ModBlocks.CORRUPTED_WOOD, ModBlocks.CORRUPTED_LOG);
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_CORRUPTED_WOOD, ModBlocks.STRIPPED_CORRUPTED_LOG);
        stairBuilder(ModBlocks.CORRUPTED_STAIRS, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CORRUPTED_SLAB, ModBlocks.CORRUPTED_PLANKS);
        fenceBuilder(ModBlocks.CORRUPTED_FENCE, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.CORRUPTED_FENCE_GATE, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        doorBuilder(ModBlocks.CORRUPTED_DOOR, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        trapdoorBuilder(ModBlocks.CORRUPTED_TRAPDOOR, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.CORRUPTED_PRESSURE_PLATE, ModBlocks.CORRUPTED_PLANKS);
        buttonBuilder(ModBlocks.CORRUPTED_BUTTON, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        woodenBoat(recipeOutput, ModItems.CORRUPTED_BOAT.get(), ModBlocks.CORRUPTED_PLANKS.get());
        chestBoat(recipeOutput, ModItems.CORRUPTED_CHEST_BOAT.get(), ModItems.CORRUPTED_BOAT.get());
        signBuilder(ModBlocks.CORRUPTED_SIGN, Ingredient.of(ModBlocks.CORRUPTED_PLANKS))
                .unlockedBy("has_corrupted_planks", has(ModBlocks.CORRUPTED_PLANKS))
                .save(recipeOutput);
        hangingSign(recipeOutput, ModItems.CORRUPTED_HANGING_SIGN.get(), ModBlocks.CORRUPTED_PLANKS.get());

        planksFromLogs(recipeOutput, ModBlocks.VIVICUS_PLANKS, ModTags.ModItemTags.VIVICUS_LOGS, 4);
        woodFromLogs(recipeOutput, ModBlocks.VIVICUS_WOOD, ModBlocks.VIVICUS_LOG);
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_VIVICUS_WOOD, ModBlocks.STRIPPED_VIVICUS_LOG);
        stairBuilder(ModBlocks.VIVICUS_STAIRS, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VIVICUS_SLAB, ModBlocks.VIVICUS_PLANKS);
        fenceBuilder(ModBlocks.VIVICUS_FENCE, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.VIVICUS_FENCE_GATE, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        doorBuilder(ModBlocks.VIVICUS_DOOR, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        trapdoorBuilder(ModBlocks.VIVICUS_TRAPDOOR, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.VIVICUS_PRESSURE_PLATE, ModBlocks.VIVICUS_PLANKS);
        buttonBuilder(ModBlocks.VIVICUS_BUTTON, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_VIVICUS_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        woodenBoat(recipeOutput, ModItems.VIVICUS_BOAT.get(), ModBlocks.VIVICUS_PLANKS.get());
        chestBoat(recipeOutput, ModItems.VIVICUS_CHEST_BOAT.get(), ModItems.VIVICUS_BOAT.get());
        signBuilder(ModBlocks.VIVICUS_SIGN, Ingredient.of(ModBlocks.VIVICUS_PLANKS))
                .unlockedBy("has_vivicus_planks", has(ModBlocks.VIVICUS_PLANKS))
                .save(recipeOutput);
        hangingSign(recipeOutput, ModItems.VIVICUS_HANGING_SIGN.get(), ModBlocks.VIVICUS_PLANKS.get());
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VIVICUS_ANTIDOTE, 1)
                        .pattern(" AB")
                        .pattern("ACA")
                        .pattern("DA ")
                        .define('A', Tags.Items.GLASS_BLOCKS_COLORLESS)
                        .define('B', ModItems.JAR_OF_ACID)
                        .define('C', ModItems.CORRUPTED_BOBLING_CORE)
                        .define('D', Tags.Items.INGOTS_IRON)
                        .unlockedBy("has_jar_of_acid", has(ModItems.JAR_OF_ACID))
                        .save(recipeOutput);

        SpecialRecipeBuilder.special(RebrewedTippedArrowRecipe::new).save(recipeOutput, "rebrewed_tipped_arrow");


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.DRIPSALT.get().asItem())
                .requires(ModItems.SALTY_SPICE.get(), 5)
                .unlockedBy("has_salty_spice", has(ModItems.SALTY_SPICE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PATTERNSPRIA.get())
                .requires(Ingredient.of(ModTags.ModItemTags.BLOCK_PATTERNS), 1)
                .requires(Ingredient.of(ModItems.DYESPRIA.get()), 1)
                .unlockedBy("has_block_pattern", has(ModTags.ModItemTags.BLOCK_PATTERNS))
                .save(recipeOutput);

        partsRecycling(recipeOutput, ModBlocks.DRIPSALT.get().asItem(), ModItems.SALTY_SPICE.get(), 5);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BEROOT_CAULDRON.get().asItem(), 1)
                .pattern("A A")
                .pattern("ABA")
                .pattern("CDC")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('B', ModItems.CROPRESSED_BEETROOT.get())
                .define('C', ModItems.FLAVORFUL_ROOTS.get())
                .define('D', ModItems.SCRAP_PIECE.get())
                .unlockedBy("has_flavorful_roots", has(ModItems.FLAVORFUL_ROOTS.get()))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.TORCHFLAME.get().asItem())
                .requires(Ingredient.of(ModItems.FIERY_SPICE.get()), 4)
                .unlockedBy("has_fiery_spice", has(ModItems.FIERY_SPICE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BEROOT_COOK_BOOK.get())
                .requires(Ingredient.of(Items.BOOK), 1)
                .requires(Ingredient.of(ModItems.CROPRESSED_BEETROOT.get()), 1)
                .unlockedBy("cropressed_beetroot", has(ModItems.CROPRESSED_BEETROOT.get()))
                .save(recipeOutput);

        ModCustomRecipeProvider.createRecipes(recipeOutput);
    }

    private void trimCrafting(RecipeOutput pRecipeOutput, ItemLike trim, TagKey<Item> ingredient) {
        trimCrafting(pRecipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(RecipeOutput pRecipeOutput, ItemLike trim, ItemLike ingredient) {
        trimCrafting(pRecipeOutput, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(RecipeOutput pRecipeOutput, ItemLike trim, Ingredient ingredient) {
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

    private void partsRecycling(RecipeOutput pRecipeOutput, ItemLike part, Item result, int count) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
                .requires(part)
                .unlockedBy("has_" + getItemName(part), has(part))
                .save(pRecipeOutput, MoreSnifferFlowers.loc(getItemName(result) + "_from_part_recycling"));
    }

}
