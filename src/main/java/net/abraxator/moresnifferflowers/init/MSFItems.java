package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.BlockPattern;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.entities.boat.ModBoatEntity;
import net.abraxator.moresnifferflowers.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public interface MSFItems {
    DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreSnifferFlowers.MOD_ID);
    DeferredItem<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(MSFBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    DeferredItem<Item> GLOOMBERRY_VINE_SEEDS = ITEMS.register("gloomberry_vine_seeds", () -> new ItemNameBlockItem(MSFBlocks.GLOOMBERRY_VINE.get(), new Item.Properties()));
    DeferredItem<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(Food.DAWNBERRY)));
    DeferredItem<Item> GLOOMBERRY = ITEMS.register("gloomberry", () -> new Item(new Item.Properties().food(Food.GLOOMBERRY)));

    DeferredItem<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(MSFBlocks.AMBUSH_BOTTOM.get(), new Item.Properties()));
    DeferredItem<Item> GARBUSH_SEEDS = ITEMS.register("garbush_seeds", () -> new ItemNameBlockItem(MSFBlocks.GARBUSH_BOTTOM.get(), new Item.Properties()));

    DeferredItem<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(MSFTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    DeferredItem<Item> EVIL_BANNER_PATTERN = ITEMS.register("evil_banner_pattern", () -> new BannerPatternItem(MSFTags.ModBannerPatternTags.EVIL_BANNER_PATTERN, new Item.Properties().stacksTo(1)));

    DeferredItem<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new TrimMaterialItem(new Item.Properties()));
    DeferredItem<Item> GARNET_SHARD = ITEMS.register("garnet_shard", () -> new TrimMaterialItem(new Item.Properties()));

    DeferredItem<Item> AROMA_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("aroma_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.AROMA));
    DeferredItem<Item> CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("carnage_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.CARNAGE));
    DeferredItem<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new DragonflyItem(new Item.Properties()));
    DeferredItem<Item> DYESPRIA = ITEMS.register("dyespria", () -> new DyespriaItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> DYESCRAPIA = ITEMS.register("dyescrapia", () -> new DyescrapiaItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> DYESPRIA_SEEDS = ITEMS.register("dyespria_seeds", () -> new ItemNameBlockItem(MSFBlocks.DYESPRIA_PLANT.get(), new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.dyespria_seeds", "Shear to hide dye").withStyle(ChatFormatting.GOLD));
        }
    });

    DeferredItem<Item> BONMEELIA_SEEDS = ITEMS.register("bonmeelia_seeds", () -> new ItemNameBlockItem(MSFBlocks.BONMEELIA.get(), new Item.Properties()));
    DeferredItem<Item> JAR_OF_BONMEEL = ITEMS.register("jar_of_bonmeel", () -> new JarOfBonmeelItem(new Item.Properties()));
    DeferredItem<Item> BONDRIPIA_SEEDS = ITEMS.register("bondripia_seeds", () -> new ItemNameBlockItem(MSFBlocks.BONDRIPIA.get(), new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.bondripia_seeds", "Plantable underneath an area of 5 blocks in a + shape").withStyle(ChatFormatting.GOLD));
        }
    });

    DeferredItem<Item> BONWILTIA_SEEDS = ITEMS.register("bonwiltia_seeds", () -> new ItemNameBlockItem(MSFBlocks.BONWILTIA.get(), new Item.Properties()));
    DeferredItem<Item> JAR_OF_ACID = ITEMS.register("jar_of_acid", () -> new JarOfAcidItem(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.acid_jar", "Ungrows organic blocks").withStyle(ChatFormatting.GOLD));
        }
    });

    DeferredItem<Item> ACIDRIPIA_SEEDS = ITEMS.register("acidripia_seeds", () -> new ItemNameBlockItem(MSFBlocks.ACIDRIPIA.get(), new Item.Properties()));

    DeferredItem<Item> CROPRESSOR = ITEMS.register("cropressor", () -> new BlockItem(MSFBlocks.CROPRESSOR_OUT.get(), new Item.Properties()));
    DeferredItem<Item> TUBE_PIECE = ITEMS.register("tube_piece", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BELT_PIECE = ITEMS.register("belt_piece", () -> new Item(new Item.Properties()));
    DeferredItem<Item> SCRAP_PIECE = ITEMS.register("scrap_piece", () -> new Item(new Item.Properties()));
    DeferredItem<Item> ENGINE_PIECE = ITEMS.register("engine_piece", () -> new Item(new Item.Properties()));
    DeferredItem<Item> PRESS_PIECE = ITEMS.register("press_piece", () -> new Item(new Item.Properties()));

    DeferredItem<Item> REBREWING_STAND = ITEMS.register("rebrewing_stand", () -> new ItemNameBlockItem(MSFBlocks.REBREWING_STAND_BOTTOM.get(), new Item.Properties()));
    DeferredItem<Item> BROKEN_REBREWING_STAND = ITEMS.register("broken_rebrewing_stand", () -> new Item(new Item.Properties()));
    DeferredItem<Item> EXTRACTION_BOTTLE = ITEMS.register("extraction_bottle", () -> new BottleOfExtractionItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> EXTRACTED_BOTTLE = ITEMS.register("extracted_bottle", () -> new PotionItem(new Item.Properties().stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, context, components, tooltipFlag);
            components.add(Component.translatableWithFallback("tooltip.extracted_bottle.obtain", "Obtainable using Bottle o' Extraction").withStyle(ChatFormatting.GOLD));
        }
    });
    DeferredItem<Item> REBREWED_POTION = ITEMS.register("rebrewed_potion", () -> new PotionItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> REBREWED_SPLASH_POTION = ITEMS.register("rebrewed_splash_potion", () -> new SplashPotionItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> REBREWED_LINGERING_POTION = ITEMS.register("rebrewed_lingering_potion", () -> new LingeringPotionItem(new Item.Properties().stacksTo(1)));
    
    DeferredItem<Item> CROPRESSED_POTATO = ITEMS.register("cropressed_potato", () -> new TrimMaterialItem(new Item.Properties()));
    DeferredItem<Item> CROPRESSED_CARROT = ITEMS.register("cropressed_carrot", () -> new TrimMaterialItem(new Item.Properties()));
    DeferredItem<Item> CROPRESSED_BEETROOT = ITEMS.register("cropressed_beetroot", () -> new TrimMaterialItem(new Item.Properties()));
    DeferredItem<Item> CROPRESSED_NETHERWART = ITEMS.register("cropressed_nether_wart", () -> new TrimMaterialItem(new Item.Properties()));
    DeferredItem<Item> CROPRESSED_WHEAT = ITEMS.register("cropressed_wheat", () -> new TrimMaterialItem(new Item.Properties()));

    DeferredItem<Item> TATER_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("tater_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.TATER));
    DeferredItem<Item> CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("carotene_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.CAROTENE));
    DeferredItem<Item> BEAT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("beat_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.BEAT));
    DeferredItem<Item> NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("nether_wart_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.NETHER_WART));
    DeferredItem<Item> GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("grain_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(MSFTrims.Patterns.GRAIN));

    DeferredItem<Item> VIVICUS_ANTIDOTE = ITEMS.register("vivicus_antidote", () -> new VivicusAntidoteItem(new Item.Properties()));
    DeferredItem<Item> CORRUPTED_BOBLING_CORE = ITEMS.register("corrupted_bobling_core", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BOBLING_CORE = ITEMS.register("bobling_core", () -> new Item(new Item.Properties()));
    DeferredItem<Item> CORRUPTED_SLIME_BALL = ITEMS.register("corrupted_slime_ball", () -> new CorruptedSlimeBallItem(new Item.Properties()));
    
    DeferredItem<Item> CORRUPTED_SIGN = ITEMS.register("corrupted_sign", () -> new SignItem(new Item.Properties(), MSFBlocks.CORRUPTED_SIGN.get(), MSFBlocks.CORRUPTED_WALL_SIGN.get()));
    DeferredItem<Item> CORRUPTED_HANGING_SIGN = ITEMS.register("corrupted_hanging_sign", () -> new HangingSignItem(MSFBlocks.CORRUPTED_HANGING_SIGN.get(), MSFBlocks.CORRUPTED_WALL_HANGING_SIGN.get(), new Item.Properties()));
    DeferredItem<Item> CORRUPTED_BOAT = ITEMS.register("corrupted_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.CORRUPTED, new Item.Properties()));
    DeferredItem<Item> CORRUPTED_CHEST_BOAT = ITEMS.register("corrupted_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.CORRUPTED, new Item.Properties()));

    DeferredItem<Item> VIVICUS_SIGN = ITEMS.register("vivicus_sign", () -> new SignItem(new Item.Properties(), MSFBlocks.VIVICUS_SIGN.get(), MSFBlocks.VIVICUS_WALL_SIGN.get()));
    DeferredItem<Item> VIVICUS_HANGING_SIGN = ITEMS.register("vivicus_hanging_sign", () -> new HangingSignItem(MSFBlocks.VIVICUS_HANGING_SIGN.get(), MSFBlocks.VIVICUS_WALL_HANGING_SIGN.get(), new Item.Properties()));
    DeferredItem<Item> VIVICUS_BOAT = ITEMS.register("vivicus_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.VIVICUS, new Item.Properties()));
    DeferredItem<Item> VIVICUS_CHEST_BOAT = ITEMS.register("vivicus_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.VIVICUS, new Item.Properties()));

    DeferredItem<Item> BOBLING_SPAWN_EGG = ITEMS.register("bobling_spawn_egg", () -> new DeferredSpawnEggItem(MSFEntityTypes.BOBLING, 0x312f35, 0xa55f85, new Item.Properties()));

    DeferredItem<Item> CAULORFLOWER_SEEDS = ITEMS.register("caulorflower_seeds", () -> new ItemNameBlockItem(MSFBlocks.CAULORFLOWER.get(), new Item.Properties()));
    DeferredItem<Item> PATTERNFLOWER_SEEDS = ITEMS.register("patternflower_seeds", () -> new ItemNameBlockItem(MSFBlocks.PATTERNFLOWER.get(), new Item.Properties()));
    DeferredItem<Item> PATTERNSPRIA = ITEMS.register("patternspria", () -> new PatternspriaItem(new Item.Properties().stacksTo(1)));

    DeferredItem<Item> BLOCK_PATTERN_PIPES = ITEMS.register("block_pattern_pipes", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_BRICKS = ITEMS.register("block_pattern_bricks", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_FOCUS = ITEMS.register("block_pattern_focus", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_BUBBLES = ITEMS.register("block_pattern_bubbles", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_CLOUDS = ITEMS.register("block_pattern_clouds", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_DEEPSLATE = ITEMS.register("block_pattern_deepslate", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_DIAMOND = ITEMS.register("block_pattern_diamond", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_EYE = ITEMS.register("block_pattern_eye", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_HEARTS = ITEMS.register("block_pattern_hearts", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_HONEYCOMB = ITEMS.register("block_pattern_honeycomb", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_PAWS = ITEMS.register("block_pattern_paws", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_PRISMARINE = ITEMS.register("block_pattern_prismarine", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_SPROUTS = ITEMS.register("block_pattern_sprouts", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_STARS = ITEMS.register("block_pattern_stars", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_COVER = ITEMS.register("block_pattern_cover", () -> new Item(new Item.Properties()));
    DeferredItem<Item> BLOCK_PATTERN_FLOWERS = ITEMS.register("block_pattern_flowers", () -> new Item(new Item.Properties()));

    DeferredItem<Item> BEROOT_CAULDRON = ITEMS.register("beroot_cauldron", () -> new BlockItem(MSFBlocks.BEROOT_CAULDRON.get(), new Item.Properties()));

    DeferredItem<Item> ROOTED_SOUP = ITEMS.register("rooted_soup", () -> new RootedSoupItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> BEROOT_COOK_BOOK = ITEMS.register("beroot_cook_book", () -> new BerootCookbookItem(new Item.Properties().stacksTo(1)));
    DeferredItem<Item> FLAVORFUL_ROOTS = ITEMS.register("flavorful_roots", () -> new Item(new Item.Properties()));

    DeferredItem<Item> SALTEMONE_SEEDS = ITEMS.register("saltemone_seeds", () -> new SaltemoneSeedsItem(MSFBlocks.SALTEMONE.get(), new Item.Properties()));
    DeferredItem<Item> SOURLEMONE_SEEDS = ITEMS.register("sourlemone_seeds", () -> new SaltemoneSeedsItem(MSFBlocks.SOURLEMONE.get(), new Item.Properties()));
    DeferredItem<Item> SALTY_SPICE = ITEMS.register("salty_spice", () -> new SaltySpiceItem(MSFBlocks.SALTY_CLUMP.get(), new Item.Properties()));
    DeferredItem<Item> SOUR_SPICE = ITEMS.register("sour_spice", () -> new SourSpiceItem(MSFBlocks.SOUR_PUDDLE.get(), new Item.Properties()));
    DeferredItem<Item> FIERY_SPICE = ITEMS.register("fiery_spice", () -> new Item(new Item.Properties()));
    DeferredItem<Item> SWEET_SPICE = ITEMS.register("sweet_spice", () -> new Item(new Item.Properties()));

    DeferredItem<Item> DRIPSALT = ITEMS.register("dripsalt", () -> new BlockItem(MSFBlocks.DRIPSALT.get(), new Item.Properties()));
    DeferredItem<Item> BURNED_SLOT = ITEMS.register("burned_slot", () -> new BurnedSlotItem(new Item.Properties().stacksTo(1)));

    DeferredItem<Item> MUSIC_DISC_BOBLING = ITEMS.register("music_disc_bobling", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MSFSounds.MusicDiscs.BOBLING_BATTLE.getKey())));
    DeferredItem<Item> DISC_FRAGMENT_BOBLING = ITEMS.register("disc_fragment_bobling", () -> new Item(new Item.Properties()));

    DeferredItem<Item> CREATIVE_TAB_ICON = ITEMS.register("creative_tab_icon", () -> new CreativeTabItem(new Item.Properties()));
    DeferredItem<Item> WAND_OF_CUBING = ITEMS.register("wand_of_cubing", () -> new WandOfCubingItem(new Item.Properties()));
    DeferredItem<Item> DEBUG_FLOWER = ITEMS.register("debug_flower", () -> new DebugFlowerItem(new Item.Properties()));
    DeferredItem<Item> PLACEHOLDER = ITEMS.registerItem("placeholder", Item::new, new Item.Properties());

    interface ModelProperties {
        static void register() {
            ItemProperties.register(DYESPRIA.get(), MoreSnifferFlowers.loc("color"), (stack, level, entity, pSeed) -> {
                if(!Dye.getDyeFromDyespria(stack).isEmpty()) {
                    return 1.0F;
                } else {
                    return 0.0F;
                }
            });

            ItemProperties.register(DRAGONFLY.get(), MoreSnifferFlowers.loc("og"), (stack, level, entity, pSeed) -> {
                Component component = stack.get(DataComponents.CUSTOM_NAME);
                if(component != null && component.getString().equals("og")) {
                    return 1.0F;
                } else {
                    return 0.0F;
                }
            });

            ItemProperties.register(PATTERNSPRIA.get(), MoreSnifferFlowers.loc("patternspria"), (stack, level, entity, pSeed) -> {
                if(!BlockPattern.fromPatternspria(stack).equals(BlockPattern.EMPTY)) {
                    return 1.0F;
                } else {
                    return 0.0F;
                }
            });
        }
    }

    interface Food {
        FoodProperties DAWNBERRY = builder(4, 0.6).fast().build();
        FoodProperties GLOOMBERRY = builder(4, 0.6).fast()
                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100), 0.8F)
                .effect(() -> new MobEffectInstance(MobEffects.POISON, 100), 0.8F)
                .build();

        static FoodProperties.Builder builder(int nutrition, double saturationModifier) {
            return new FoodProperties.Builder().nutrition(nutrition).saturationModifier((float) saturationModifier);
        }
    }
}