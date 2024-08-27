package net.abraxator.moresnifferflowers.worldgen.configurations;

import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.corrupted.CorruptedTrunkPlacer;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.vivicus.VivicusTrunkPlacer;
import net.abraxator.moresnifferflowers.worldgen.feature.ModFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_TREE = FeatureUtils.createKey("corrupted_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CURED_VIVICUS_TREE = FeatureUtils.createKey("cured_vivicus_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_VIVICUS_TREE = FeatureUtils.createKey("corrupted_vivicus_tree");
    
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
        
        FeatureUtils.register(
                context, CORRUPTED_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.CORRUPTED_LOG.get().defaultBlockState(), 10)
                                        .add(ModBlocks.STRIPPED_CORRUPTED_LOG.get().defaultBlockState(), 4)
                        ),
                        new CorruptedTrunkPlacer(3, 1, 2),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.CORRUPTED_LEAVES.get().defaultBlockState(), 10)
                                        .add(ModBlocks.CORRUPTED_LEAVES_BUSH.get().defaultBlockState(), 2)
                                        .add(ModBlocks.CORRUPTED_SLUDGE.get().defaultBlockState(), 1)
                        ),
                        new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), UniformInt.of(2, 3), 35),
                        new TwoLayersFeatureSize(2, 0, 2)
                )
                        .ignoreVines()
                        .build());
        FeatureUtils.register(
                context, CURED_VIVICUS_TREE, ModFeatures.VIVICUS_TREE.get(), vivicusTree(blockHolderGetter, BoblingEntity.Type.CURED)
                        .ignoreVines()
                        .build());
        FeatureUtils.register(
                context, CORRUPTED_VIVICUS_TREE, ModFeatures.VIVICUS_TREE.get(), vivicusTree(blockHolderGetter, BoblingEntity.Type.CORRUPTED)
                        .ignoreVines()
                        .build());
    }
    
    private static TreeConfiguration.TreeConfigurationBuilder vivicusTree(HolderGetter<Block> blockHolderGetter, BoblingEntity.Type type) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.VIVICUS_LOG.get()),
                new CherryTrunkPlacer(
                        7,
                        1,
                        0,
                        new WeightedListInt(
                                SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                        ),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0)
                ),
                //new VivicusTrunkPlacer(6, 3, 3, UniformInt.of(3, 5), 1F, UniformInt.of(0, 1), blockHolderGetter.getOrThrow(ModTags.ModBlockTags.VIVICUS_TREE_REPLACABLE)),
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(ModBlocks.VIVICUS_LEAVES.get().defaultBlockState(), 10)
                                .add(ModBlocks.SPROUTING_VIVICUS_LEAVES.get().defaultBlockState().setValue(ModStateProperties.VIVICUS_TYPE, type), 3)
                        //.add(Blocks.ORANGE_WOOL.defaultBlockState(), 5)
                ),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                //new CherryFoliagePlacer(UniformInt.of(3, 6), ConstantInt.ZERO, UniformInt.of(4, 5), 0.5F, 0.4F, 0.7F, 0.4F),
                //new RandomSpreadFoliagePlacer(UniformInt.of(3, 6), ConstantInt.ZERO, UniformInt.of(2, 4), 40),
                new TwoLayersFeatureSize(2, 0, 2)
        );
    }
}
