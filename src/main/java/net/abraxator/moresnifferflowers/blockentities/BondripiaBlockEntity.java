package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BondripiaBlockEntity extends ModBlockEntity {
    public BondripiaBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities, pPos, pBlockState);
    }
}
