package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptedSlimeLayerBlock extends SnowLayerBlock {
    public CorruptedSlimeLayerBlock(Properties p_56585_) {
        super(p_56585_);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return false;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    }

}
