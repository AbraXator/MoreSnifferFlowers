package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup pProvider) {
        this.getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.DAWNBERRY_VINE, ModBlocks.AMBUSH, ModBlocks.CAULORFLOWER);
        this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(ModBlocks.DAWNBERRY_VINE, ModBlocks.AMBUSH, ModBlocks.CAULORFLOWER);
        this.getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.DAWNBERRY_VINE, ModBlocks.AMBUSH, ModBlocks.CAULORFLOWER);
        this.getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.DAWNBERRY_VINE, ModBlocks.AMBUSH);
        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.AMBER);
        this.getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.AMBER);
        this.getOrCreateTagBuilder(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM).add(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.NETHER_WART);
        this.getOrCreateTagBuilder(ModTags.ModBlockTags.GIANT_CROPS).add(ModBlocks.GIANT_CARROT, ModBlocks.GIANT_POTATO, ModBlocks.GIANT_NETHERWART, ModBlocks.GIANT_BEETROOT, ModBlocks.GIANT_WHEAT);
    }
}
