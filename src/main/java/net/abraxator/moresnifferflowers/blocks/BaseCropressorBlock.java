package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class BaseCropressorBlock extends HorizontalDirectionalBlock {
    public final Part PART;
    protected BlockPos ENTITY_POS;
    protected static final VoxelShape OUT_EAST = Block.box(2, 0, 2, 16, 10, 14);
    protected static final VoxelShape OUT_SOUTH = Block.box(2, 0, 2, 14, 10, 16);
    protected static final VoxelShape OUT_WEST = Block.box(0, 0, 2, 14, 10, 14);
    protected static final VoxelShape OUT_NORTH = Block.box(2, 0, 0, 14, 10, 14);
    protected static final VoxelShape CENTER_EAST = Block.box(1, 0, 2, 16, 10, 14);
    protected static final VoxelShape CENTER_SOUTH = Block.box(2, 0, 1, 14, 10, 16);
    protected static final VoxelShape CENTER_WEST = Block.box(0, 0, 2, 15, 10, 14);
    protected static final VoxelShape CENTER_NORTH = Block.box(2, 0, 0, 14, 10, 15);


    public BaseCropressorBlock(Properties pProperties, Part part) {
        super(pProperties);
        PART = part;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = getConnectedDirection(pState);
        if(pState.getBlock() instanceof CropressorBlockOut) {
            return switch (direction) {
                case EAST -> OUT_EAST;
                case SOUTH -> OUT_SOUTH;
                case WEST -> OUT_WEST;
                default -> OUT_NORTH;
            };
        } else {
            return switch (direction) {
                case EAST -> CENTER_EAST;
                case SOUTH -> CENTER_SOUTH;
                case WEST -> CENTER_WEST;
                default -> CENTER_NORTH;
            };
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    private Direction getNeighbourDirection(CropressorBlockOut.Part part, Direction direction) {
        return part == CropressorBlockOut.Part.OUT ? direction : direction.getOpposite();
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if(!pLevel.isClientSide && pPlayer.isCreative()) {
            if(PART == CropressorBlockOut.Part.OUT) {
                BlockPos blockPos = pPos.relative(getNeighbourDirection(PART, pState.getValue(FACING)));
                BlockState blockState = pLevel.getBlockState(blockPos);
                if(getPartFromState(blockState) == CropressorBlockOut.Part.CENTER) {
                    pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, blockPos, Block.getId(blockState));
                }
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockPos = pContext.getClickedPos();
        BlockPos blockPos1 = blockPos.relative(direction);
        Level level = pContext.getLevel();

        return level.getBlockState(blockPos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockPos1) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    public static Direction getConnectedDirection(BlockState pState) {
        Direction direction = pState.getValue(FACING);
        return getPartFromState(pState) == Part.CENTER ? direction.getOpposite() : direction;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide) {
            BlockPos blockPos = pPos.relative(pState.getValue(FACING));
            pLevel.setBlockAndUpdate(blockPos, ModBlocks.CROPRESSOR_CENTER.get().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ENTITY_POS = PART == Part.OUT ? pPos : getEntityPos(pLevel, pPos);
        if (!pLevel.isClientSide && pLevel.getBlockEntity(ENTITY_POS) instanceof CropressorBlockEntity entity) {
            if (entity.isHasFinished()) {
                pPlayer.addItem(entity.getInventory().get(0));
            } else {
                entity.addItem(pPlayer.getMainHandItem());
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private BlockPos getEntityPos(Level level, BlockPos blockPos) {
        if(PART == Part.OUT) {
            return blockPos;
        }

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if(level.getBlockEntity(blockPos.relative(direction)) instanceof CropressorBlockEntity) {
                return blockPos.relative(direction);
            }
        }

        return null;
    }

    public static Part getPartFromState(BlockState blockState) {
        return blockState.getBlock() instanceof BaseCropressorBlock baseCropressorBlock ? baseCropressorBlock.PART : null;
    }

    public static enum Part implements StringRepresentable {
        CENTER("center"),
        OUT("out");

        private String name;

        Part(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
