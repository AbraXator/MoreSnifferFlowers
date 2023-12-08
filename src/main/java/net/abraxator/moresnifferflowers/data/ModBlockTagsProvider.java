package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.FLOWERS).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.tag(BlockTags.SWORD_EFFICIENT).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.AMBER.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.AMBER.get());
        this.tag(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM).add(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.NETHER_WART);
    }
}
