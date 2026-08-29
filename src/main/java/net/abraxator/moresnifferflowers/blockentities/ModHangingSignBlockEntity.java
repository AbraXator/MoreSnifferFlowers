package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignBlockEntity extends HangingSignBlockEntity {
    public ModHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<ModHangingSignBlockEntity> getType() {
        return MSFBlockEntities.MOD_HANGING_SIGN.get();
    }
}