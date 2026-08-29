package net.abraxator.moresnifferflowers.datagen.tag;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.datagen.DatagenUtils;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static net.abraxator.moresnifferflowers.init.MSFTags.ModBlockTags.*;

public class ModBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, pLookupProvider, block -> block.builtInRegistryHolder().key(), MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.FLOWERS).add(MSFBlocks.DAWNBERRY_VINE.get(), MSFBlocks.AMBUSH_BOTTOM.get(), MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.CAULORFLOWER.get(), MSFBlocks.DYESPRIA_PLANT.get(), MSFBlocks.BONMEELIA.get());
        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(MSFBlocks.DAWNBERRY_VINE.get(), MSFBlocks.AMBUSH_BOTTOM.get(), MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.CAULORFLOWER.get());

        this.tag(BlockTags.SWORD_EFFICIENT).add(MSFBlocks.DAWNBERRY_VINE.get(), MSFBlocks.AMBUSH_BOTTOM.get(), MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.CAULORFLOWER.get(), MSFBlocks.BONMEELIA.get(),
                MSFBlocks.GIANT_CARROT.get(), MSFBlocks.GIANT_POTATO.get(), MSFBlocks.GIANT_NETHERWART.get(), MSFBlocks.GIANT_BEETROOT.get(), MSFBlocks.GIANT_WHEAT.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(MSFBlocks.DAWNBERRY_VINE.get(), MSFBlocks.AMBUSH_BOTTOM.get(), MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.BONMEELIA.get(), MSFBlocks.CAULORFLOWER.get(),
                MSFBlocks.CORRUPTED_LEAVES.get(), MSFBlocks.CORRUPTED_LEAVES_BUSH.get(), MSFBlocks.VIVICUS_LEAVES.get(), MSFBlocks.VIVICUS_LEAVES_SPROUT.get()).addTag(GIANT_CROPS);
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(MSFBlocks.CAULORFLOWER.get()).addTag(GIANT_CROPS);
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(MSFBlocks.CORRUPTED_SLUDGE.get(), MSFBlocks.CORRUPTED_SLIME_LAYER.get(), MSFBlocks.CORRUPTED_GRASS_BLOCK.get(), MSFBlocks.CURED_GRASS_BLOCK.get(), MSFBlocks.SALTY_CLUMP.get(), MSFBlocks.SOUR_PUDDLE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                MSFBlocks.AMBER_BLOCK.get(), MSFBlocks.GARNET_BLOCK.get(), MSFBlocks.CROPRESSOR_OUT.get(), MSFBlocks.CROPRESSOR_CENTER.get(), MSFBlocks.REBREWING_STAND_BOTTOM.get(),
                MSFBlocks.CHISELED_AMBER.get(), MSFBlocks.CHISELED_AMBER_SLAB.get(), MSFBlocks.CRACKED_AMBER.get(), MSFBlocks.AMBER_MOSAIC.get(), MSFBlocks.AMBER_MOSAIC_STAIRS.get(), MSFBlocks.AMBER_MOSAIC_WALL.get(), MSFBlocks.AMBER_MOSAIC_SLAB.get(),
                MSFBlocks.CHISELED_GARNET.get(), MSFBlocks.CHISELED_GARNET_SLAB.get(), MSFBlocks.CRACKED_GARNET.get(), MSFBlocks.GARNET_MOSAIC.get(), MSFBlocks.GARNET_MOSAIC_STAIRS.get(), MSFBlocks.GARNET_MOSAIC_WALL.get(), MSFBlocks.GARNET_MOSAIC_SLAB.get(),
                MSFBlocks.REBREWING_STAND_TOP.get(), MSFBlocks.BEROOT_CAULDRON.get(), MSFBlocks.DRIPSALT.get() );
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(MSFBlocks.AMBER_BLOCK.get(), MSFBlocks.GARNET_BLOCK.get(), MSFBlocks.CROPRESSOR_OUT.get(), MSFBlocks.CROPRESSOR_CENTER.get(), MSFBlocks.REBREWING_STAND_BOTTOM.get(), MSFBlocks.REBREWING_STAND_TOP.get());

        this.tag(MSFTags.ModBlockTags.BONMEELABLE).add(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.NETHER_WART)
                .addOptional(MoreSnifferFlowers.farmersDelightLoc("tomatoes")).addOptional(MoreSnifferFlowers.farmersDelightLoc("onions")).addOptional(MoreSnifferFlowers.farmersDelightLoc("cabbages")).addOptional(MoreSnifferFlowers.farmersDelightLoc("rice_panicles"));

        this.tag(MSFTags.ModBlockTags.GIANT_CROP_REPLACEABLE).addOptional(MoreSnifferFlowers.farmersDelightLoc("rice"));
        this.tag(MSFTags.ModBlockTags.GIANT_CROPS).add(MSFBlocks.GIANT_CARROT.get(), MSFBlocks.GIANT_POTATO.get(), MSFBlocks.GIANT_NETHERWART.get(), MSFBlocks.GIANT_BEETROOT.get(), MSFBlocks.GIANT_WHEAT.get(), MSFBlocks.GIANT_ONION.get(), MSFBlocks.GIANT_TOMATO.get(), MSFBlocks.GIANT_CABBAGE.get(), MSFBlocks.GIANT_RICE.get());
        this.tag(MSFTags.ModBlockTags.NO_SHADING).add(MSFBlocks.GIANT_RICE.get());
        this.tag(MSFTags.ModBlockTags.WATERLOGGABLE).add(MSFBlocks.GIANT_RICE.get());

        this.tag(BlockTags.LOGS_THAT_BURN).add(MSFBlocks.DECAYED_LOG.get(), MSFBlocks.CORRUPTED_LOG.get(), MSFBlocks.VIVICUS_LOG.get(), MSFBlocks.STRIPPED_CORRUPTED_LOG.get(), MSFBlocks.STRIPPED_VIVICUS_LOG.get(), MSFBlocks.CORRUPTED_WOOD.get(), MSFBlocks.VIVICUS_WOOD.get(), MSFBlocks.STRIPPED_CORRUPTED_WOOD.get(), MSFBlocks.STRIPPED_VIVICUS_WOOD.get());
        this.tag(BlockTags.LEAVES).add(MSFBlocks.CORRUPTED_LEAVES.get(), MSFBlocks.CORRUPTED_LEAVES_BUSH.get(), MSFBlocks.VIVICUS_LEAVES.get());

        this.tag(BlockTags.WOODEN_BUTTONS).add(MSFBlocks.CORRUPTED_BUTTON.get(), MSFBlocks.VIVICUS_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(MSFBlocks.CORRUPTED_PRESSURE_PLATE.get(), MSFBlocks.VIVICUS_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(MSFBlocks.CORRUPTED_DOOR.get(), MSFBlocks.VIVICUS_DOOR.get());
        this.tag(BlockTags.WOODEN_SLABS).add(MSFBlocks.CORRUPTED_SLAB.get(), MSFBlocks.VIVICUS_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(MSFBlocks.CORRUPTED_STAIRS.get(), MSFBlocks.VIVICUS_STAIRS.get());
        this.tag(BlockTags.WOODEN_FENCES).add(MSFBlocks.CORRUPTED_FENCE.get(), MSFBlocks.VIVICUS_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(MSFBlocks.CORRUPTED_FENCE_GATE.get(), MSFBlocks.VIVICUS_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(MSFBlocks.CORRUPTED_TRAPDOOR.get(), MSFBlocks.VIVICUS_TRAPDOOR.get());
        this.tag(BlockTags.PLANKS).add(MSFBlocks.CORRUPTED_PLANKS.get(), MSFBlocks.VIVICUS_PLANKS.get());
        this.tag(BlockTags.SAPLINGS).add(MSFBlocks.CORRUPTED_SAPLING.get(), MSFBlocks.VIVICUS_SAPLING.get());
        this.tag(BlockTags.WALLS).add(MSFBlocks.AMBER_MOSAIC_WALL.get(), MSFBlocks.GARNET_MOSAIC_WALL.get());

        this.tag(VIVICUS_BLOCKS).add(
            MSFBlocks.STRIPPED_VIVICUS_WOOD.get(), MSFBlocks.STRIPPED_VIVICUS_LOG.get(), MSFBlocks.VIVICUS_BUTTON.get(),
            MSFBlocks.VIVICUS_DOOR.get(), MSFBlocks.VIVICUS_FENCE.get(), MSFBlocks.VIVICUS_FENCE_GATE.get(),
            MSFBlocks.VIVICUS_LEAVES.get(), MSFBlocks.VIVICUS_LOG.get(), MSFBlocks.VIVICUS_PLANKS.get(),
            MSFBlocks.VIVICUS_PRESSURE_PLATE.get(), MSFBlocks.VIVICUS_SAPLING.get(), MSFBlocks.VIVICUS_STAIRS.get(),
            MSFBlocks.VIVICUS_SLAB.get(), MSFBlocks.VIVICUS_TRAPDOOR.get(), MSFBlocks.VIVICUS_WOOD.get(),
            MSFBlocks.VIVICUS_LEAVES_SPROUT.get());
        this.tag(CORRUPTED_BLOCKS).add(
                MSFBlocks.STRIPPED_CORRUPTED_WOOD.get(), MSFBlocks.STRIPPED_CORRUPTED_LOG.get(), MSFBlocks.CORRUPTED_BUTTON.get(),
                MSFBlocks.CORRUPTED_DOOR.get(), MSFBlocks.CORRUPTED_FENCE.get(), MSFBlocks.CORRUPTED_FENCE_GATE.get(),
                MSFBlocks.CORRUPTED_LEAVES.get(), MSFBlocks.CORRUPTED_LEAVES_BUSH.get(), MSFBlocks.CORRUPTED_LOG.get(), MSFBlocks.CORRUPTED_PLANKS.get(),
                MSFBlocks.CORRUPTED_PRESSURE_PLATE.get(), MSFBlocks.CORRUPTED_SAPLING.get(), MSFBlocks.CORRUPTED_STAIRS.get(),
                MSFBlocks.CORRUPTED_SLAB.get(), MSFBlocks.CORRUPTED_TRAPDOOR.get(), MSFBlocks.CORRUPTED_WOOD.get(),
                MSFBlocks.CORRUPTED_SLUDGE.get());
        this.tag(CORRUPTED_SLUDGE).add(MSFBlocks.CORRUPTED_LOG.get(), MSFBlocks.CORRUPTED_LEAVES.get(), MSFBlocks.CORRUPTED_LEAVES_BUSH.get());
        this.tag(VIVICUS_TREE_REPLACABLE).addTag(BlockTags.REPLACEABLE_BY_TREES).add(MSFBlocks.VIVICUS_SAPLING.get());
        this.tag(CORRUPTION_TRANSFORMABLES).add(MSFBlocks.DYESPRIA_PLANT.get(), MSFBlocks.DAWNBERRY_VINE.get());
        this.tag(UNCORRUPTABLE).add(MSFBlocks.CORRUPTED_LOG.get(), MSFBlocks.CORRUPTED_LEAVES_BUSH.get(), MSFBlocks.CORRUPTED_LEAVES.get(), MSFBlocks.STRIPPED_CORRUPTED_LOG.get()
                , MSFBlocks.CORRUPTED_WOOD.get(), MSFBlocks.STRIPPED_CORRUPTED_WOOD.get(), MSFBlocks.DECAYED_LOG.get());
        this.tag(NO_CORRUPTED_SLIME_COLLISION).add(MSFBlocks.SALTEMONE.get(), MSFBlocks.SOURLEMONE.get());

        this.tag(MSFTags.ModBlockTags.STICKABLE)
                .add(Blocks.SHORT_GRASS, Blocks.STONE, Blocks.SAND, Blocks.GRAVEL, Blocks.TALL_GRASS)
                .addTag(BlockTags.LEAVES).addTag(BlockTags.DIRT).addTag(net.minecraft.tags.BlockTags.REPLACEABLE);

        this.tag(CORRUPTION_SHIELDING).add(Blocks.OXEYE_DAISY, Blocks.SUNFLOWER, Blocks.PITCHER_PLANT);
        this.tag(CARRYON_BLACKLIST).add(MSFBlocks.AMBUSH_BOTTOM.get(), MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.GARBUSH_BOTTOM.get(), MSFBlocks.GARBUSH_TOP.get());

        this.tag(BlockTags.CAULDRONS).add(MSFBlocks.ACID_FILLED_CAULDRON.get(), MSFBlocks.BONMEEL_FILLED_CAULDRON.get());

        this.supTag(BlockTags.DIRT).add(MSFBlocks.CORRUPTED_GRASS_BLOCK, MSFBlocks.CURED_GRASS_BLOCK);
        this.tag(BlockTags.FLOWER_POTS).add(MSFBlocks.POTTED_DYESPRIA.get(), MSFBlocks.POTTED_CORRUPTED_SAPLING.get(), MSFBlocks.POTTED_VIVICUS_SAPLING.get());

        this.tag(MSFTags.ModBlockTags.DYED).add(Blocks.GLASS, Blocks.GLASS_PANE, Blocks.TERRACOTTA, Blocks.SHULKER_BOX, Blocks.CANDLE)
                .addTag(Tags.Blocks.DYED).remove(BlockTags.BEDS);
    }

    public DatagenUtils.SupplierTagAppender<Block> supTag(TagKey<Block> tagKey){
        return new DatagenUtils.SupplierTagAppender<>(this.tag(tagKey));
    }
}
