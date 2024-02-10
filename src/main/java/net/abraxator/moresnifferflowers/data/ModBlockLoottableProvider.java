package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.blocks.DawnberryVineBlock;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLoottableProvider extends BlockLootSubProvider {
    public ModBlockLoottableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.DAWNBERRY_VINE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(this.applyExplosionDecay(ModItems.DAWNBERRY_VINE_SEEDS.get(), LootItem.lootTableItem(ModItems.DAWNBERRY_VINE_SEEDS.get()).apply(Direction.values(), (p_251536_) ->
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.DAWNBERRY_VINE.get()))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DawnberryVineBlock.AGE, 4)))));
        add(ModBlocks.AMBER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.AMBUSH_BANNER_PATTERN.get()))
                        .add(LootItem.lootTableItem(ModItems.AMBER_SHARD.get()))
                        .add(LootItem.lootTableItem(ModItems.DRAGONFLY.get()))
                        .add(LootItem.lootTableItem(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get()))
                        .add(LootItem.lootTableItem(ModItems.AMBUSH_SEEDS.get()))
                        .add(LootItem.lootTableItem(ModItems.DAWNBERRY_VINE_SEEDS.get()))
                        .add(LootItem.lootTableItem(ModBlocks.CAULORFLOWER.get().asItem()))
                        .add(LootItem.lootTableItem(ModItems.BONMEELIA_SEEDS.get()))
                        .add(LootItem.lootTableItem(Items.TORCHFLOWER_SEEDS))
                        .add(LootItem.lootTableItem(Items.PITCHER_POD))));
        dropSelf(ModBlocks.BOBLING_HEAD.get());
        dropSelf(ModBlocks.AMBUSH.get());
        dropSelf(ModBlocks.CAULORFLOWER.get());
        add(ModBlocks.GIANT_CARROT.get(), createSingleItemTable(Items.CARROT, UniformGenerator.between(1, 4)));
        add(ModBlocks.GIANT_POTATO.get(), createSingleItemTable(Items.POTATO, UniformGenerator.between(1, 4)));
        add(ModBlocks.GIANT_NETHERWART.get(), createSingleItemTable(Items.NETHER_WART, UniformGenerator.between(1, 4)));
        add(ModBlocks.GIANT_BEETROOT.get(), createSingleItemTable(Items.BEETROOT, UniformGenerator.between(1, 4)));
        add(ModBlocks.GIANT_WHEAT.get(), createSingleItemTable(Items.WHEAT, UniformGenerator.between(1, 4)));
        add(ModBlocks.BONMEELIA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.BONMEELIA_SEEDS.get())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.JAR_OF_BONMEEL.get()))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE)
                                        .hasProperty(BonmeeliaBlock.HAS_BOTTLE, true))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BonmeeliaBlock.AGE, BonmeeliaBlock.MAX_AGE))
                                .invert()
                        .and(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BONMEELIA.get())
                                .setProperties((StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BonmeeliaBlock.HAS_BOTTLE, true)))))));
    }

    private LootTable.Builder createGiantCropBuilder(Block block, ItemLike pItem) {
        return createGiantCropBuilder(block, pItem, Items.AIR);
    }

    private LootTable.Builder createGiantCropBuilder(Block block, ItemLike pItem, ItemLike specialDrop) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(pItem, LootItem.lootTableItem(pItem)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(GiantCropBlock.IS_CENTER, true))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(specialDrop, LootItem.lootTableItem(specialDrop))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
                .stream()
                .filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(MoreSnifferFlowers.MOD_ID))
                .collect(Collectors.toSet());
    }
}
