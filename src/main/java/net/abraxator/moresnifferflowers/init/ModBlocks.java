package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
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

    public static final DeferredBlock<Block> DAWNBERRY_VINE = BLOCKS.register("dawnberry_vine", () -> new DawnberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(value -> value.getValue(DawnberryVineBlock.AGE) >= 3 ? 3 : 0).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));
    public static final DeferredBlock<Block> BOBLING_HEAD = registerBlockNoItem("bobling_head", () -> new BoblingHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).noOcclusion()));
    public static final DeferredBlock<Block> AMBUSH = BLOCKS.register("ambush", () -> new AmbushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> AMBER = registerBlockWithItem("amber", () ->  new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(3.0F).noOcclusion()));
    public static final DeferredBlock<Block> CAULORFLOWER = registerBlockWithItem("caulorflower", () ->  new CaulorflowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).strength(2.0F).noCollission().noOcclusion().randomTicks()));
    public static final DeferredBlock<Block> GIANT_CARROT = registerBlockNoItem("giant_carrot", () ->  new GiantCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.MOSS_CARPET).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_POTATO = registerBlockNoItem("giant_potato", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_NETHERWART = registerBlockNoItem("giant_netherwart", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_BEETROOT = registerBlockNoItem("giant_beetroot", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> GIANT_WHEAT = registerBlockNoItem("giant_wheat", () ->  new GiantCropBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.GIANT_CARROT.get()).noOcclusion()));
    public static final DeferredBlock<Block> BONMEELIA = registerBlockNoItem("bonmeelia", () ->  new BonmeeliaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).lightLevel(value -> 3).noOcclusion()));


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
