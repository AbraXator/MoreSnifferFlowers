package net.abraxator.moresnifferflowers.worldgen.configurations;

import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.CorruptedTrunkPlacer;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.ModUpwardsBranchingTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_TREE = FeatureUtils.createKey("corrupted_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CURED_VIVICUS_TREE = FeatureUtils.createKey("cured_vivicus_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_VIVICUS_TREE = FeatureUtils.createKey("corrupted_vivicus_tree");
    
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holderGetter = context.lookup(Registries.BLOCK);
        
        FeatureUtils.register(
                context, CORRUPTED_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.CORRUPTED_LOG.get()),
                        new CorruptedTrunkPlacer(3, 1, 2),
                        BlockStateProvider.simple(ModBlocks.CORRUPTED_LEAVES.get()),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), UniformInt.of(0, 1), UniformInt.of(2, 3), 25),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
        FeatureUtils.register(
                context, CURED_VIVICUS_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.VIVICUS_LOG.get()),
                        new ModUpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.of(1, 4), 0.5F, UniformInt.of(0, 1)),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.VIVICUS_LEAVES.get().defaultBlockState(), 10)
                                        .add(ModBlocks.SPROUTING_VIVICUS_LEAVES.get().defaultBlockState().setValue(ModStateProperties.VIVICUS_TYPE, BoblingEntity.Type.CURED), 3)
                                        //.add(Blocks.GREEN_WOOL.defaultBlockState(), 5)
                        ),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
        FeatureUtils.register(
                context, CORRUPTED_VIVICUS_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.VIVICUS_LOG.get()),
                        new ModUpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.of(1, 4), 0.5F, UniformInt.of(0, 1)),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.VIVICUS_LEAVES.get().defaultBlockState(), 10)
                                        .add(ModBlocks.SPROUTING_VIVICUS_LEAVES.get().defaultBlockState().setValue(ModStateProperties.VIVICUS_TYPE, BoblingEntity.Type.CORRUPTED), 3)
                                        //.add(Blocks.ORANGE_WOOL.defaultBlockState(), 5)
                        ),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
    }
}
