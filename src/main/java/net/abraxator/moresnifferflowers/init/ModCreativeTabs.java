package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MORESNIFFERFLOWERS_TAB = TABS.register("moresnifferflowers_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("moresnifferflowers.creative_tab"))
            .icon(() -> new ItemStack(ModItems.CREATIVE_TAB_ICON.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.DAWNBERRY_VINE_SEEDS.get());
                output.accept(ModItems.DAWNBERRY.get());
                output.accept(ModItems.GLOOMBERRY_VINE_SEEDS);
                output.accept(ModItems.GLOOMBERRY);

                output.accept(ModItems.AMBUSH_SEEDS.get());
                output.accept(ModBlocks.AMBER_BLOCK.get());
                output.accept(ModBlocks.AMBER_MOSAIC.get());
                output.accept(ModBlocks.AMBER_MOSAIC_STAIRS.get());
                output.accept(ModBlocks.AMBER_MOSAIC_SLAB.get());
                output.accept(ModBlocks.AMBER_MOSAIC_WALL.get());
                output.accept(ModBlocks.CHISELED_AMBER.get());
                output.accept(ModBlocks.CHISELED_AMBER_SLAB.get());
                output.accept(ModBlocks.CRACKED_AMBER.get());

                output.accept(ModItems.AMBER_SHARD.get());
                output.accept(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.DRAGONFLY.get());
                output.accept(ModItems.AMBUSH_BANNER_PATTERN.get());

                output.accept(ModItems.GARBUSH_SEEDS);
                output.accept(ModBlocks.GARNET_BLOCK.get());
                output.accept(ModBlocks.GARNET_MOSAIC.get());
                output.accept(ModBlocks.GARNET_MOSAIC_STAIRS.get());
                output.accept(ModBlocks.GARNET_MOSAIC_SLAB.get());
                output.accept(ModBlocks.GARNET_MOSAIC_WALL.get());
                output.accept(ModBlocks.CHISELED_GARNET.get());
                output.accept(ModBlocks.CHISELED_GARNET_SLAB.get());
                output.accept(ModBlocks.CRACKED_GARNET.get());
                output.accept(ModItems.GARNET_SHARD);

                output.accept(ModItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.EVIL_BANNER_PATTERN.get());

                output.accept(ModItems.DYESPRIA_SEEDS.get());
                output.accept(ModItems.DYESPRIA.get());
                output.accept(ModItems.DYESCRAPIA);

                output.accept(ModBlocks.CAULORFLOWER.get());
                output.accept(ModBlocks.PATTERNFLOWER.get());
                output.accept(ModItems.PATTERNSPRIA.get());

                output.accept(ModItems.BONMEELIA_SEEDS.get());
                output.accept(ModItems.JAR_OF_BONMEEL.get());
                output.accept(ModItems.BONDRIPIA_SEEDS);
                output.accept(ModItems.BONWILTIA_SEEDS.get());
                output.accept(ModItems.JAR_OF_ACID.get());
                output.accept(ModItems.ACIDRIPIA_SEEDS);

                output.accept(ModItems.SALTEMONE_SEEDS.get());
                output.accept(ModItems.SALTY_SPICE.get());
                output.accept(ModItems.DRIPSALT.get());
                output.accept(ModItems.SOURLEMONE_SEEDS.get());
                output.accept(ModBlocks.SOUR_PUDDLE.get());

                output.accept(ModBlocks.TORCHFLOWER_AFLAME.get());
                output.accept(ModBlocks.TORCHFLAME.get());
                output.accept(ModItems.FIERY_SPICE.get());

                output.accept(ModBlocks.TORCHEWFLOWER.get());
                output.accept(ModItems.SWEET_SPICE.get());

                output.accept(ModItems.BELT_PIECE.get());
                output.accept(ModItems.ENGINE_PIECE.get());
                output.accept(ModItems.TUBE_PIECE.get());
                output.accept(ModItems.SCRAP_PIECE.get());
                output.accept(ModItems.PRESS_PIECE.get());
                output.accept(ModItems.CROPRESSOR.get());
                
                output.accept(ModItems.CROPRESSED_CARROT.get());
                output.accept(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModBlocks.GIANT_CARROT);
                
                output.accept(ModItems.CROPRESSED_POTATO.get());
                output.accept(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModBlocks.GIANT_POTATO);
                
                output.accept(ModItems.CROPRESSED_WHEAT.get());
                output.accept(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModBlocks.GIANT_WHEAT);
                
                output.accept(ModItems.CROPRESSED_BEETROOT.get());
                output.accept(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModBlocks.GIANT_BEETROOT.get());
                output.accept(ModItems.FLAVORFUL_ROOTS.get());
                output.accept(ModItems.BEROOT_CAULDRON.get());
                output.accept(ModItems.BEROOT_COOK_BOOK.get());

                output.accept(ModItems.CROPRESSED_NETHERWART.get());
                output.accept(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModBlocks.GIANT_NETHERWART);

                output.accept(ModItems.EXTRACTION_BOTTLE.get());
                output.accept(ModItems.BROKEN_REBREWING_STAND.get());
                output.accept(ModItems.REBREWING_STAND.get());

                if (MoreSnifferFlowers.hasFarmersDelight()){
                    output.accept(ModBlocks.GIANT_ONION.get());
                    output.accept(ModBlocks.GIANT_CABBAGE.get());
                    output.accept(ModBlocks.GIANT_TOMATO.get());
                    output.accept(ModBlocks.GIANT_RICE.get());

                }

                output.accept(ModItems.BOBLING_SPAWN_EGG);
                output.accept(ModItems.CORRUPTED_BOBLING_CORE);
                output.accept(ModItems.CORRUPTED_SLIME_BALL);
                output.accept(ModItems.BOBLING_CORE);
                output.accept(ModItems.VIVICUS_ANTIDOTE);

                output.accept(ModBlocks.DECAYED_LOG.get());
                output.accept(ModBlocks.CORRUPTED_GRASS_BLOCK.get());
                output.accept(ModBlocks.CURED_GRASS_BLOCK.get());
                output.accept(ModBlocks.CORRUPTED_GRASS.get());
                output.accept(ModBlocks.CORRUPTED_TALL_GRASS.get());
                output.accept(ModBlocks.CORRUPTED_WART.get());

                output.accept(ModBlocks.VIVICUS_LOG.get());
                output.accept(ModBlocks.VIVICUS_WOOD.get());
                output.accept(ModBlocks.STRIPPED_VIVICUS_LOG.get());
                output.accept(ModBlocks.STRIPPED_VIVICUS_WOOD.get());
                output.accept(ModBlocks.VIVICUS_PLANKS.get());
                output.accept(ModBlocks.VIVICUS_STAIRS.get());
                output.accept(ModBlocks.VIVICUS_SLAB.get());
                output.accept(ModBlocks.VIVICUS_FENCE.get());
                output.accept(ModBlocks.VIVICUS_FENCE_GATE.get());
                output.accept(ModBlocks.VIVICUS_DOOR.get());
                output.accept(ModBlocks.VIVICUS_TRAPDOOR.get());
                output.accept(ModBlocks.VIVICUS_PRESSURE_PLATE.get());
                output.accept(ModBlocks.VIVICUS_BUTTON.get());
                output.accept(ModBlocks.VIVICUS_LEAVES.get());
                output.accept(ModBlocks.VIVICUS_SAPLING.get());
                output.accept(ModBlocks.VIVICUS_LEAVES_SPROUT.get());
                output.accept(ModItems.VIVICUS_SIGN.get());
                output.accept(ModItems.VIVICUS_HANGING_SIGN.get());
                output.accept(ModItems.VIVICUS_BOAT);
                output.accept(ModItems.VIVICUS_CHEST_BOAT);

                output.accept(ModBlocks.CORRUPTED_LOG.get());
                output.accept(ModBlocks.CORRUPTED_WOOD.get());
                output.accept(ModBlocks.STRIPPED_CORRUPTED_LOG.get());
                output.accept(ModBlocks.STRIPPED_CORRUPTED_WOOD.get());
                output.accept(ModBlocks.CORRUPTED_PLANKS.get());
                output.accept(ModBlocks.CORRUPTED_STAIRS.get());
                output.accept(ModBlocks.CORRUPTED_SLAB.get());
                output.accept(ModBlocks.CORRUPTED_FENCE.get());
                output.accept(ModBlocks.CORRUPTED_FENCE_GATE.get());
                output.accept(ModBlocks.CORRUPTED_DOOR.get());
                output.accept(ModBlocks.CORRUPTED_TRAPDOOR.get());
                output.accept(ModBlocks.CORRUPTED_PRESSURE_PLATE.get());
                output.accept(ModBlocks.CORRUPTED_BUTTON.get());
                output.accept(ModBlocks.CORRUPTED_LEAVES.get());
                output.accept(ModBlocks.CORRUPTED_LEAVES_BUSH.get());
                output.accept(ModBlocks.CORRUPTED_SAPLING.get());
                output.accept(ModBlocks.CORRUPTED_SLUDGE);
                output.accept(ModBlocks.CORRUPTED_SLIME_LAYER);
                output.accept(ModItems.CORRUPTED_SIGN);
                output.accept(ModItems.CORRUPTED_HANGING_SIGN);
                output.accept(ModItems.CORRUPTED_BOAT);
                output.accept(ModItems.CORRUPTED_CHEST_BOAT);

                output.accept(ModItems.BLOCK_PATTERN_CLOUDS.get());
                output.accept(ModItems.BLOCK_PATTERN_EYE.get());
                output.accept(ModItems.BLOCK_PATTERN_COVER.get());
                output.accept(ModItems.BLOCK_PATTERN_DEEPSLATE.get());
                output.accept(ModItems.BLOCK_PATTERN_PAWS.get());
                output.accept(ModItems.BLOCK_PATTERN_HEARTS.get());
                output.accept(ModItems.BLOCK_PATTERN_HONEYCOMB.get());
                output.accept(ModItems.BLOCK_PATTERN_STARS.get());
                output.accept(ModItems.BLOCK_PATTERN_PIPES.get());
                output.accept(ModItems.BLOCK_PATTERN_SPROUTS.get());
                output.accept(ModItems.BLOCK_PATTERN_DIAMOND.get());
                output.accept(ModItems.BLOCK_PATTERN_BUBBLES.get());
                output.accept(ModItems.BLOCK_PATTERN_PRISMARINE.get());
                output.accept(ModItems.BLOCK_PATTERN_FOCUS.get());
                output.accept(ModItems.BLOCK_PATTERN_BRICKS.get());
                output.accept(ModItems.BLOCK_PATTERN_FLOWERS.get());



            })
            .backgroundTexture(MoreSnifferFlowers.loc("textures/gui/container/tab_items.png"))
            .withTabsImage(MoreSnifferFlowers.loc("textures/gui/container/tabs.png"))
            .build()
    );
}