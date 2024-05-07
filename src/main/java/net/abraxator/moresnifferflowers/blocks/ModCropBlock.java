package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.IPlantable;

public interface ModCropBlock extends IPlantable, BonemealableBlock {
    IntegerProperty getAgeProperty();

    default boolean isMaxAge (BlockState blockState) {
        return getAge(blockState) >= getMaxAge();
    }

    default int getMaxAge() {
        return getAgeProperty().getPossibleValues().stream().toList().get(getAgeProperty().getPossibleValues().size() - 1);
    }

    default int getAge(BlockState blockState) {
        return blockState.getValue(getAgeProperty());
    }

    default void makeGrowOnTick(Block block, BlockState blockState, Level level, BlockPos blockPos) {
        if (!isMaxAge(blockState) && level.isAreaLoaded(blockPos, 1) && level.getRawBrightness(blockPos, 0) >= 9) {
            float f = getGrowthSpeed(block, level, blockPos);
            if (CommonHooks.onCropsGrowPre(level, blockPos, blockState, level.getRandom().nextInt((int)(25.0F / f) + 1) == 0)) {
                level.setBlock(blockPos, blockState.setValue(getAgeProperty(), (blockState.getValue(getAgeProperty()) + 1)), 2);
                CommonHooks.onCropsGrowPost(level, blockPos, blockState);
            }
        }
    }

    default void makeGrowOnBonemeal(Level level, BlockPos blockPos, BlockState blockState) {
        level.setBlock(blockPos, blockState.setValue(getAgeProperty(), getAge(blockState) >= 3 ? getAge(blockState) : getAge(blockState) + 1), 2);
    }

    default boolean mayPlaceOn(BlockState pState) {
        return pState.is(Blocks.FARMLAND) || pState.getBlock() instanceof FarmBlock;
    }

    default float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = pLevel.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(pLevel, blockpos.offset(i, 0, j), net.minecraft.core.Direction.UP, (IPlantable) pBlock)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(pLevel, pPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = pLevel.getBlockState(blockpos3).is(pBlock) || pLevel.getBlockState(blockpos4).is(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).is(pBlock) || pLevel.getBlockState(blockpos2).is(pBlock);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).is(pBlock) || pLevel.getBlockState(blockpos4.north()).is(pBlock) || pLevel.getBlockState(blockpos4.south()).is(pBlock) || pLevel.getBlockState(blockpos3.south()).is(pBlock);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    record PosAndState(BlockPos blockPos, BlockState state) {}
}
