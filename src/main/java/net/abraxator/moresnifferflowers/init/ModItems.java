package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.boat.ModBoatEntity;
import net.abraxator.moresnifferflowers.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreSnifferFlowers.MOD_ID);
    public static final DeferredItem<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final DeferredItem<Item> GLOOMBERRY_VINE_SEEDS = ITEMS.register("gloomberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.GLOOMBERRY_VINE.get(), new Item.Properties()));
    public static final DeferredItem<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(ModFoods.DAWNBERRY)));
    public static final DeferredItem<Item> GLOOMBERRY = ITEMS.register("gloomberry", () -> new Item(new Item.Properties().food(ModFoods.GLOOMBERRY)));

    public static final DeferredItem<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(ModBlocks.AMBUSH_BOTTOM.get(), new Item.Properties()));
    public static final DeferredItem<Item> GARBUSH_SEEDS = ITEMS.register("garbush_seeds", () -> new ItemNameBlockItem(ModBlocks.GARBUSH_BOTTOM.get(), new Item.Properties()));

    public static final DeferredItem<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EVIL_BANNER_PATTERN = ITEMS.register("evil_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.EVIL_BANNER_PATTERN, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new TrimMaterialItem(new Item.Properties()));
    public static final DeferredItem<Item> GARNET_SHARD = ITEMS.register("garnet_shard", () -> new TrimMaterialItem(new Item.Properties()));

    public static final DeferredItem<Item> AROMA_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("aroma_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.AROMA));
    public static final DeferredItem<Item> CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("carnage_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.CARNAGE));
    public static final DeferredItem<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new DragonflyItem(new Item.Properties()));
    public static final DeferredItem<Item> DYESPRIA = ITEMS.register("dyespria", () -> new DyespriaItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DYESCRAPIA = ITEMS.register("dyescrapia", () -> new DyescrapiaItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DYESPRIA_SEEDS = ITEMS.register("dyespria_seeds", () -> new ItemNameBlockItem(ModBlocks.DYESPRIA_PLANT.get(), new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.dyespria_seeds", "Shear to hide dye").withStyle(ChatFormatting.GOLD));
        }
    });

    public static final DeferredItem<Item> BONMEELIA_SEEDS = ITEMS.register("bonmeelia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONMEELIA.get(), new Item.Properties()));
    public static final DeferredItem<Item> JAR_OF_BONMEEL = ITEMS.register("jar_of_bonmeel", () -> new JarOfBonmeelItem(new Item.Properties()));
    public static final DeferredItem<Item> BONDRIPIA_SEEDS = ITEMS.register("bondripia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONDRIPIA.get(), new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.bondripia_seeds", "Plantable underneath an area of 5 blocks in a + shape").withStyle(ChatFormatting.GOLD));
        }
    });

    public static final DeferredItem<Item> BONWILTIA_SEEDS = ITEMS.register("bonwiltia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONWILTIA.get(), new Item.Properties()));
    public static final DeferredItem<Item> JAR_OF_ACID = ITEMS.register("jar_of_acid", () -> new JarOfAcidItem(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.acid_jar", "Ungrows organic blocks").withStyle(ChatFormatting.GOLD));
        }
    });

    public static final DeferredItem<Item> ACIDRIPIA_SEEDS = ITEMS.register("acidripia_seeds", () -> new ItemNameBlockItem(ModBlocks.ACIDRIPIA.get(), new Item.Properties()));

    public static final DeferredItem<Item> CROPRESSOR = ITEMS.register("cropressor", () -> new BlockItem(ModBlocks.CROPRESSOR_OUT.get(), new Item.Properties()));
    public static final DeferredItem<Item> TUBE_PIECE = ITEMS.register("tube_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BELT_PIECE = ITEMS.register("belt_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SCRAP_PIECE = ITEMS.register("scrap_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ENGINE_PIECE = ITEMS.register("engine_piece", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRESS_PIECE = ITEMS.register("press_piece", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> REBREWING_STAND = ITEMS.register("rebrewing_stand", () -> new ItemNameBlockItem(ModBlocks.REBREWING_STAND_BOTTOM.get(), new Item.Properties()));
    public static final DeferredItem<Item> BROKEN_REBREWING_STAND = ITEMS.register("broken_rebrewing_stand", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EXTRACTION_BOTTLE = ITEMS.register("extraction_bottle", () -> new BottleOfExtractionItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EXTRACTED_BOTTLE = ITEMS.register("extracted_bottle", () -> new PotionItem(new Item.Properties().stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.extracted_bottle.obtain", "Obtainable using Bottle o' Extraction").withStyle(ChatFormatting.GOLD));
        }
    });
    public static final DeferredItem<Item> REBREWED_POTION = ITEMS.register("rebrewed_potion", () -> new PotionItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> REBREWED_SPLASH_POTION = ITEMS.register("rebrewed_splash_potion", () -> new SplashPotionItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> REBREWED_LINGERING_POTION = ITEMS.register("rebrewed_lingering_potion", () -> new LingeringPotionItem(new Item.Properties().stacksTo(1)));
    
    public static final DeferredItem<Item> CROPRESSED_POTATO = ITEMS.register("cropressed_potato", () -> new TrimMaterialItem(new Item.Properties()));
    public static final DeferredItem<Item> CROPRESSED_CARROT = ITEMS.register("cropressed_carrot", () -> new TrimMaterialItem(new Item.Properties()));
    public static final DeferredItem<Item> CROPRESSED_BEETROOT = ITEMS.register("cropressed_beetroot", () -> new TrimMaterialItem(new Item.Properties()));
    public static final DeferredItem<Item> CROPRESSED_NETHERWART = ITEMS.register("cropressed_nether_wart", () -> new TrimMaterialItem(new Item.Properties()));
    public static final DeferredItem<Item> CROPRESSED_WHEAT = ITEMS.register("cropressed_wheat", () -> new TrimMaterialItem(new Item.Properties()));

    public static final DeferredItem<Item> TATER_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("tater_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.TATER));
    public static final DeferredItem<Item> CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("carotene_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.CAROTENE));
    public static final DeferredItem<Item> BEAT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("beat_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.BEAT));
    public static final DeferredItem<Item> NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("nether_wart_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.NETHER_WART));
    public static final DeferredItem<Item> GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("grain_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.GRAIN));

    public static final DeferredItem<Item> VIVICUS_ANTIDOTE = ITEMS.register("vivicus_antidote", () -> new VivicusAntidoteItem(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_BOBLING_CORE = ITEMS.register("corrupted_bobling_core", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BOBLING_CORE = ITEMS.register("bobling_core", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_SLIME_BALL = ITEMS.register("corrupted_slime_ball", () -> new CorruptedSlimeBallItem(new Item.Properties()));
    
    public static final DeferredItem<Item> CORRUPTED_SIGN = ITEMS.register("corrupted_sign", () -> new SignItem(new Item.Properties(), ModBlocks.CORRUPTED_SIGN.get(), ModBlocks.CORRUPTED_WALL_SIGN.get()));
    public static final DeferredItem<Item> CORRUPTED_HANGING_SIGN = ITEMS.register("corrupted_hanging_sign", () -> new HangingSignItem(ModBlocks.CORRUPTED_HANGING_SIGN.get(), ModBlocks.CORRUPTED_WALL_HANGING_SIGN.get(), new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_BOAT = ITEMS.register("corrupted_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.CORRUPTED, new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_CHEST_BOAT = ITEMS.register("corrupted_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.CORRUPTED, new Item.Properties()));

    public static final DeferredItem<Item> VIVICUS_SIGN = ITEMS.register("vivicus_sign", () -> new SignItem(new Item.Properties(), ModBlocks.VIVICUS_SIGN.get(), ModBlocks.VIVICUS_WALL_SIGN.get()));
    public static final DeferredItem<Item> VIVICUS_HANGING_SIGN = ITEMS.register("vivicus_hanging_sign", () -> new HangingSignItem(ModBlocks.VIVICUS_HANGING_SIGN.get(), ModBlocks.VIVICUS_WALL_HANGING_SIGN.get(), new Item.Properties()));
    public static final DeferredItem<Item> VIVICUS_BOAT = ITEMS.register("vivicus_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.VIVICUS, new Item.Properties()));
    public static final DeferredItem<Item> VIVICUS_CHEST_BOAT = ITEMS.register("vivicus_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.VIVICUS, new Item.Properties()));

    public static final DeferredItem<Item> BOBLING_SPAWN_EGG = ITEMS.register("bobling_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.BOBLING, 0x312f35, 0xa55f85, new Item.Properties()));

    public static final DeferredItem<Item> CAULORFLOWER_SEEDS = ITEMS.register("caulorflower_seeds", () -> new ItemNameBlockItem(ModBlocks.CAULORFLOWER.get(), new Item.Properties()));
    public static final DeferredItem<Item> PATTERNFLOWER_SEEDS = ITEMS.register("patternflower_seeds", () -> new ItemNameBlockItem(ModBlocks.PATTERNFLOWER.get(), new Item.Properties()));
    public static final DeferredItem<Item> PATTERNSPRIA = ITEMS.register("patternspria", () -> new PatternspriaItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BLOCK_PATTERN_PIPES = ITEMS.register("block_pattern_pipes", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_BRICKS = ITEMS.register("block_pattern_bricks", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_FOCUS = ITEMS.register("block_pattern_focus", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_BUBBLES = ITEMS.register("block_pattern_bubbles", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_CLOUDS = ITEMS.register("block_pattern_clouds", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_DEEPSLATE = ITEMS.register("block_pattern_deepslate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_DIAMOND = ITEMS.register("block_pattern_diamond", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_EYE = ITEMS.register("block_pattern_eye", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_HEARTS = ITEMS.register("block_pattern_hearts", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_HONEYCOMB = ITEMS.register("block_pattern_honeycomb", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_PAWS = ITEMS.register("block_pattern_paws", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_PRISMARINE = ITEMS.register("block_pattern_prismarine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_SPROUTS = ITEMS.register("block_pattern_sprouts", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_STARS = ITEMS.register("block_pattern_stars", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_COVER = ITEMS.register("block_pattern_cover", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PATTERN_FLOWERS = ITEMS.register("block_pattern_flowers", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BEROOT_CAULDRON = ITEMS.register("beroot_cauldron", () -> new BlockItem(ModBlocks.BEROOT_CAULDRON.get(), new Item.Properties()));

    public static final DeferredItem<Item> ROOTED_SOUP = ITEMS.register("rooted_soup", () -> new RootedSoupItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BEROOT_COOK_BOOK = ITEMS.register("beroot_cook_book", () -> new BerootCookbookItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FLAVORFUL_ROOTS = ITEMS.register("flavorful_roots", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SALTEMONE_SEEDS = ITEMS.register("saltemone_seeds", () -> new SaltemoneSeedsItem(ModBlocks.SALTEMONE.get(), new Item.Properties()));
    public static final DeferredItem<Item> SOURLEMONE_SEEDS = ITEMS.register("sourlemone_seeds", () -> new SaltemoneSeedsItem(ModBlocks.SOURLEMONE.get(), new Item.Properties()));
    public static final DeferredItem<Item> SALTY_SPICE = ITEMS.register("salty_spice", () -> new SaltySpiceItem(ModBlocks.SALTY_CLUMP.get(), new Item.Properties()));
    public static final DeferredItem<Item> SOUR_SPICE = ITEMS.register("sour_spice", () -> new SourSpiceItem(ModBlocks.SOUR_PUDDLE.get(), new Item.Properties()));
    public static final DeferredItem<Item> FIERY_SPICE = ITEMS.register("fiery_spice", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SWEET_SPICE = ITEMS.register("sweet_spice", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRIPSALT = ITEMS.register("dripsalt", () -> new BlockItem(ModBlocks.DRIPSALT.get(), new Item.Properties()));
    public static final DeferredItem<Item> BURNED_SLOT = ITEMS.register("burned_slot", () -> new BurnedSlotItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CREATIVE_TAB_ICON = ITEMS.register("creative_tab_icon", () -> new CreativeTabItem(new Item.Properties()));
    public static final DeferredItem<Item> WAND_OF_CUBING = ITEMS.register("wand_of_cubing", () -> new WandOfCubingItem(new Item.Properties()));
    public static final DeferredItem<Item> DEBUG_FLOWER = ITEMS.register("debug_flower", () -> new DebugFlowerItem(new Item.Properties()));
    public static final DeferredItem<Item> PLACEHOLDER = ITEMS.register("placeholder", () -> new Item(new Item.Properties()));

}