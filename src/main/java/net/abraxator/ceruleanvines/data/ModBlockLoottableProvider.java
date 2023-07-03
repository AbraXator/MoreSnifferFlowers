package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.MoreSnifferFlowers;
import net.abraxator.ceruleanvines.blocks.DawnberryVineBlock;
import net.abraxator.ceruleanvines.init.ModBlocks;
import net.abraxator.ceruleanvines.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLoottableProvider extends BlockLootSubProvider {
    public ModBlockLoottableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.DAWNBERRY_VINE.get(), createMultifaceBlockDrops());
    }

    protected LootTable.Builder createMultifaceBlockDrops() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().add(this.applyExplosionDecay(ModItems.DAWNBERRY_VINE_SEEDS.get(), LootItem.lootTableItem(ModItems.DAWNBERRY_VINE_SEEDS.get()).apply(Direction.values(), (p_251536_) ->
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.DAWNBERRY_VINE.get()))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DAWNBERRY_VINE.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(DawnberryVineBlock.AGE, 4))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
                .stream()
                .filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(MoreSnifferFlowers.MOD_ID))
                .collect(Collectors.toSet());
    }
}
