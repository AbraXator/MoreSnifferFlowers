package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.ModBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface ModEntityBlock extends BlockEntityProvider {
    @Nullable
     default <T extends BlockEntity> BlockEntityTicker<T> tickerHelper(World pLevel) {
        if(pLevel.isClient) return null;
        return (pLevel1, pPos, pState1, pBlockEntity) -> ((ModBlockEntity) pBlockEntity).tick();
    }
}
