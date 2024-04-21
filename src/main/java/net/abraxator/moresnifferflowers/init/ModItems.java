package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final Item DAWNBERRY_VINE_SEEDS = Registry.register(Registries.ITEM, "dawnberry_vine_seeds", new AliasedBlockItem(ModBlocks.DAWNBERRY_VINE, new Item.Settings()));
    public static final Item DAWNBERRY = Registry.register(Registries.ITEM,"dawnberry", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).snack().build())));

    public static final Item AMBUSH_SEEDS = Registry.register(Registries.ITEM,"ambush_seeds", new AliasedBlockItem(ModBlocks.AMBUSH, new Item.Settings()));
//    public static final Item AMBUSH_BANNER_PATTERN = Registry.register(Registries.ITEM,"ambush_banner_pattern", new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Settings().maxCount(1)));
    public static final Item AMBER_SHARD = Registry.register(Registries.ITEM,"amber_shard", new Item(new Item.Settings()));
//        @Override
//         public void appendTooltip(ItemStack pStack, @Nullable World pWorld, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
//          super.appendTooltip(pStack, pWorld, pTooltipComponents, pIsAdvanced);
//          pTooltipComponents.add(Text.translatable("tooltip.amber_shard.usage").formatted(Formatting.GOLD));
//      }
//    };

    public static final Item AROMA_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"aroma_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.AROMA));
    public static final Item DRAGONFLY = Registry.register(Registries.ITEM,"dragonfly", new Item(new Item.Settings().food(ModFoods.DRAGONFLY)));
    public static final Item DYESPRIA = Registry.register(Registries.ITEM,"dyespria", new DyespriaItem(new Item.Settings().maxCount(1)));
//    public static final Item DYESPRIA_SEEDS = Registry.register(Registries.ITEM,"dyespria_seeds", new AliasedBlockItem(ModBlocks.DYESPRIA_PLANT, new Item.Settings()));

    public static final Item BONMEELIA_SEEDS = Registry.register(Registries.ITEM,"bonmeelia_seeds", new AliasedBlockItem(ModBlocks.BONMEELIA, new Item.Settings()));
    public static final Item JAR_OF_BONMEEL = Registry.register(Registries.ITEM,"jar_of_bonmeel", new JarOfBonmeelItem(new Item.Settings()));

 //   public static final Item CROPRESSOR = Registry.register(Registries.ITEM,"cropressor", new AliasedBlockItem(ModBlocks.CROPRESSOR_OUT, new Item.Settings()));
    public static final Item TUBE_PIECE = Registry.register(Registries.ITEM,"tube_piece", new Item(new Item.Settings()));
    public static final Item BELT_PIECE = Registry.register(Registries.ITEM,"belt_piece", new Item(new Item.Settings()));
    public static final Item SCRAP_PIECE = Registry.register(Registries.ITEM,"scrap_piece", new Item(new Item.Settings()));
    public static final Item ENGINE_PIECE = Registry.register(Registries.ITEM,"engine_piece", new Item(new Item.Settings()));
    public static final Item PRESS_PIECE = Registry.register(Registries.ITEM,"press_piece", new Item(new Item.Settings()));

//    public static final Item REBREWING_STAND = Registry.register(Registries.ITEM,"rebrewing_stand", new AliasedBlockItem(ModBlocks.REBREWING_STAND_BOTTOM, new Item.Settings()));
    public static final Item BROKEN_REBREWING_STAND = Registry.register(Registries.ITEM,"broken_rebrewing_stand", new Item(new Item.Settings()));
    public static final Item EXTRACTION_BOTTLE = Registry.register(Registries.ITEM,"extraction_bottle", new Item(new Item.Settings()));
    public static final Item EXTRACTED_BOTTLE = Registry.register(Registries.ITEM,"extracted_bottle", new Item(new Item.Settings()));
//        @Override
//         public void appendTooltip(ItemStack pStack, @Nullable World pWorld, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
//          super.appendTooltip(pStack, pWorld, pTooltipComponents, pIsAdvanced);
//          pTooltipComponents.add(Text.translatable("tooltip.extracted_bottle.obtain").formatted(Formatting.GOLD));
//      }
//    };
    public static final Item REBREWED_POTION = Registry.register(Registries.ITEM,"rebrewed_potion", new Item(new Item.Settings()));

    public static final Item CROPRESSED_POTATO = Registry.register(Registries.ITEM,"cropressed_potato", new Item(new Item.Settings()));
    public static final Item CROPRESSED_CARROT = Registry.register(Registries.ITEM,"cropressed_carrot", new Item(new Item.Settings()));
    public static final Item CROPRESSED_BEETROOT = Registry.register(Registries.ITEM,"cropressed_beetroot", new Item(new Item.Settings()));
    public static final Item CROPRESSED_NETHERWART = Registry.register(Registries.ITEM,"cropressed_netherwart", new Item(new Item.Settings()));
    public static final Item CROPRESSED_WHEAT = Registry.register(Registries.ITEM,"cropressed_wheat", new Item(new Item.Settings()));

    public static final Item TATER_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"tater_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.TATER));
    public static final Item CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"carotene_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.CAROTENE));
    public static final Item BEAT_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"beat_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.BEAT));
    public static final Item NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"nether_wart_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.NETHER_WART));
    public static final Item GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = Registry.register(Registries.ITEM,"grain_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.GRAIN));


        public static void registerModItems() {
            MoreSnifferFlowers.LOGGER.info("Registering Items for" + MoreSnifferFlowers.MOD_ID);
        }

}
