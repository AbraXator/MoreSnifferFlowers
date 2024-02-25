package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.ValueLookupTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends ValueLookupTagProvider<Block> {
    public ModBlockTagsProvider(DataOutput pOutput, CompletableFuture<RegistryWrapper.WrapperLookup> pLookupProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, RegistryKeys.BLOCK, pLookupProvider, block -> block.builtInRegistryHolder().key(), MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup pProvider) {
        this.getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get(), ModBlocks.CAULORFLOWER.get());
        this.getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.DAWNBERRY_VINE.get(), ModBlocks.AMBUSH.get());
        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.AMBER.get());
        this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.AMBER.get());
        this.getOrCreateTagBuilder(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM).add(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.NETHER_WART);
        this.getOrCreateTagBuilder(ModTags.ModBlockTags.GIANT_CROPS).add(ModBlocks.GIANT_CARROT.get(), ModBlocks.GIANT_POTATO.get(), ModBlocks.GIANT_NETHERWART.get(), ModBlocks.GIANT_BEETROOT.get(), ModBlocks.GIANT_WHEAT.get());
    }
}
