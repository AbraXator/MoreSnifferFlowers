package net.abraxator.moresnifferflowers.blocks.ambush;

import net.abraxator.moresnifferflowers.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.ModEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AmbushBlockUpper extends AmbushBlockBase implements ModEntityBlock {
    public static final int AGE_TO_GROW_UP = 4;

    public AmbushBlockUpper(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        ENTITY_POS = pPos;
        return new AmbushBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }
}
