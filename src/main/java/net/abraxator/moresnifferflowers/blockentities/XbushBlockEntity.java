package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class XbushBlockEntity extends GrowingCropBlockEntity {
    public XbushBlockEntity(BlockPos pos, BlockState state) {
        super(MSFBlockEntities.XBUSH.get(), pos, state, state.is(MSFBlocks.AMBUSH_TOP) ? 0.001f : 0.0005F);
    }

    @Override
    public boolean canGrow(float growProgress, boolean hasGrown) {
        return this.getBlockState().getValue(MSFStateProperties.AGE_8).equals(7)
                && !getBlockState().getValue(MSFStateProperties.SHEARED)
                && super.canGrow(growProgress, hasGrown);
    }
}