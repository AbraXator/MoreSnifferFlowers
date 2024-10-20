package net.abraxator.moresnifferflowers.worldgen.configurations.tree.corrupted;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.worldgen.configurations.ModTrunkPlacerTypes;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.*;
import java.util.function.BiConsumer;

public class CorruptedTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<CorruptedTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            p_70161_ -> trunkPlacerParts(p_70161_).apply(p_70161_, CorruptedTrunkPlacer::new)
    );
    
    public CorruptedTrunkPlacer(int p_70148_, int p_70149_, int p_70150_) {
        super(p_70148_, p_70149_, p_70150_);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.CORRUPTED_TRUNK_PLACER.get();
    }


    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        List<FoliagePlacer.FoliageAttachment> ret = new ArrayList<>();
        List<Branch> branches = new ArrayList<>();
        int lastLogHeight = pFreeTreeHeight - ((int) Mth.randomBetween(pRandom, 2, 3));
        BlockPos.MutableBlockPos mainTrunk = pPos.mutable();
        Direction growthDir = null;
        int outerHeight = pRandom.nextInt(3);;
        int cornerHeight = outerHeight + (pRandom.nextInt(2) - 1);
        int innerHeight = Math.min(outerHeight + pRandom.nextIntBetweenInclusive(3, 5), pFreeTreeHeight - 2);
        
        for(int i = 0; i < pFreeTreeHeight; i++) {
            
            this.placeLog(pLevel, pBlockSetter, pRandom, mainTrunk, pConfig);
            for (int b = 0; b < pRandom.nextInt(2); b++) {
                addBranch(mainTrunk, Direction.Plane.HORIZONTAL.getRandomDirection(pRandom), branches, pRandom, i);
            }
            placeBranch(branches, ret, pBlockSetter, pRandom, i, pFreeTreeHeight);
            
            if (growthDir != null) {
                pPos = pPos.relative(growthDir).relative(growthDir.getClockWise());
                mainTrunk.move(growthDir).move(growthDir.getClockWise());
            }
            
            /*for(BlockPos branchPos : branches.keySet()) {
                pBlockSetter.accept(branchPos, Blocks.ORANGE_WOOL.defaultBlockState());
                //this.placeLog(pLevel, pBlockSetter, pRandom, branchPos, pConfig);

                if(i == pFreeTreeHeight - 1) {
                    ret.add(new FoliagePlacer.FoliageAttachment(branchPos, 0, false));
                }
            };*/
            
            if (i == 0) {
                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    if(pRandom.nextDouble() <= 0.90D) {
                        BlockPos blockPosInner = pPos.relative(direction);
                        for(int j = 0; j < innerHeight; j++) {
                            this.placeLog(pLevel, pBlockSetter, pRandom, blockPosInner.above(j), pConfig);
                            //addBranch(blockPosInner, direction, branchesPos, branchesDir, pRandom, j);
                        }

                        if(pRandom.nextDouble() <= 0.8D) {
                            ret.add(new FoliagePlacer.FoliageAttachment(blockPosInner.above(innerHeight), 0, false));
                        }

                        if(pRandom.nextDouble() <= 0.90D) {
                            BlockPos blockPosCorner = blockPosInner.relative(pRandom.nextDouble() > 0.5D ? direction.getClockWise() : direction.getCounterClockWise());
                            for(int j = 0; j < cornerHeight; j++) {
                                this.placeLog(pLevel, pBlockSetter, pRandom, blockPosCorner.above(j), pConfig);
                                //addBranch(blockPosInner, direction, branchesPos, branchesDir, pRandom, j);
                            }
                        }

                        if(pRandom.nextDouble() <= 0.90D) {
                            BlockPos blockPosOuter = blockPosInner.relative(direction);
                            for(int j = 0; j < outerHeight; j++) {
                                this.placeLog(pLevel, pBlockSetter, pRandom, blockPosOuter.above(j), pConfig);
                            }
                        }
                    }
                }
            } else if(pRandom.nextDouble() <= 0.7D) {
                //rowthDir = flagStumpHeight ? null : Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
            }

            mainTrunk.move(Direction.UP);
        }

        ret.add(new FoliagePlacer.FoliageAttachment(mainTrunk.below(1), 0, false));
        return ret;
    }

    private void addBranch(BlockPos blockPos, Direction direction, List<Branch> branches, RandomSource random, int height) {
        if (height >= 3) {
            direction = direction.getOpposite();
            Direction finalDir = Branch.computeBranchDir(direction, random, 0.5D);
            branches.add(new Branch(blockPos.relative(finalDir), finalDir, 0,false));
        }
    }

    private void placeBranch(List<Branch> branches, List<FoliagePlacer.FoliageAttachment> ret, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource random, int height, int pFreeTreeHeight) {
        for (int i = 0; i < branches.size(); i++) {
            Branch branch = branches.get(i);
            
            if(branch.last) {
                ret.add(new FoliagePlacer.FoliageAttachment(branch.pos, 0, false));
            } else {
                pBlockSetter.accept(branch.pos, Blocks.ORANGE_WOOL.defaultBlockState());
                if(random.nextBoolean()) {
                    pBlockSetter.accept(branch.pos.relative(branch.direction), Blocks.ORANGE_WOOL.defaultBlockState());
                }

                Direction direction = Branch.computeBranchDir(branch.direction, random, 0.3D);
                BlockPos blockPos = branch.pos.relative(direction).above();
                boolean last = branch.lenght > 3 && height >= pFreeTreeHeight - random.nextInt(1);
                
                branches.set(i, new Branch(blockPos, direction, 1, last));
            }
        }
    }
    
    

    @Override
    protected boolean validTreePos(LevelSimulatedReader pLevel, BlockPos pPos) {
        return true;
    }
    
    private record Branch(BlockPos pos, Direction direction, int lenght, boolean last) {
        private static Direction computeBranchDir(Direction direction, RandomSource random, double chance) {
            Direction clockAdjusted = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
            return random.nextDouble() <= chance ? direction : clockAdjusted;
        }
    }
}