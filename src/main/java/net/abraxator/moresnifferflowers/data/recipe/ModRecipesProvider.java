package net.abraxator.moresnifferflowers.data.recipe;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        trimSmithing(pWriter, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pWriter, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pWriter, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pWriter, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pWriter, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        trimSmithing(pWriter, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MoreSnifferFlowers.loc(getItemName(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get())));
        
        trimCrafting(pWriter, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT);
        trimCrafting(pWriter, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_NETHERWART.get());
        trimCrafting(pWriter, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_CARROT.get());
        trimCrafting(pWriter, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_POTATO.get());
        trimCrafting(pWriter, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ModItems.CROPRESSED_BEETROOT.get());
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRACTION_BOTTLE.get())
                .pattern(" A ")
                .pattern("BAB")
                .pattern(" B ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.GLASS)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .save(pWriter);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AMBUSH_BANNER_PATTERN.get())
                .requires(Items.PAPER)
                .requires(ModItems.AMBER_SHARD.get())
                .unlockedBy("has_amber_shard", has(ModItems.AMBER_SHARD.get()))
                .save(pWriter);
        
        twoByTwoPacker(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBER.get(), ModItems.AMBER_SHARD.get());
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CROPRESSOR.get())
                .requires(ModItems.TUBE_PIECE.get())
                .requires(ModItems.SCRAP_PIECE.get())
                .requires(ModItems.ENGINE_PIECE.get())
                .requires(ModItems.PRESS_PIECE.get())
                .requires(ModItems.BELT_PIECE.get())
                .unlockedBy("has_cropressor_piece", has(ModTags.ModItemTags.CROPRESSOR_PIECES))
                .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REBREWING_STAND.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern("BCB")
                .define('A', ModItems.CROPRESSED_NETHERWART.get())
                .define('B', ModItems.BROKEN_REBREWING_STAND.get())
                .define('C', ModItems.TUBE_PIECE.get())
                .unlockedBy("has_broken_rebrewing_stand", has(ModItems.BROKEN_REBREWING_STAND.get()))
                .save(pWriter);
        
        partsRecycling(pWriter, ModItems.BELT_PIECE.get(), Items.LEATHER, 8);
        partsRecycling(pWriter, ModItems.SCRAP_PIECE.get(), Items.COPPER_INGOT, 8);
        partsRecycling(pWriter, ModItems.ENGINE_PIECE.get(), Items.GOLD_INGOT, 8);
        partsRecycling(pWriter, ModItems.TUBE_PIECE.get(), Items.IRON_INGOT, 8);
        partsRecycling(pWriter, ModItems.PRESS_PIECE.get(), Items.NETHERITE_SCRAP, 1);
        partsRecycling(pWriter, ModItems.BROKEN_REBREWING_STAND.get(), ModItems.CROPRESSED_NETHERWART.get(), 4);
        
        ModCustomRecipeProvider.createRecipes(pWriter);
    }

    private void trimCrafting(Consumer<FinishedRecipe> pWriter, ItemLike trim, TagKey<Item> ingredient) {
        trimCrafting(pWriter, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(Consumer<FinishedRecipe> pWriter, ItemLike trim, ItemLike ingredient) {
        trimCrafting(pWriter, trim, Ingredient.of(ingredient));
    }

    private void trimCrafting(Consumer<FinishedRecipe> pWriter, ItemLike trim, Ingredient ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, trim)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .define('A', Items.DIAMOND)
                .define('B', trim)
                .define('C', ingredient)
                .unlockedBy("has_" + getItemName(trim) + "_trim_template", has(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(pWriter, MoreSnifferFlowers.loc(getItemName(trim) + "_from_trim_crafting"));
    }
    
    private void partsRecycling(Consumer<FinishedRecipe> pWriter, Item part, Item result, int count) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
                .requires(part)
                .unlockedBy("has_" + getItemName(part), has(ModItems.BELT_PIECE.get()))
                .save(pWriter, MoreSnifferFlowers.loc(getItemName(result) + "_from_part_recycling"));
    }
}
