package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VivicusSignBlockEntity extends SignBlockEntity {
    public VivicusSignBlockEntity(BlockPos pos, BlockState state) {
        super(MSFBlockEntities.VIVICUS_SIGN.get(), pos, state);
    }
        @Override
     public BlockEntityType<?> getType() {
            return MSFBlockEntities.VIVICUS_SIGN.get();
    }
}
