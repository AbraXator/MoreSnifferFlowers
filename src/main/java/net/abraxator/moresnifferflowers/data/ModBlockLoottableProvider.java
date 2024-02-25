package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.blocks.DawnberryVineBlock;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.math.Direction;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLoottableProvider extends BlockLootTableGenerator {
    public ModBlockLoottableProvider() {
        super(Set.of(), FeatureFlags.FEATURE_MANAGER.getFeatureSet());
    }

    @Override
    protected void generate() {
        addDrop(ModBlocks.DAWNBERRY_VINE.get(), LootTable.builder()
                .pool(LootPool.builder().with(this.applyExplosionDecay(ModItems.DAWNBERRY_VINE_SEEDS.get(), ItemEntry.builder(ModItems.DAWNBERRY_VINE_SEEDS.get()).apply(Direction.values(), (p_251536_) ->
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(-1.0F), true)))))
                .withPool(LootPool.builder().with(ItemEntry.builder(ModBlocks.DAWNBERRY_VINE.get()))
                        .when(BlockStatePropertyLootCondition.builder(ModBlocks.DAWNBERRY_VINE.get())
                                .setProperties(StatePredicate.Builder.create()
                                        .exactMatch(DawnberryVineBlock.AGE, 4)))));
        addDrop(ModBlocks.AMBER.get(), LootTable.builder()
                .pool(LootPool.builder()
                        .conditionally(WITH_SILK_TOUCH)
                        .with(ItemEntry.builder(ModBlocks.AMBER.get())))
                .withPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(WITHOUT_SILK_TOUCH)
                        .with(ItemEntry.builder(ModItems.AMBUSH_BANNER_PATTERN.get()))
                        .add(ItemEntry.builder(ModItems.AMBER_SHARD.get()))
                        .add(ItemEntry.builder(ModItems.DRAGONFLY.get()))
                        .add(ItemEntry.builder(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()))
                        .add(ItemEntry.builder(ModItems.AMBUSH_SEEDS.get()))
                        .add(ItemEntry.builder(ModItems.DAWNBERRY_VINE_SEEDS.get()))
                        .add(ItemEntry.builder(ModBlocks.CAULORFLOWER.get().asItem()))
                        .add(ItemEntry.builder(ModItems.BONMEELIA_SEEDS.get()))
                        .add(ItemEntry.builder(Items.TORCHFLOWER_SEEDS))
                        .add(ItemEntry.builder(Items.PITCHER_POD))));
        addDrop(ModBlocks.BOBLING_HEAD.get());
        addDrop(ModBlocks.AMBUSH.get());
        addDrop(ModBlocks.CAULORFLOWER.get());
        addDrop(ModBlocks.GIANT_CARROT.get(), drops(Items.CARROT, UniformLootNumberProvider.create(1, 4)));
        addDrop(ModBlocks.GIANT_POTATO.get(), drops(Items.POTATO, UniformLootNumberProvider.create(1, 4)));
        addDrop(ModBlocks.GIANT_NETHERWART.get(), drops(Items.NETHER_WART, UniformLootNumberProvider.create(1, 4)));
        addDrop(ModBlocks.GIANT_BEETROOT.get(), drops(Items.BEETROOT, UniformLootNumberProvider.create(1, 4)));
        addDrop(ModBlocks.GIANT_WHEAT.get(), drops(Items.WHEAT, UniformLootNumberProvider.create(1, 4)));
        addDrop(ModBlocks.BONMEELIA.get(), LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.BONMEELIA_SEEDS.get())))
                .withPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.JAR_OF_BONMEEL.get()))
                        .when(BlockStatePropertyLootCondition.builder(ModBlocks.BONMEELIA.get())
                                .setProperties(StatePredicate.Builder.create()
                                        .exactMatch(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE)
                                        .exactMatch(BonmeeliaBlock.HAS_BOTTLE, true))))
                .withPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.GLASS_BOTTLE))
                        .conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.BONMEELIA.get())
                                .setProperties(StatePredicate.Builder.create()
                                        .exactMatch(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE))
                                .invert()
                        .and(BlockStatePropertyLootCondition.builder(ModBlocks.BONMEELIA.get())
                                .setProperties((StatePredicate.Builder.create()
                                        .exactMatch(BonmeeliaBlock.HAS_BOTTLE, true)))))));
        addDrop(ModBlocks.CROPRESSOR.get());
        addDrop(ModBlocks.MORE_SNIFFER_FLOWER.get());
    }

    private LootTable.Builder createGiantCropBuilder(Block block, ItemConvertible pItem) {
        return createGiantCropBuilder(block, pItem, Items.AIR);
    }

    private LootTable.Builder createGiantCropBuilder(Block block, ItemConvertible pItem, ItemConvertible specialDrop) {
        return LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(this.applyExplosionDecay(pItem, ItemEntry.builder(pItem)))
                        .conditionally(BlockStatePropertyLootCondition.builder(block)
                                .properties(StatePredicate.Builder.create()
                                        .exactMatch(GiantCropBlock.IS_CENTER, true))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(this.applyExplosionDecay(specialDrop, ItemEntry.builder(specialDrop))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registries.BLOCK
                .stream()
                .filter(block -> Registries.BLOCK.getId(block).getNamespace().equals(MoreSnifferFlowers.MOD_ID))
                .collect(Collectors.toSet());
    }
}
