package net.abraxator.moresnifferflowers.data.loot;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.blocks.DawnberryVineBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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
                .withPool(LootPool.lootPool()
                        .when(HAS_SILK_TOUCH)
                        .add(LootItem.lootTableItem(ModBlocks.AMBER.get())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .when(HAS_NO_SILK_TOUCH)
                        //COMMON
                                .add(LootItem.lootTableItem(Items.COAL).setWeight(100))
                                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(100))
                                .add(LootItem.lootTableItem(Items.STICK).setWeight(100))
                                .add(LootItem.lootTableItem(ModItems.AMBER_SHARD.get()).setWeight(100))
                        //UNCOMMON
                                .add(LootItem.lootTableItem(Items.CARROT).setWeight(50))
                                .add(LootItem.lootTableItem(Items.POTATO).setWeight(50))
                                .add(LootItem.lootTableItem(Items.BEETROOT).setWeight(50))
                                .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(50))
                                .add(LootItem.lootTableItem(Items.NETHER_WART).setWeight(50))
                                .add(LootItem.lootTableItem(Items.WHEAT).setWeight(50))
                                .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(50))
                                .add(LootItem.lootTableItem(ModItems.DRAGONFLY.get()).setWeight(50))
                        //RARE
                                .add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.BELT_PIECE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.ENGINE_PIECE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.SCRAP_PIECE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.TUBE_PIECE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.PRESS_PIECE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.AMBUSH_BANNER_PATTERN.get()).setWeight(25))
                                .add(LootItem.lootTableItem(ModItems.EXTRACTION_BOTTLE.get()).setWeight(25))
                        //VERY RARE
                                .add(LootItem.lootTableItem(Items.TORCHFLOWER_SEEDS).setWeight(12))
                                .add(LootItem.lootTableItem(Items.PITCHER_POD).setWeight(12))
                                .add(LootItem.lootTableItem(Items.SNIFFER_EGG).setWeight(12))
                                .add(LootItem.lootTableItem(ModItems.DAWNBERRY_VINE_SEEDS.get()).setWeight(12))
                                .add(LootItem.lootTableItem(ModItems.AMBUSH_SEEDS.get()).setWeight(12))
                                .add(LootItem.lootTableItem(ModItems.DYESPRIA_SEEDS.get()).setWeight(12))
                                .add(LootItem.lootTableItem(ModItems.BONMEELIA_SEEDS.get()).setWeight(12))));
                                
        dropSelf(ModBlocks.BOBLING_HEAD.get());
        add(ModBlocks.AMBUSH_TOP.get(), noDrop());
        dropSelf(ModBlocks.AMBUSH_BOTTOM.get());
        dropSelf(ModBlocks.CAULORFLOWER.get());
        add(ModBlocks.GIANT_CARROT.get(), giantCropLoot(Items.CARROT, ModItems.CROPRESSED_CARROT.get(), Items.AIR, ModItems.BELT_PIECE.get(), ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
        add(ModBlocks.GIANT_POTATO.get(), giantCropLoot(Items.POTATO, ModItems.CROPRESSED_POTATO.get(), Items.AIR, ModItems.TUBE_PIECE.get(), ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
        add(ModBlocks.GIANT_NETHERWART.get(), giantCropLoot(Items.NETHER_WART, ModItems.CROPRESSED_NETHERWART.get(), ModItems.BROKEN_REBREWING_STAND.get(), ModItems.ENGINE_PIECE.get(), ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
        add(ModBlocks.GIANT_BEETROOT.get(), giantCropLoot(Items.BEETROOT, ModItems.CROPRESSED_BEETROOT.get(), Items.AIR, ModItems.PRESS_PIECE.get(), ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
        add(ModBlocks.GIANT_WHEAT.get(), giantCropLoot(Items.WHEAT, ModItems.CROPRESSED_WHEAT.get(), Items.AIR, ModItems.SCRAP_PIECE.get(), ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
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
        dropSelf(ModBlocks.CROPRESSOR_OUT.get());
        dropSelf(ModBlocks.CROPRESSOR_CENTER.get());
        dropSelf(ModBlocks.MORE_SNIFFER_FLOWER.get());
        dropSelf(ModBlocks.REBREWING_STAND_BOTTOM.get());
        add(ModBlocks.REBREWING_STAND_TOP.get(), noDrop());
        dropSelf(ModBlocks.DYESPRIA_PLANT.get());
    }
    
    private LootTable.Builder giantCropLoot(Item crop, Item cropressed, Item special, Item piece, Item trim) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 4))
                        .add(LootItem.lootTableItem(crop)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                        .add(LootItem.lootTableItem(piece).setWeight(100))
                        .add(LootItem.lootTableItem(trim).setWeight(10))
                        .add(LootItem.lootTableItem(cropressed).setWeight(10))
                        .add(LootItem.lootTableItem(special).setWeight(100)));
    }
    
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
                .stream()
                .filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(MoreSnifferFlowers.MOD_ID))
                .collect(Collectors.toSet());
    }
}
