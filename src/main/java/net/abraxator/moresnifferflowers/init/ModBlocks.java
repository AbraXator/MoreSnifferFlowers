package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.*;
import net.abraxator.moresnifferflowers.blocks.ambush.AmbushBlockBase;
import net.abraxator.moresnifferflowers.blocks.ambush.AmbushBlockUpper;
import net.abraxator.moresnifferflowers.blocks.cropressor.CropressorBlockBase;
import net.abraxator.moresnifferflowers.blocks.cropressor.CropressorBlockOut;
import net.abraxator.moresnifferflowers.blocks.rebrewingstand.RebrewingStandBlockBase;
import net.abraxator.moresnifferflowers.blocks.rebrewingstand.RebrewingStandBlockTop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MoreSnifferFlowers.MOD_ID);

    public static final DeferredBlock<Block> DAWNBERRY_VINE = registerBlockNoItem("dawnberry_vine", () -> new DawnberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(value -> value.getValue(DawnberryVineBlock.AGE) >= 3 ? 3 : 0).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));
    public static final DeferredBlock<Block> BOBLING_HEAD = registerBlockNoItem("bobling_head", () -> new BoblingHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noOcclusion()));
    public static final DeferredBlock<Block> AMBUSH_BOTTOM = BLOCKS.register("ambush_bottom", () -> new AmbushBlockBase(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));
    public static final DeferredBlock<Block> AMBUSH_TOP = BLOCKS.register("ambush_top", () -> new AmbushBlockUpper(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F)));
    public static final DeferredBlock<Block> AMBER = registerBlockWithItem("amber", () ->  new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    public static final DeferredBlock<Block> CAULORFLOWER = registerBlockWithItem("caulorflower", () ->  new CaulorflowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).strength(2.0F).noCollission().noOcclusion().randomTicks()));
    public static final DeferredBlock<Block> GIANT_CARROT = registerBlockNoItem("giant_carrot", () ->  new GiantCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(3.0F).sound(SoundType.MOSS_CARPET).noOcclusion().pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> GIANT_POTATO = registerBlockNoItem("giant_potato", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_NETHERWART = registerBlockNoItem("giant_netherwart", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_BEETROOT = registerBlockNoItem("giant_beetroot", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_WHEAT = registerBlockNoItem("giant_wheat", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> BONMEELIA = registerBlockNoItem("bonmeelia", () ->  new BonmeeliaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).strength(0.2F).lightLevel(value -> 3).noOcclusion()));
    public static final DeferredBlock<Block> CROPRESSOR_CENTER = registerBlockNoItem("cropressor_center", () ->  new CropressorBlockBase(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL), CropressorBlockBase.Part.CENTER));
    public static final DeferredBlock<Block> CROPRESSOR_OUT = registerBlockNoItem("cropressor_out", () ->  new CropressorBlockOut(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL), CropressorBlockBase.Part.OUT));
    public static final DeferredBlock<Block> MORE_SNIFFER_FLOWER = BLOCKS.register("more_sniffer_flower", () -> new MoreSnifferFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).strength(2.0F).noCollission().noOcclusion()));
    public static final DeferredBlock<Block> REBREWING_STAND_BOTTOM = registerBlockNoItem("rebrewing_stand_bottom", () -> new RebrewingStandBlockBase(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5F).noOcclusion()));
    public static final DeferredBlock<Block> REBREWING_STAND_TOP = registerBlockNoItem("rebrewing_stand_top", () -> new RebrewingStandBlockTop(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5F).noOcclusion()));
    public static final DeferredBlock<Block> DYESPRIA_PLANT = registerBlockNoItem("dyespria_plant", () ->  new DyespriaPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
}
