package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DyespriaPlantBlockEntity extends ColoredBlockEntity {
    public DyespriaPlantBlockEntity(BlockPos pos, BlockState state) {
        super(MSFBlockEntities.DYESPRIA_PLANT.get(), pos, state);
    }
}
