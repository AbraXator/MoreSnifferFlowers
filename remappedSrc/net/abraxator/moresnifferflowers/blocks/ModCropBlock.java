package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.neoforged.neoforge.common.IPlantable;

public interface ModCropBlock extends IPlantable {
    default float getGrowthSpeed(Block pBlock, BlockView pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = pLevel.getBlockState(blockpos.add(i, 0, j));
                if (blockstate.canSustainPlant(pLevel, blockpos.add(i, 0, j), net.minecraft.util.math.Direction.UP, (IPlantable) pBlock)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(pLevel, pPos.add(i, 0, j))) {
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
        boolean flag = pLevel.getBlockState(blockpos3).isOf(pBlock) || pLevel.getBlockState(blockpos4).isOf(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).isOf(pBlock) || pLevel.getBlockState(blockpos2).isOf(pBlock);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).isOf(pBlock) || pLevel.getBlockState(blockpos4.north()).isOf(pBlock) || pLevel.getBlockState(blockpos4.south()).isOf(pBlock) || pLevel.getBlockState(blockpos3.south()).isOf(pBlock);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    record PosAndState(BlockPos blockPos, BlockState state) {}
}
