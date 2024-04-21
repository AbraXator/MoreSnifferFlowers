package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final Item DAWNBERRY_VINE_SEEDS = registerItem( "dawnberry_vine_seeds", new AliasedBlockItem(ModBlocks.DAWNBERRY_VINE, new FabricItemSettings()));
    public static final Item DAWNBERRY = registerItem("dawnberry", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).snack().build())));

    public static final Item AMBUSH_SEEDS = registerItem("ambush_seeds", new AliasedBlockItem(ModBlocks.AMBUSH, new FabricItemSettings()));
//    public static final Item AMBUSH_BANNER_PATTERN = registerItem("ambush_banner_pattern", new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new FabricItemSettings().maxCount(1)));
    public static final Item AMBER_SHARD = registerItem("amber_shard", new Item(new FabricItemSettings()));
//        @Override
//         public void appendTooltip(ItemStack pStack, @Nullable World pWorld, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
//          super.appendTooltip(pStack, pWorld, pTooltipComponents, pIsAdvanced);
//          pTooltipComponents.add(Text.translatable("tooltip.amber_shard.usage").formatted(Formatting.GOLD));
//      }
//    };

    public static final Item AROMA_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("aroma_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.AROMA));
    public static final Item DRAGONFLY = registerItem("dragonfly", new Item(new FabricItemSettings().food(ModFoods.DRAGONFLY)));
    public static final Item DYESPRIA = registerItem("dyespria", new Item(new FabricItemSettings().maxCount(1)));
//    public static final Item DYESPRIA_SEEDS = registerItem("dyespria_seeds", new AliasedBlockItem(ModBlocks.DYESPRIA_PLANT, new FabricItemSettings()));

    public static final Item BONMEELIA_SEEDS = registerItem("bonmeelia_seeds", new AliasedBlockItem(ModBlocks.BONMEELIA, new FabricItemSettings()));
    public static final Item JAR_OF_BONMEEL = registerItem("jar_of_bonmeel", new JarOfBonmeelItem(new FabricItemSettings()));

 //   public static final Item CROPRESSOR = registerItem("cropressor", new AliasedBlockItem(ModBlocks.CROPRESSOR_OUT, new FabricItemSettings()));
    public static final Item TUBE_PIECE = registerItem("tube_piece", new Item(new FabricItemSettings()));
    public static final Item BELT_PIECE = registerItem("belt_piece", new Item(new FabricItemSettings()));
    public static final Item SCRAP_PIECE = registerItem("scrap_piece", new Item(new FabricItemSettings()));
    public static final Item ENGINE_PIECE = registerItem("engine_piece", new Item(new FabricItemSettings()));
    public static final Item PRESS_PIECE = registerItem("press_piece", new Item(new FabricItemSettings()));

//    public static final Item REBREWING_STAND = registerItem("rebrewing_stand", new AliasedBlockItem(ModBlocks.REBREWING_STAND_BOTTOM, new FabricItemSettings()));
    public static final Item BROKEN_REBREWING_STAND = registerItem("broken_rebrewing_stand", new Item(new FabricItemSettings()));
    public static final Item EXTRACTION_BOTTLE = registerItem("extraction_bottle", new Item(new FabricItemSettings()));
    public static final Item EXTRACTED_BOTTLE = registerItem("extracted_bottle", new Item(new FabricItemSettings()));
//        @Override
//         public void appendTooltip(ItemStack pStack, @Nullable World pWorld, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
//          super.appendTooltip(pStack, pWorld, pTooltipComponents, pIsAdvanced);
//          pTooltipComponents.add(Text.translatable("tooltip.extracted_bottle.obtain").formatted(Formatting.GOLD));
//      }
//    };
    public static final Item REBREWED_POTION = registerItem("rebrewed_potion", new Item(new FabricItemSettings()));

    public static final Item CROPRESSED_POTATO = registerItem("cropressed_potato", new Item(new FabricItemSettings()));
    public static final Item CROPRESSED_CARROT = registerItem("cropressed_carrot", new Item(new FabricItemSettings()));
    public static final Item CROPRESSED_BEETROOT = registerItem("cropressed_beetroot", new Item(new FabricItemSettings()));
    public static final Item CROPRESSED_NETHERWART = registerItem("cropressed_netherwart", new Item(new FabricItemSettings()));
    public static final Item CROPRESSED_WHEAT = registerItem("cropressed_wheat", new Item(new FabricItemSettings()));

    public static final Item TATER_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("tater_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.TATER));
    public static final Item CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("carotene_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.CAROTENE));
    public static final Item BEAT_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("beat_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.BEAT));
    public static final Item NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("nether_wart_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.NETHER_WART));
    public static final Item GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("grain_armor_trim_smithing_template", SmithingTemplateItem.of(ModTrimPatterns.GRAIN));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoreSnifferFlowers.MOD_ID, name), item);
    }
        public static void registerModItems() {
            MoreSnifferFlowers.LOGGER.info("Registering Items for" + MoreSnifferFlowers.MOD_ID);
        }

}
