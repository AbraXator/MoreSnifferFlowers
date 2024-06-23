package net.abraxator.moresnifferflowers.worldgen.features;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.worldgen.features.tree.ModUpwardsBranchingTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_TREE = FeatureUtils.createKey("corrupted_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VIVICUS_TREE = FeatureUtils.createKey("vivicus_tree");
    
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holderGetter = context.lookup(Registries.BLOCK);
        
        FeatureUtils.register(
                context, CORRUPTED_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.CORRUPTED_LOG.get()),
                        new ModUpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.of(1, 4), 0.5F, UniformInt.of(0, 1)),
                        BlockStateProvider.simple(ModBlocks.CORRUPTED_LEAVES.get()),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
        FeatureUtils.register(
                context, VIVICUS_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.VIVICUS_LOG.get()),
                        new ModUpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.of(1, 4), 0.5F, UniformInt.of(0, 1)),
                        BlockStateProvider.simple(ModBlocks.VIVICUS_LEAVES.get()),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
    }
}
