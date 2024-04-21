package net.abraxator.moresnifferflowers.data.loot;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.blocks.DawnberryVineBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.math.Direction;


import java.util.Set;
import java.util.stream.Collectors;

class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {
    public ModBlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

    }

//    @Override
//    public void generate() {
//        LootTable.Builder(ModBlocks.DAWNBERRY_VINE, LootTable.Builder
//                (LootPool.builder(this.applyExplosionDecay(ModItems.DAWNBERRY_VINE_SEEDS, ItemEntry.builder(ModItems.DAWNBERRY_VINE_SEEDS).apply(Direction.values(), (p_251536_) ->
//                        SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F), true)
//                                .build(BlockStatePropertyLootCondition.builder(ModBlocks.DAWNBERRY_VINE)
//                                        .setProperties(StatePropertiesPredicate.Builder.properties()
//                                                .hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))))
//                .withPool(LootPool.lootPool().addDrop(ItemEntry.builder(ModBlocks.DAWNBERRY_VINE))
//                        .when(ItemEntryBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE)
//                                .setProperties(StatePropertiesPredicate.Builder.properties()
//                                        .hasProperty(DawnberryVineBlock.AGE, 4)))));
//
//        LootCondition.Builder HAS_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create()
//                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.ANY)));
//
//        addDrop(ModBlocks.AMBER, LootTable.builder()
//                .(HAS_SILK_TOUCH)
//                        .addDrop(ItemEntry.builder(ModBlocks.AMBER)))
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .when(WITHOUT_SILK_TOUCH)
//                        //COMMON
//                        .addDrop(ItemEntry.builder(Items.COAL).Weight(100))
//                        .addDrop(ItemEntry.builder(Items.EMERALD).Weight(100))
//                        .addDrop(ItemEntry.builder(Items.STICK).Weight(100))
//                        .addDrop(ItemEntry.builder(ModItems.AMBER_SHARD).Weight(100))
//                        //UNCOMMON
//                        .addDrop(ItemEntry.builder(Items.CARROT).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.POTATO).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.BEETROOT).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.BEETROOT_SEEDS).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.NETHER_WART).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.WHEAT).Weight(50))
//                        .addDrop(ItemEntry.builder(Items.WHEAT_SEEDS).Weight(50))
//                        .addDrop(ItemEntry.builder(ModItems.DRAGONFLY).Weight(50))
//                        //RARE
//                        .addDrop(ItemEntry.builder(Items.SNORT_POTTERY_SHERD).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.BELT_PIECE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.ENGINE_PIECE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.SCRAP_PIECE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.TUBE_PIECE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.PRESS_PIECE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.AMBUSH_BANNER_PATTERN).Weight(25))
//                        .addDrop(ItemEntry.builder(ModItems.EXTRACTION_BOTTLE).Weight(25))
//                        //VERY RARE
//                        .addDrop(ItemEntry.builder(Items.TORCHFLOWER_SEEDS).Weight(12))
//                        .addDrop(ItemEntry.builder(Items.PITCHER_POD).Weight(12))
//                        .addDrop(ItemEntry.builder(Items.SNIFFER_EGG).Weight(12))
//                        .addDrop(ItemEntry.builder(ModItems.DAWNBERRY_VINE_SEEDS).Weight(12))
//                        .addDrop(ItemEntry.builder(ModItems.AMBUSH_SEEDS).Weight(12))
//                        .addDrop(ItemEntry.builder(ModItems.DYESPRIA_SEEDS).Weight(12))
//                        .addDrop(ItemEntry.builder(ModItems.BONMEELIA_SEEDS).Weight(12))));
//
//        addDrop(ModBlocks.BOBLING_HEAD);
//        addDrop(ModBlocks.AMBUSH);
//        addDrop(ModBlocks.CAULORFLOWER, drops(ModBlocks.CAULORFLOWER.asItem()));
//        addDrop(ModBlocks.GIANT_CARROT, BlockLootTableGenerator.drops(Items.CARROT, UniformLootNumberProvider.between(1, 4)));
//        addDrop(ModBlocks.GIANT_POTATO, createSingleItemTable(Items.POTATO, UniformGenerator.between(1, 4)));
//        addDrop(ModBlocks.GIANT_NETHERWART, createSingleItemTable(Items.NETHER_WART, UniformGenerator.between(1, 4)));
//        addDrop(ModBlocks.GIANT_BEETROOT, createSingleItemTable(Items.BEETROOT, UniformGenerator.between(1, 4)));
//        addDrop(ModBlocks.GIANT_WHEAT, createSingleItemTable(Items.WHEAT, UniformGenerator.between(1, 4)));
//        addDrop(ModBlocks.BONMEELIA, LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .addDrop(ItemEntry.builder(ModItems.BONMEELIA_SEEDS)))
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .addDrop(ItemEntry.builder(ModItems.JAR_OF_BONMEEL))
//                        .when(ItemEntryBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA)
//                                .setProperties(StatePropertiesPredicate.Builder.properties()
//                                        .hasProperty(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE)
//                                        .hasProperty(BonmeeliaBlock.HAS_BOTTLE, true))))
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                        .addDrop(ItemEntry.builder(Items.GLASS_BOTTLE))
//                        .when(ItemEntryBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA)
//                                .setProperties(StatePropertiesPredicate.Builder.properties()
//                                        .hasProperty(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE))
//                                .invert()
//                                .and(ItemEntryBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA)
//                                        .setProperties((StatePropertiesPredicate.Builder.properties()
//                                                .hasProperty(BonmeeliaBlock.HAS_BOTTLE, true)))))));
//        dropSelf(ModBlocks.CROPRESSOR_OUT);
//        dropSelf(ModBlocks.CROPRESSOR_CENTER);
//        dropSelf(ModBlocks.MORE_SNIFFER_FLOWER);
//        dropSelf(ModBlocks.REBREWING_STAND_BOTTOM);
//        dropOther(ModBlocks.REBREWING_STAND_TOP, Items.AIR);
//        dropSelf(ModBlocks.DYESPRIA_PLANT);
//    }
//
//    @Override
//    protected Iterable<Block> getKnownBlocks() {
//        return ForgeRegistries.BLOCKS.getValues()
//                .stream()
//                .filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(MoreSnifferFlowers.MOD_ID))
//                .collect(Collectors.toSet());
//    }
}
