package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.nikdo53.tinymultiblocklib.blockentities.AbstractMultiBlockEntity;

public class BondripiaBlockEntity extends AbstractMultiBlockEntity {
    public BondripiaBlockEntity(BlockPos pos, BlockState state) {
        super(MSFBlockEntities.BONDRIPIA.get(), pos, state);
    }
}
