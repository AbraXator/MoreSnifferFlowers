package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).fast().build())));

    public static final RegistryObject<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(ModBlocks.AMBUSH_BOTTOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new TrimMaterialItem(new Item.Properties()));
    public static final RegistryObject<Item> AROMA_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("aroma_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.AROMA));
    public static final RegistryObject<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new DragonflyItem(new Item.Properties()));
    public static final RegistryObject<Item> DYESPRIA = ITEMS.register("dyespria", () -> new DyespriaItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DYESPRIA_SEEDS = ITEMS.register("dyespria_seeds", () -> new ItemNameBlockItem(ModBlocks.DYESPRIA_PLANT.get(), new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
            pTooltip.add(Component.translatableWithFallback("dyespria_seeds.tooltip", "Use shears to hide the dye item").withStyle(ChatFormatting.GOLD));
        }
    });

    public static final RegistryObject<Item> BONMEELIA_SEEDS = ITEMS.register("bonmeelia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONMEELIA.get(), new Item.Properties()));
    public static final RegistryObject<Item> JAR_OF_BONMEEL = ITEMS.register("jar_of_bonmeel", () -> new JarOfBonmeelItem(new Item.Properties()));
    
    public static final RegistryObject<Item> CROPRESSOR = ITEMS.register("cropressor", () -> new ItemNameBlockItem(ModBlocks.CROPRESSOR_OUT.get(), new Item.Properties()));
    public static final RegistryObject<Item> TUBE_PIECE = ITEMS.register("tube_piece", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BELT_PIECE = ITEMS.register("belt_piece", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCRAP_PIECE = ITEMS.register("scrap_piece", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENGINE_PIECE = ITEMS.register("engine_piece", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PRESS_PIECE = ITEMS.register("press_piece", () -> new Item(new Item.Properties()));
    
    public static final RegistryObject<Item> REBREWING_STAND = ITEMS.register("rebrewing_stand", () -> new ItemNameBlockItem(ModBlocks.REBREWING_STAND_BOTTOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROKEN_REBREWING_STAND = ITEMS.register("broken_rebrewing_stand", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRACTION_BOTTLE = ITEMS.register("extraction_bottle", () -> new BottleOfExtractionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EXTRACTED_BOTTLE = ITEMS.register("extracted_bottle", () -> new PotionItem(new Item.Properties().stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
            pTooltip.add(Component.translatableWithFallback("tooltip.extracted_bottle.obtain", "Obtainable using Bottle o' Extraction").withStyle(ChatFormatting.GOLD));
        }
    });
    public static final RegistryObject<Item> REBREWED_POTION = ITEMS.register("rebrewed_potion", () -> new PotionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> REBREWED_SPLASH_POTION = ITEMS.register("rebrewed_splash_potion", () -> new SplashPotionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> REBREWED_LINGERING_POTION = ITEMS.register("rebrewed_lingering_potion", () -> new LingeringPotionItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CROPRESSED_POTATO = ITEMS.register("cropressed_potato", () -> new TrimMaterialItem(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_CARROT = ITEMS.register("cropressed_carrot", () -> new TrimMaterialItem(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_BEETROOT = ITEMS.register("cropressed_beetroot", () -> new TrimMaterialItem(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_NETHERWART = ITEMS.register("cropressed_nether_wart", () -> new TrimMaterialItem(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_WHEAT = ITEMS.register("cropressed_wheat", () -> new TrimMaterialItem(new Item.Properties()));

    public static final RegistryObject<Item> TATER_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("tater_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.TATER));
    public static final RegistryObject<Item> CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("carotene_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.CAROTENE));
    public static final RegistryObject<Item> BEAT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("beat_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.BEAT));
    public static final RegistryObject<Item> NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("nether_wart_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.NETHER_WART));
    public static final RegistryObject<Item> GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("grain_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.GRAIN));

}
