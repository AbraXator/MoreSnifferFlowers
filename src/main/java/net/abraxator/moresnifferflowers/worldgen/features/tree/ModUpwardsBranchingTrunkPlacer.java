package net.abraxator.moresnifferflowers.worldgen.features.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class ModUpwardsBranchingTrunkPlacer extends UpwardsBranchingTrunkPlacer {
    public ModUpwardsBranchingTrunkPlacer(int baseHeight, int pHeightRandA, int pHeightRandB, IntProvider extraBranchSteps, float placeBranchPerLogProbability, IntProvider extraBranchLength) {
        super(baseHeight, pHeightRandA, pHeightRandB, extraBranchSteps, placeBranchPerLogProbability, extraBranchLength, HolderSet.empty());
    }

    @Override
    protected boolean validTreePos(LevelSimulatedReader pLevel, BlockPos pPos) {
        return TreeFeature.validTreePos(pLevel, pPos);
    }
}
