package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.RebrewingStandBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RebrewingStandBlockBase extends Block {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};
    public static final VoxelShape BASE = Block.box(0, 0, 0, 16, 1, 16);
    public static VoxelShape ROD_UPPER = Block.box(6.5, 0, 6.5, 9.5, 14, 9.5);
    public static VoxelShape ROD_LOWER = Block.box(6.5, 0, 6.5, 9.5, 16, 9.5);
    
    public RebrewingStandBlockBase(Properties pProperties) {
        super(pProperties);
        if(!(this instanceof RebrewingStandBlockTop)) {
            defaultBlockState().setValue(HAS_BOTTLE[0], false).setValue(HAS_BOTTLE[1], false).setValue(HAS_BOTTLE[2], false);
        }
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HAS_BOTTLE);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if(pState.is(ModBlocks.REBREWING_STAND_TOP.get())) {
            BlockState blockstate1 = pState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            pLevel.setBlock(pPos.below(), blockstate1, 35);
            pLevel.levelEvent(pPlayer, 2001, pPos.below(), Block.getId(blockstate1));
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(BASE, ROD_LOWER);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return canSurvive(pState, pLevel, pCurrentPos) ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if(!pState.is(ModBlocks.REBREWING_STAND_TOP.get())) {
            return super.canSurvive(pState, pLevel, pPos);
        } else {
            BlockState blockState = pLevel.getBlockState(pPos.below());
            return blockState.is(ModBlocks.REBREWING_STAND_BOTTOM.get());
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockPos blockPos = pPos;
            if(pLevel.getBlockState(pPos).is(ModBlocks.REBREWING_STAND_BOTTOM.get())) {
                blockPos = blockPos.above();
            }
            
            BlockEntity entity = pLevel.getBlockEntity(blockPos);
            if (entity instanceof RebrewingStandBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), ((RebrewingStandBlockEntity) entity), blockPos);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockPos = pContext.getClickedPos();
        BlockPos blockPos1 = blockPos.above();
        Level level = pContext.getLevel();

        return level.getBlockState(blockPos1).canBeReplaced(pContext) ? this.defaultBlockState() : null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide) {
            BlockPos blockPos = pPos.above();
            pLevel.setBlockAndUpdate(blockPos, ModBlocks.REBREWING_STAND_TOP.get().defaultBlockState());
            pState.updateNeighbourShapes(pLevel, blockPos, 3);
        }
    }
}
