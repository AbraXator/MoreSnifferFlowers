package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AmbushBlockEntity extends GrowingCropBlockEntity {
    public AmbushBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBUSH.get(), pPos, pBlockState, 0.001f);
    }

    @Override
    public boolean canGrow(float growProgress, boolean hasGrown) {
        return this.getCachedState().get(AmbushBlock.AGE).equals(7) && super.canGrow(growProgress, hasGrown);   
    }
}