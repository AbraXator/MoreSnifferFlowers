package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.ModBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface ModEntityBlock extends EntityBlock {
    @Nullable
     default <T extends BlockEntity> BlockEntityTicker<T> tickerHelper(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide) return null;
        return (pLevel1, pPos, pState1, pBlockEntity) -> ((ModBlockEntity) pBlockEntity).tick();
    }
}
