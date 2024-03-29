package net.abraxator.moresnifferflowers.blocks.blockentities;

import mezz.jei.api.runtime.IEditModeConfig;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RebrewingStandBlock extends BlockEntity {
    public RebrewingStandBlock(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REBREWING_STAND.get(), pPos, pBlockState);
    }
}
