package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTRasparentWallBlock extends WallBlock {
    public HalfTRasparentWallBlock(Properties p_57964_) {
        super(p_57964_);
    }

    @Override
    protected boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}
