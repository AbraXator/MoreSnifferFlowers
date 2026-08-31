package net.abraxator.moresnifferflowers.blocks.corrupted;

import net.abraxator.moresnifferflowers.blockentities.CorruptedSludgeBlockEntity;
import net.abraxator.moresnifferflowers.blocks.TickableEntityBlock;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class CorruptedSludgeBlock extends Block implements TickableEntityBlock {
    public CorruptedSludgeBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(defaultBlockState().setValue(MSFStateProperties.USES_4, 3).setValue(MSFStateProperties.CURED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MSFStateProperties.USES_4, MSFStateProperties.CURED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CorruptedSludgeBlockEntity(pos, state);
    }
}
