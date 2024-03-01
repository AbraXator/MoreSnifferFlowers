package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CaulorflowerBlock extends Block implements BonemealableBlock, ModCropBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);

    private static final int MAX_STAGE = 5;

    public CaulorflowerBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(FLIPPED, true)
                .setValue(COLOR, DyeColor.WHITE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, FLIPPED, COLOR);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(canSurvive(pState, pLevel, pCurrentPos)) {
            return pState.setValue(FLIPPED, pCurrentPos.getY() % 2 == 0);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
        if(state.is(this)) {
            return state.setValue(FLIPPED, pContext.getClickedPos().getY() % 2 == 0);
        }
        return super.getStateForPlacement(pContext).setValue(FLIPPED, pContext.getClickedPos().getY() % 2 == 0).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockPos = pPos.below();
        BlockState blockState = pLevel.getBlockState(blockPos);
        BlockPos wallPos = pPos.relative(pState.getValue(FACING).getOpposite());
        BlockState wallState = pLevel.getBlockState(wallPos);
        return pLevel.getBlockState(blockPos).is(this) || blockState.isFaceSturdy(pLevel, blockPos, Direction.UP) || wallState.isFaceSturdy(pLevel, wallPos, pState.getValue(FACING));
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if(pRandom.nextFloat() < 0.15) {
            grow(pLevel, pPos, false);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return getHeighestPos(pLevel, pPos, true).isPresent() && pLevel.getBlockState(getHeighestPos(pLevel, pPos, true).get().above()).is(Blocks.AIR);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        grow(pLevel, pPos, true);
    }

    protected void grow(ServerLevel pLevel, BlockPos pos, boolean bonemeal) {
        getHeighestPos(pLevel, pos, bonemeal).ifPresent(highestPos -> {
            BlockPos placedBlock = highestPos.above();
            BlockState highestBlockState = pLevel.getBlockState(highestPos);
            BlockState currentState = pLevel.getBlockState(pos);
            pLevel.setBlockAndUpdate(placedBlock, highestBlockState.setValue(FLIPPED, placedBlock.getY() % 2 == 0));
        });
    }

    private Optional<BlockPos> getHeighestPos(BlockGetter level, BlockPos blockPos, boolean bonemeal) {
        var pos = getTopConnectedBlock(level, getLowestPos(level, blockPos).get(), this, Direction.UP);
        return pos.filter(blockPos1 -> bonemeal || !(blockPos1.below().getY() > (getLowestPos(level, blockPos).get().getY() + 5))).map(BlockPos::below);
    }

    private Optional<BlockPos> getLowestPos(BlockGetter level, BlockPos blockPos) {
        var posDown = getTopConnectedBlock(level, blockPos, this, Direction.DOWN).map(BlockPos::above);
        return level.getBlockState(posDown.get()).is(this) ? posDown : Optional.empty();
    }

    public static Optional<BlockPos> getTopConnectedBlock(BlockGetter pGetter, BlockPos pPos, Block pBaseBlock, Direction pDirection) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        BlockState blockstate;
        do {
            blockpos$mutableblockpos.move(pDirection);
            blockstate = pGetter.getBlockState(blockpos$mutableblockpos);
        } while(blockstate.is(pBaseBlock));

        return pDirection == Direction.DOWN ? Optional.of(blockpos$mutableblockpos.below()) : (blockstate.is(Blocks.AIR) ? Optional.of(blockpos$mutableblockpos) : Optional.empty());
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
    }
}
