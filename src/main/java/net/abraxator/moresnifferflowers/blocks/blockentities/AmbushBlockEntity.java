package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AmbushBlockEntity extends GrowingCropBlockEntity {
    public AmbushBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBUSH.get(), pPos, pBlockState, 0.001f);
    }

    @Override
    public boolean canGrow(float growProgress, boolean hasGrown) {
        return this.getBlockState().getValue(AmbushBlock.AGE).equals(7) && super.canGrow(growProgress, hasGrown);   
    }
}