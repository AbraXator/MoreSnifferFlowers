package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ModEntityDoubleTallBlock extends Block implements IModEntityDoubleTallBlock {
    protected BlockPos ENTITY_POS;
    
    public ModEntityDoubleTallBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if(!level.isClientSide) {
            if(player.isCreative()) {
                preventCreativeDropFromBottomPart(level, pos, state, player);
            } else {
                var blockEntity = isUpper(state) ? level.getBlockEntity(pos) : null;
                dropResources(state, level, pos, blockEntity, player, player.getMainHandItem());
            }
        }

        super.playerWillDestroy(level, pos, state, player);
        return state;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), pBlockEntity, pTool);
    }

    @Override
    public void onRemove(@NotNull BlockState state, Level level, BlockPos pos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if(isUpper(state)) {
            Containers.dropContentsOnDestroy(state, pNewState, level, pos);
        }

        super.onRemove(state, level, pos, pNewState, pMovedByPiston);
    }
    
    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.getAxis() != Direction.Axis.Y || isLower(state) != (pFacing == Direction.UP) || isStateThis(pFacingState) && !areTwoHalfSame(state, pFacingState)) {
            return isLower(state) && pFacing == Direction.DOWN && !canSurvive(state, level, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, pFacing, pFacingState, level, pCurrentPos, pFacingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isLower(state)) {
            return super.canSurvive(state, level, pos);
        } else {

            BlockState blockstate = level.getBlockState(pos.below());
            if (!isStateThis(state)) return super.canSurvive(state, level, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return isStateThis(blockstate) && isLower(blockstate);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();

        return blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }
    
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity pPlacer, ItemStack stack) {
        level.setBlockAndUpdate(pos.above(), getUpperBlock().defaultBlockState());
    }
    
    public void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        if (isUpper(state)) {
            BlockPos blockPosBelow = pos.below();
            BlockState blockStateBelow = level.getBlockState(blockPosBelow);
            if (isStateThis(blockStateBelow) && isLower(blockStateBelow)) {
                BlockState blockStateForReplacement = blockStateBelow.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockPosBelow, blockStateForReplacement, 35);
                level.levelEvent(player, 2001, blockPosBelow, Block.getId(blockStateBelow));
            }
        }
    }
}
