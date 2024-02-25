package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.block.*;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public interface ModCropBlock {
    default Pair<Boolean, Integer> canCropGrow(Block block, World world, BlockPos pos, int age, int maxAge) {
        int i = 0;
        return new Pair<>((world.random.nextInt((int) (25.0f / getGrowthSpeed(block, world, pos) + 1)) == 0) && ((i = age) < maxAge) && (world.getBaseLightLevel(pos, 0) >= 9), i);
    }

    default float getGrowthSpeed(Block block, BlockView world, BlockPos pos) {
        boolean bl2;
        float f = 1.0f;
        BlockPos blockPos = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float g = 0.0f;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0f;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0f;
                    }
                }
                if (i != 0 || j != 0) {
                    g /= 4.0f;
                }
                f += g;
            }
        }
        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl3 = bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0f;
        } else {
            boolean bl32;
            boolean bl4 = bl32 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl32) {
                f /= 2.0f;
            }
        }
        return f;
    }

    default float getBonemealGrowth(World world, int maxAge) {
        return MathHelper.nextInt(world.random, Math.round(maxAge / 2), maxAge - 2);
    }

    record PosAndState(BlockPos blockPos, BlockState state) {}
}
