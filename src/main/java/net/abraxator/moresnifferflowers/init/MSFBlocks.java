package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.*;
import net.abraxator.moresnifferflowers.blocks.corrupted.*;
import net.abraxator.moresnifferflowers.blocks.xbush.AmbushBlockLower;
import net.abraxator.moresnifferflowers.blocks.xbush.AmbushBlockUpper;
import net.abraxator.moresnifferflowers.blocks.cropressor.CropressorBlockBase;
import net.abraxator.moresnifferflowers.blocks.cropressor.CropressorBlockOut;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.rebrewingstand.RebrewingStandBlockBase;
import net.abraxator.moresnifferflowers.blocks.rebrewingstand.RebrewingStandBlockTop;
import net.abraxator.moresnifferflowers.blocks.signs.ModHangingSignBlock;
import net.abraxator.moresnifferflowers.blocks.signs.ModStandingSignBlock;
import net.abraxator.moresnifferflowers.blocks.signs.ModWallHangingSign;
import net.abraxator.moresnifferflowers.blocks.signs.ModWallSignBlock;
import net.abraxator.moresnifferflowers.blocks.vivicus.*;
import net.abraxator.moresnifferflowers.blocks.xbush.GarbushBlockLower;
import net.abraxator.moresnifferflowers.blocks.xbush.GarbushBlockUpper;
import net.abraxator.moresnifferflowers.items.GiantCropItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface MSFBlocks {
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreSnifferFlowers.MOD_ID);

    DeferredBlock<Block> TORCHFLOWER_AFLAME = registerBlockWithItem("torchflower_aflame", () -> new TorchflowerAflameBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCHFLOWER).noCollission().randomTicks().lightLevel(value -> value.getValue(MSFStateProperties.AGE_2) == 1 ? 12 : 0)));
    DeferredBlock<Block> TORCHFLAME = registerBlockWithItem("torchflame", () -> new TorchflameBlock(BlockBehaviour.Properties.of().sound(SoundType.EMPTY).lightLevel(value -> 12).instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).noCollission()));
    DeferredBlock<Block> TORCHEWFLOWER = registerBlockWithItem("torchewflower", () -> new TorchewflowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCHFLOWER).noCollission().randomTicks()));

    DeferredBlock<Block> DAWNBERRY_VINE = registerBlockNoItem("dawnberry_vine", () -> new DawnberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(value -> value.getValue(DawnberryVineBlock.AGE) >= 3 ? 3 : 0).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion(), false));
    DeferredBlock<Block> GLOOMBERRY_VINE = registerBlockNoItem("gloomberry_vine", () -> new GloomberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));

    DeferredBlock<Block> AMBUSH_BOTTOM = registerBlockNoItem("ambush_bottom", () -> new AmbushBlockLower(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));
    DeferredBlock<Block> AMBUSH_TOP = registerBlockNoItem("ambush_top", () -> new AmbushBlockUpper(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));
    DeferredBlock<Block> GARBUSH_BOTTOM = registerBlockNoItem("garbush_bottom", () -> new GarbushBlockLower(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));
    DeferredBlock<Block> GARBUSH_TOP = registerBlockNoItem("garbush_top", () -> new GarbushBlockUpper(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));

    DeferredBlock<Block> AMBER_BLOCK = registerBlockWithItem("amber_block", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> CHISELED_AMBER = registerBlockWithItem("chiseled_amber", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> CHISELED_AMBER_SLAB = registerBlockWithItem("chiseled_amber_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.CHISELED_AMBER.get())));
    DeferredBlock<Block> CRACKED_AMBER = registerBlockWithItem("cracked_amber", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> AMBER_MOSAIC = registerBlockWithItem("amber_mosaic", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> AMBER_MOSAIC_SLAB = registerBlockWithItem("amber_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.AMBER_MOSAIC.get())));
    DeferredBlock<Block> AMBER_MOSAIC_STAIRS = registerBlockWithItem("amber_mosaic_stairs", () -> stair(AMBER_MOSAIC.get()));
    DeferredBlock<Block> AMBER_MOSAIC_WALL = registerBlockWithItem("amber_mosaic_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.AMBER_MOSAIC.get())));
    DeferredBlock<Block> GARNET_BLOCK = registerBlockWithItem("garnet_block", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(5.0F).noOcclusion()));
    DeferredBlock<Block> CHISELED_GARNET = registerBlockWithItem("chiseled_garnet", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> CHISELED_GARNET_SLAB = registerBlockWithItem("chiseled_garnet_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.CHISELED_GARNET.get())));
    DeferredBlock<Block> CRACKED_GARNET = registerBlockWithItem("cracked_garnet", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> GARNET_MOSAIC = registerBlockWithItem("garnet_mosaic", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    DeferredBlock<Block> GARNET_MOSAIC_SLAB = registerBlockWithItem("garnet_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GARNET_MOSAIC.get())));
    DeferredBlock<Block> GARNET_MOSAIC_STAIRS = registerBlockWithItem("garnet_mosaic_stairs", () -> stair(GARNET_MOSAIC.get()));
    DeferredBlock<Block> GARNET_MOSAIC_WALL = registerBlockWithItem("garnet_mosaic_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GARNET_MOSAIC.get())));

    DeferredBlock<Block> CAULORFLOWER = registerBlockNoItem("caulorflower", () ->  new CaulorflowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).strength(2.0F).noCollission().noOcclusion().randomTicks()));
    DeferredBlock<Block> PATTERNFLOWER = registerBlockNoItem("patternflower", () ->  new PatternflowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).strength(2.0F).noCollission().noOcclusion().randomTicks()));

    DeferredBlock<Block> GIANT_CARROT = registerGiantCrop("giant_carrot", () ->  new GiantCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(3.0F).sound(SoundType.MOSS_CARPET).noOcclusion().pushReaction(PushReaction.BLOCK).isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_POTATO = registerGiantCrop("giant_potato", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get())));
    DeferredBlock<Block> GIANT_NETHERWART = registerGiantCrop("giant_netherwart", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE).dynamicShape()));
    DeferredBlock<Block> GIANT_BEETROOT = registerGiantCrop("giant_beetroot", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_WHEAT = registerGiantCrop("giant_wheat", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_CABBAGE = registerGiantCrop("giant_cabbage", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_ONION = registerGiantCrop("giant_onion", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_TOMATO = registerGiantCrop("giant_tomato", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));
    DeferredBlock<Block> GIANT_RICE = registerGiantCrop("giant_rice", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.GIANT_CARROT.get()).noOcclusion().isSuffocating(GiantCropBlock.STATE_PREDICATE)));

    DeferredBlock<Block> BONMEELIA = registerBlockNoItem("bonmeelia", () ->  new BonmeeliaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F).lightLevel(value -> 3).noOcclusion(), false));
    DeferredBlock<Block> BONWILTIA = registerBlockNoItem("bonwiltia", () ->  new BonmeeliaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F).lightLevel(value -> 3).noOcclusion(), true));
    DeferredBlock<Block> BONDRIPIA = registerBlockNoItem("bondripia", () ->  new BondripiaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPORE_BLOSSOM).strength(0.2F).lightLevel(value -> 3).noOcclusion().randomTicks().pushReaction(PushReaction.BLOCK)));
    DeferredBlock<Block> ACIDRIPIA = registerBlockNoItem("acidripia", () ->  new AciddripiaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPORE_BLOSSOM).strength(0.2F).lightLevel(value -> 3).noOcclusion().randomTicks().pushReaction(PushReaction.BLOCK)));
    DeferredBlock<Block> BONMEEL_FILLED_CAULDRON = registerBlockNoItem("bonmeel_filled_cauldron", () ->  new ModLayeredCauldronBlock(Biome.Precipitation.NONE, MSFCauldronInteractions.BONMEEL, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));
    DeferredBlock<Block> ACID_FILLED_CAULDRON = registerBlockNoItem("acid_filled_cauldron", () ->  new ModLayeredCauldronBlock(Biome.Precipitation.NONE, MSFCauldronInteractions.ACID, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));

    DeferredBlock<Block> CROPRESSOR_CENTER = registerBlockNoItem("cropressor_center", () ->  new CropressorBlockBase(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL), CropressorBlockBase.Part.CENTER));
    DeferredBlock<Block> CROPRESSOR_OUT = registerBlockNoItem("cropressor_out", () ->  new CropressorBlockOut(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL), CropressorBlockBase.Part.OUT));

    DeferredBlock<Block> REBREWING_STAND_BOTTOM = registerBlockNoItem("rebrewing_stand_bottom", () -> new RebrewingStandBlockBase(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5F).noOcclusion()));
    DeferredBlock<Block> REBREWING_STAND_TOP = registerBlockNoItem("rebrewing_stand_top", () -> new RebrewingStandBlockTop(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5F).noOcclusion()));

    DeferredBlock<Block> DYESPRIA_PLANT = registerBlockNoItem("dyespria_plant", () ->  new DyespriaPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    DeferredBlock<Block> DYESCRAPIA_PLANT = registerBlockNoItem("dyescrapia_plant", () ->  new DyescrapiaPlantBlock(BlockBehaviour.Properties.ofFullCopy(DYESPRIA_PLANT.get())));

    DeferredBlock<Block> CORRUPTED_LOG = registerBlockWithItem("corrupted_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_STEM)));
    DeferredBlock<Block> CORRUPTED_WOOD = registerBlockWithItem("corrupted_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HYPHAE)));
    DeferredBlock<Block> STRIPPED_CORRUPTED_LOG = registerBlockWithItem("stripped_corrupted_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_STEM)));
    DeferredBlock<Block> STRIPPED_CORRUPTED_WOOD = registerBlockWithItem("stripped_corrupted_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_HYPHAE)));
    DeferredBlock<Block> CORRUPTED_PLANKS = registerBlockWithItem("corrupted_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));
    DeferredBlock<Block> CORRUPTED_STAIRS = registerBlockWithItem("corrupted_stairs", () -> stair(CORRUPTED_PLANKS.get()));
    DeferredBlock<Block> CORRUPTED_SLAB = registerBlockWithItem("corrupted_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_SLAB)));
    DeferredBlock<Block> CORRUPTED_FENCE = registerBlockWithItem("corrupted_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE)));
    DeferredBlock<Block> CORRUPTED_FENCE_GATE = registerBlockWithItem("corrupted_fence_gate", () -> new FenceGateBlock(MSFWood.WoodTypes.CORRUPTED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE_GATE)));
    DeferredBlock<Block> CORRUPTED_DOOR = registerBlockWithItem("corrupted_door", () -> new DoorBlock(BlockSetType.WARPED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_DOOR)));
    DeferredBlock<Block> CORRUPTED_TRAPDOOR = registerBlockWithItem("corrupted_trapdoor", () -> new TrapDoorBlock(BlockSetType.WARPED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_TRAPDOOR)));
    DeferredBlock<Block> CORRUPTED_PRESSURE_PLATE = registerBlockWithItem("corrupted_pressure_plate", () -> new PressurePlateBlock(BlockSetType.WARPED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PRESSURE_PLATE)));
    DeferredBlock<Block> CORRUPTED_BUTTON = registerBlockWithItem("corrupted_button", () -> new ButtonBlock(BlockSetType.WARPED, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_BUTTON)));
    DeferredBlock<Block> CORRUPTED_LEAVES = registerBlockWithItem("corrupted_leaves", () -> new CorruptedLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LEAVES).noOcclusion()));
    DeferredBlock<Block> CORRUPTED_SAPLING = registerBlockWithItem("corrupted_sapling", () -> new CorruptedSaplingBlock(MSFWood.TreeGrowers.CORRUPTED_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_SAPLING)));
    DeferredBlock<Block> CORRUPTED_SLUDGE = registerBlockWithItem("corrupted_sludge", () -> new CorruptedSludgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(2.0F).friction(0.8F).sound(SoundType.SLIME_BLOCK).lightLevel(value -> 4)));
    DeferredBlock<Block> CORRUPTED_SLIME_LAYER = registerBlockWithItem("corrupted_slime_layer", () -> new CorruptedSlimeLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(0.5F).friction(0.8F).noOcclusion().randomTicks().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).sound(SoundType.SLIME_BLOCK).lightLevel(value -> 4)));
    DeferredBlock<Block> CORRUPTED_SIGN = registerBlockNoItem("corrupted_sign", () -> new ModStandingSignBlock(MSFWood.WoodTypes.CORRUPTED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_SIGN)));
    DeferredBlock<Block> CORRUPTED_WALL_SIGN = registerBlockNoItem("corrupted_wall_sign", () -> new ModWallSignBlock(MSFWood.WoodTypes.CORRUPTED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WALL_SIGN)));
    DeferredBlock<Block> CORRUPTED_HANGING_SIGN = registerBlockNoItem("corrupted_hanging_sign", () -> new ModHangingSignBlock(MSFWood.WoodTypes.CORRUPTED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HANGING_SIGN)));
    DeferredBlock<Block> CORRUPTED_WALL_HANGING_SIGN = registerBlockNoItem("corrupted_wall_hanging_sign", () -> new ModWallHangingSign(MSFWood.WoodTypes.CORRUPTED, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WALL_HANGING_SIGN)));
    DeferredBlock<Block> CORRUPTED_LEAVES_BUSH = registerBlockWithItem("corrupted_leaves_bush", () -> new CorruptedLeavesBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.CORRUPTED_LEAVES.get()).noOcclusion()));

    DeferredBlock<Block> CORRUPTED_GRASS = registerBlockWithItem("corrupted_grass", () -> new TallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    DeferredBlock<Block> CORRUPTED_TALL_GRASS = registerBlockWithItem("corrupted_tall_grass", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    DeferredBlock<Block> DECAYED_LOG = registerBlockWithItem("decayed_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    DeferredBlock<Block> CORRUPTED_GRASS_BLOCK = registerBlockWithItem("corrupted_grass_block", () -> new CorruptedGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.6F).sound(SoundType.WET_GRASS)));
    DeferredBlock<Block> CURED_GRASS_BLOCK = registerBlockWithItem("cured_grass_block", () -> new CuredGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.6F).sound(SoundType.WET_GRASS)));
    DeferredBlock<Block> CORRUPTED_WART = registerBlockWithItem("corrupted_wart", () -> new CorruptedWartBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().instabreak().dynamicShape().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).pushReaction(PushReaction.DESTROY)));

    DeferredBlock<Block> VIVICUS_LOG = registerBlockWithItem("vivicus_log", () -> new VivicusRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG)));
    DeferredBlock<Block> VIVICUS_WOOD = registerBlockWithItem("vivicus_wood", () -> new VivicusRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WOOD)));
    DeferredBlock<Block> STRIPPED_VIVICUS_LOG = registerBlockWithItem("stripped_vivicus_log", () -> new VivicusRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_LOG)));
    DeferredBlock<Block> STRIPPED_VIVICUS_WOOD = registerBlockWithItem("stripped_vivicus_wood", () -> new VivicusRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD)));
    DeferredBlock<Block> VIVICUS_PLANKS = registerBlockWithItem("vivicus_planks", () -> new VivicusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    DeferredBlock<Block> VIVICUS_STAIRS = registerBlockWithItem("vivicus_stairs", () -> vivicusStair(VIVICUS_PLANKS.get()));
    DeferredBlock<Block> VIVICUS_SLAB = registerBlockWithItem("vivicus_slab", () -> new VivicusSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SLAB)));
    DeferredBlock<Block> VIVICUS_FENCE = registerBlockWithItem("vivicus_fence", () -> new VivicusFenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE)));
    DeferredBlock<Block> VIVICUS_FENCE_GATE = registerBlockWithItem("vivicus_fence_gate", () -> new VivicusFenceGateBlock(MSFWood.WoodTypes.VIVICUS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE_GATE)));
    DeferredBlock<Block> VIVICUS_DOOR = registerBlockWithItem("vivicus_door", () -> new VivicusDoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_DOOR)));
    DeferredBlock<Block> VIVICUS_TRAPDOOR = registerBlockWithItem("vivicus_trapdoor", () -> new VivicusTrapDoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_TRAPDOOR)));
    DeferredBlock<Block> VIVICUS_PRESSURE_PLATE = registerBlockWithItem("vivicus_pressure_plate", () -> new VivicusPressurePlateBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE)));
    DeferredBlock<Block> VIVICUS_BUTTON = registerBlockWithItem("vivicus_button", () -> new VivicusButtonBlock(BlockSetType.CHERRY, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_BUTTON)));
    DeferredBlock<Block> VIVICUS_LEAVES = registerBlockWithItem("vivicus_leaves", () -> new VivicusLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES)));
    DeferredBlock<Block> VIVICUS_SAPLING = registerBlockWithItem("vivicus_sapling", () -> new VivicusSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING)));
    DeferredBlock<Block> VIVICUS_LEAVES_SPROUT = registerBlockWithItem("vivicus_leaves_sprout", () -> new VivicusSproutingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PROPAGULE)));
    DeferredBlock<Block> VIVICUS_SIGN = registerBlockNoItem("vivicus_sign", () -> new VivicusStandingSignBlock(MSFWood.WoodTypes.VIVICUS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SIGN)));
    DeferredBlock<Block> VIVICUS_WALL_SIGN = registerBlockNoItem("vivicus_wall_sign", () -> new VivicusWallSignBlock(MSFWood.WoodTypes.VIVICUS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_SIGN)));
    DeferredBlock<Block> VIVICUS_HANGING_SIGN = registerBlockNoItem("vivicus_hanging_sign", () -> new VivicusHangingSignBlock(MSFWood.WoodTypes.VIVICUS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_HANGING_SIGN)));
    DeferredBlock<Block> VIVICUS_WALL_HANGING_SIGN = registerBlockNoItem("vivicus_wall_hanging_sign", () -> new VivicusHangingWallSignBlock(MSFWood.WoodTypes.VIVICUS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN)));

    DeferredBlock<Block> POTTED_DYESPRIA = registerBlockNoItem("potted_dyespria", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DYESPRIA_PLANT, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    DeferredBlock<Block> POTTED_CORRUPTED_SAPLING = registerBlockNoItem("potted_corrupted_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CORRUPTED_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    DeferredBlock<Block> POTTED_VIVICUS_SAPLING = registerBlockNoItem("potted_vivicus_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, VIVICUS_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    DeferredBlock<Block> BEROOT_CAULDRON = registerBlockNoItem("beroot_cauldron", () -> new BerootCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL).noOcclusion().pushReaction(PushReaction.BLOCK).isSuffocating(MSFBlocks::never)));

    DeferredBlock<Block> SALTEMONE = registerBlockNoItem("saltemone", () -> new SaltemoneBlock(BlockBehaviour.Properties.of().noOcclusion().pushReaction(PushReaction.BLOCK).isSuffocating(MSFBlocks::never).strength(0.3F).sound(SoundType.WET_GRASS)));
    DeferredBlock<Block> SOURLEMONE = registerBlockNoItem("sourlemone", () -> new SourlemonBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.SALTEMONE.get()).noOcclusion().pushReaction(PushReaction.BLOCK).isSuffocating(MSFBlocks::never)));
    DeferredBlock<Block> SALTY_CLUMP = registerBlockNoItem("salty_clump", () -> new SaltyClumpBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD).noOcclusion().sound(SoundType.SAND).pushReaction(PushReaction.DESTROY).isSuffocating(MSFBlocks::never).noCollission()));
    DeferredBlock<Block> DRIPSALT = registerBlockNoItem("dripsalt", () -> new DripsaltBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE)));
    DeferredBlock<Block> SOUR_PUDDLE = registerBlockNoItem("sour_puddle", () -> new SourPuddleBlock(BlockBehaviour.Properties.ofFullCopy(MSFBlocks.SALTY_CLUMP.get()).sound(SoundType.MUD).requiresCorrectToolForDrops().friction(0.98F).noOcclusion().pushReaction(PushReaction.DESTROY).isSuffocating(MSFBlocks::never)));

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return MSFItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerGiantCrop(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerGiantCropItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<Item> registerGiantCropItem(String name, DeferredBlock<T> block) {
        return MSFItems.ITEMS.register(name, () -> new GiantCropItem(block.get(),
                new Item.Properties()));
    }

    static Block vivicusStair(Block pBaseBlock) {
        return new VivicusStairBlock(pBaseBlock.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(pBaseBlock));
    }
    
    static Block stair(Block pBaseBlock) {
        return new StairBlock(pBaseBlock.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(pBaseBlock));
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
}
