package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class BondripiaBlock extends SporeBlossomBlock implements ModEntityBlock {
    public BondripiaBlock(Properties p_49795_) {
        super(p_49795_);
        this.defaultBlockState().setValue(ModStateProperties.CENTER, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ModStateProperties.CENTER);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockPos.withinManhattanStream(pPos, 2, 1, 2).forEach(blockPos -> {
            pLevel.setBlock(blockPos, this.defaultBlockState().setValue(ModStateProperties.CENTER, pPos.equals(blockPos)), 3);
        });
    }

    @Override
    public void animateTick(BlockState p_222503_, Level p_222504_, BlockPos p_222505_, RandomSource p_222506_) {}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
