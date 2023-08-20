package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<Block> DAWNBERRY_VINE = BLOCKS.register("dawnberry_vine", () -> new DawnberryVineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noOcclusion()));
    public static final RegistryObject<Block> BOBLING_HEAD = BLOCKS.register("bobling_head", () -> new BoblingHeadBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> AMBUSH = BLOCKS.register("ambush", () -> new AmbushBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    //public static final RegistryObject<Block> AMBUSH_CROP = BLOCKS.register("ambush_crop", () -> new AmbushBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> AMBER = registerBlock("amber", () ->  new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GLASS).strength(30.0F, 600.0F).noOcclusion()));
    public static final RegistryObject<Block> CAULORFLOWER = registerBlock("caulorflower", () ->  new CaulorflowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.WOOD).strength(30.0F, 600.0F).noCollission().noOcclusion()));
    public static final RegistryObject<Block> CAULORFLOWER_PLANT = registerBlock("caulorflower_plant", () ->  new CaulorflowerPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.WOOD).strength(30.0F, 600.0F).noCollission().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
}
