package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GiantCropBlock extends Block implements EntityBlock {
    public static final BooleanProperty IS_CENTER = BooleanProperty.create("center");

    public GiantCropBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(IS_CENTER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(IS_CENTER);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if(!(pLevel.getBlockEntity(pCurrentPos) instanceof GiantCropBlockEntity entity)) {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        }
        Set<BlockPos> box = getBox(pCurrentPos, pLevel, entity);
        if(box.size() <= 26) {
            box.forEach(blockPos -> pLevel.destroyBlock(blockPos, false));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    public Set<BlockPos> getBox(BlockPos blockPos, LevelAccessor level, GiantCropBlockEntity entity) {
        HashSet<BlockPos> cropPositions = new HashSet<>();
        scan(level, blockPos, cropPositions, entity);
        return cropPositions;
    }

    private void scan(LevelAccessor level, BlockPos currentPos, Set<BlockPos> visited, GiantCropBlockEntity entity) {
        if(visited.contains(currentPos) 
                || !level.getBlockState(currentPos).is(this)
                || !(level.getBlockEntity(currentPos) instanceof GiantCropBlockEntity entity1)
                || !entity.uuid.equals(entity1.uuid)) {
            return;
        }

        visited.add(currentPos);
        int[] offsets = {-1, 0, 1};
        
        for(int offsetX : offsets) {
            for(int offsetY : offsets) {
                for(int offsetZ : offsets) {
                    BlockPos neighbouringPos = currentPos.offset(offsetX, offsetY, offsetZ);
                    scan(level, neighbouringPos, visited, entity);
                }
            }
        }
    }

    public static boolean isCenter(BlockState blockState) {
        return blockState.getValue(IS_CENTER);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GiantCropBlockEntity(pPos, pState);
    }
}
