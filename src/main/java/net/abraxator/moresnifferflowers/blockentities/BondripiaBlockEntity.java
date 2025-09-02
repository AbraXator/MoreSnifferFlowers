package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BondripiaBlockEntity extends MultiBlockEntity{
    public BondripiaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BONDRIPIA.get(), pos, state);
    }
}
