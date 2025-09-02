package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class XbushBlockEntity extends GrowingCropBlockEntity {
    public XbushBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.XBUSH.get(), pos, state, state.is(ModBlocks.AMBUSH_TOP) ? 0.001f : 0.0005F);
    }

    @Override
    public boolean canGrow(float growProgress, boolean hasGrown) {
        return this.getBlockState().getValue(ModStateProperties.AGE_8).equals(7)
                && !getBlockState().getValue(ModStateProperties.SHEARED)
                && super.canGrow(growProgress, hasGrown);
    }
}