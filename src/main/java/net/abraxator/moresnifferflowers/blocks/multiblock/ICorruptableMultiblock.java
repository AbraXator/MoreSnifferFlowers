package net.abraxator.moresnifferflowers.blocks.multiblock;

import net.abraxator.moresnifferflowers.entities.CorruptedProjectile;
import net.abraxator.moresnifferflowers.recipes.CorruptionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nikdo53.tinymultiblocklib.blockentities.IMultiBlockEntity;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;

public interface ICorruptableMultiblock extends IMultiBlock {
    Block getCuredBlock();
    Block getCorruptedBlock();

    @Override
    default boolean allBlocksPresent(LevelReader level, BlockPos pos, BlockState state){
        if (level.isClientSide()) return true;
        BlockPos center = IMultiBlock.getCenter(level, pos);

        boolean ret = getFullBlockShape(level, center, state).getGlobalPositions().stream().allMatch(blockPos -> level.getBlockState(blockPos).is(getCuredBlock()) || level.getBlockState(blockPos).is(getCorruptedBlock()));

        if (ret && level.getBlockEntity(pos) instanceof IMultiBlockEntity entity && !entity.isPlaced()) {
            getFullBlockShape(level, center, state).getGlobalPositions().forEach(blockPos -> IMultiBlockEntity.setPlaced(level, blockPos, true));
        }

        return ret;
    }

    default void corruptionHelper(BlockState state, Level level, BlockPos pos, Entity entityInside){
        if(entityInside instanceof CorruptedProjectile corruptedProjectile && CorruptionRecipe.canBeCorrupted(state.getBlock(), level)) {
            if(level.getBlockEntity(pos) instanceof IMultiBlockEntity entity) {
                corruptedProjectile.discard();
                BlockPos centrePos = entity.getCenter();
                BlockState centreState = level.getBlockState(centrePos);
                getFullBlockShape(level, entity.getCenter(), state).getGlobalPositions().forEach(pos1 -> afterCorruption(centrePos, level, pos1));
            }
        }
    }

    default void afterCorruption(BlockPos centrePos, Level level, BlockPos pos){
        if (!CorruptionRecipe.canBeCorrupted(level.getBlockState(pos).getBlock(), level)) return;

        Block corruptedBlock = CorruptionRecipe.getCorruptedBlock(level.getBlockState(pos).getBlock(), level).orElse(Blocks.AIR);
        level.setBlockAndUpdate(pos, corruptedBlock.withPropertiesOf(level.getBlockState(pos)));

        if(level.getBlockEntity(pos) instanceof IMultiBlockEntity entity){
            entity.setCenter(centrePos);
        }
    }
}
