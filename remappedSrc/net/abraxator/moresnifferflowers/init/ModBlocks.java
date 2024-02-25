package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MoreSnifferFlowers.MOD_ID);

    public static final DeferredBlock<Block> DAWNBERRY_VINE = BLOCKS.register("dawnberry_vine", () -> new DawnberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(value -> value.getValue(DawnberryVineBlock.AGE) >= 3 ? 3 : 0).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));
    public static final DeferredBlock<Block> BOBLING_HEAD = registerBlockNoItem("bobling_head", () -> new BoblingHeadBlock(AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK).nonOpaque()));
    public static final DeferredBlock<Block> AMBUSH = BLOCKS.register("ambush", () -> new AmbushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> AMBER = registerBlockWithItem("amber", () ->  new Block(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.GLASS).strength(3.0F).nonOpaque()));
    public static final DeferredBlock<Block> CAULORFLOWER = registerBlockWithItem("caulorflower", () ->  new CaulorflowerBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.GRASS).strength(2.0F).noCollision().nonOpaque().ticksRandomly()));
    public static final DeferredBlock<Block> GIANT_CARROT = registerBlockNoItem("giant_carrot", () ->  new GiantCropBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(Instrument.BANJO).strength(0.5F).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque()));
    public static final DeferredBlock<Block> GIANT_POTATO = registerBlockNoItem("giant_potato", () ->  new GiantCropBlock(AbstractBlock.Settings.copy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_NETHERWART = registerBlockNoItem("giant_netherwart", () ->  new GiantCropBlock(AbstractBlock.Settings.copy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_BEETROOT = registerBlockNoItem("giant_beetroot", () ->  new GiantCropBlock(AbstractBlock.Settings.copy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_WHEAT = registerBlockNoItem("giant_wheat", () ->  new GiantCropBlock(AbstractBlock.Settings.copy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> BONMEELIA = registerBlockNoItem("bonmeelia", () ->  new BonmeeliaBlock(AbstractBlock.Settings.copy(Blocks.WHEAT).luminance(value -> 3).nonOpaque()));
    public static final DeferredBlock<Block> CROPRESSOR = registerBlockNoItem("cropressor", () ->  new CropressorBlock(AbstractBlock.Settings.copy(Blocks.ANVIL)));
    public static final DeferredBlock<Block> MORE_SNIFFER_FLOWER = registerBlockNoItem("more_sniffer_flower", () ->  new CropressorBlock(AbstractBlock.Settings.copy(Blocks.ANVIL)));


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
