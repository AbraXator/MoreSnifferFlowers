package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {
    public ModBlockTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BLOCK, pLookupProvider, block -> block.builtInRegistryHolder().key(), MoreSnifferFlowers.MOD_ID, existingFileHelper);
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
        this.tag(ModTags.ModBlockTags.GIANT_CROPS).add(ModBlocks.GIANT_CARROT.get(), ModBlocks.GIANT_POTATO.get(), ModBlocks.GIANT_NETHERWART.get(), ModBlocks.GIANT_BEETROOT.get(), ModBlocks.GIANT_WHEAT.get());
    }
}
