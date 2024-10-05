package net.abraxator.moresnifferflowers.worldgen.configurations.tree.vivicus;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.worldgen.configurations.ModTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class VivicusTrunkPlacer extends TrunkPlacer {
    private static final Codec<UniformInt> BRANCH_START_CODEC = UniformInt.CODEC
            .codec()
            .validate(
                    p_275181_ -> p_275181_.getMaxValue() - p_275181_.getMinValue() < 1
                            ? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches")
                            : DataResult.success(p_275181_)
            );
    public static final MapCodec<VivicusTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            p_338099_ -> trunkPlacerParts(p_338099_)
                    .and(
                            p_338099_.group(
                                    IntProvider.codec(1, 3).fieldOf("branch_count").forGetter(p_272644_ -> p_272644_.branchCount),
                                    IntProvider.codec(2, 16).fieldOf("branch_horizontal_length").forGetter(p_273612_ -> p_273612_.branchHorizontalLength),
                                    IntProvider.validateCodec(-16, 0, BRANCH_START_CODEC)
                                            .fieldOf("branch_start_offset_from_top")
                                            .forGetter(p_272705_ -> p_272705_.branchStartOffsetFromTop),
                                    IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top").forGetter(p_273633_ -> p_273633_.branchEndOffsetFromTop)
                            )
                    )
                    .apply(p_338099_, VivicusTrunkPlacer::new)
    );
    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;
    private final UniformInt branchStartOffsetFromTop;
    private final UniformInt secondBranchStartOffsetFromTop;
    private final IntProvider branchEndOffsetFromTop;

    public VivicusTrunkPlacer(
            int p_273281_, int p_273327_, int p_272619_, IntProvider p_272873_, IntProvider p_272789_, UniformInt p_272917_, IntProvider p_272948_
    ) {
        super(p_273281_, p_273327_, p_272619_);
        this.branchCount = p_272873_;
        this.branchHorizontalLength = p_272789_;
        this.branchStartOffsetFromTop = p_272917_;
        this.secondBranchStartOffsetFromTop = UniformInt.of(p_272917_.getMinValue(), p_272917_.getMaxValue() - 1);
        this.branchEndOffsetFromTop = p_272948_;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.VIVICUS_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader pLevel,
            BiConsumer<BlockPos, BlockState> pBlockSetter,
            RandomSource pRandom,
            int pFreeTreeHeight,
            BlockPos pPos,
            TreeConfiguration pConfig
    ) {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int i = Math.max(0, pFreeTreeHeight - 1 + this.branchStartOffsetFromTop.sample(pRandom));
        int j = Math.max(0, pFreeTreeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(pRandom));
        if (j >= i) {
            j++;
        }

        int k = this.branchCount.sample(pRandom);
        boolean flag = k == 3;
        boolean flag1 = k >= 2;
        int l;
        if (flag) {
            l = pFreeTreeHeight;
        } else if (flag1) {
            l = Math.max(i, j) + 1;
        } else {
            l = i + 1;
        }

        for (int i1 = 0; i1 < l; i1++) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i1), pConfig);
        }

        List<FoliagePlacer.FoliageAttachment> list = new ArrayList<>();
        if (flag) {
            list.add(new FoliagePlacer.FoliageAttachment(pPos.above(l), 0, false));
        }

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
        Function<BlockState, BlockState> function = p_273382_ -> p_273382_.trySetValue(RotatedPillarBlock.AXIS, direction.getAxis());
        list.add(
                this.generateBranch(pLevel, pBlockSetter, pRandom, pFreeTreeHeight, pPos, pConfig, function, direction, i, i < l - 1, blockpos$mutableblockpos)
        );
        if (flag1) {
            list.add(
                    this.generateBranch(
                            pLevel, pBlockSetter, pRandom, pFreeTreeHeight, pPos, pConfig, function, direction.getOpposite(), j, j < l - 1, blockpos$mutableblockpos
                    )
            );
        }

        return list;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(
            LevelSimulatedReader pLevel,
            BiConsumer<BlockPos, BlockState> pBlockSetter,
            RandomSource pRandom,
            int pFreeTreeHeight,
            BlockPos pPos,
            TreeConfiguration pConfig,
            Function<BlockState, BlockState> pPropertySetter,
            Direction pDirection,
            int pSecondBranchStartOffsetFromTop,
            boolean pDoubleBranch,
            BlockPos.MutableBlockPos pCurrentPos
    ) {
        pCurrentPos.set(pPos).move(Direction.UP, pSecondBranchStartOffsetFromTop);
        int i = pFreeTreeHeight - 1 + this.branchEndOffsetFromTop.sample(pRandom);
        boolean flag = pDoubleBranch || i < pSecondBranchStartOffsetFromTop;
        int j = this.branchHorizontalLength.sample(pRandom) + (flag ? 1 : 0);
        BlockPos blockpos = pPos.relative(pDirection, j).above(i);
        int k = flag ? 2 : 1;

        for (int l = 0; l < k; l++) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pCurrentPos.move(pDirection), pConfig, pPropertySetter);
        }

        Direction direction = blockpos.getY() > pCurrentPos.getY() ? Direction.UP : Direction.DOWN;

        while (true) {
            int i1 = pCurrentPos.distManhattan(blockpos);
            if (i1 == 0) {
                return new FoliagePlacer.FoliageAttachment(blockpos.above(), 0, false);
            }

            float f = (float)Math.abs(blockpos.getY() - pCurrentPos.getY()) / (float)i1;
            boolean flag1 = pRandom.nextFloat() < f;
            pCurrentPos.move(flag1 ? direction : pDirection);
            this.placeLog(pLevel, pBlockSetter, pRandom, pCurrentPos, pConfig, flag1 ? Function.identity() : pPropertySetter);
        }
    }
    
    @Override
    protected boolean validTreePos(LevelSimulatedReader pLevel, BlockPos pPos) {
        return super.validTreePos(pLevel, pPos) || pLevel.isStateAtPosition(pPos, blockState -> blockState.is(ModTags.ModBlockTags.VIVICUS_TREE_REPLACABLE));
    }
}
