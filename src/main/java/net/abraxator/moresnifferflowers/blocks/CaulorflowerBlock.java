package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.CaulorflowerBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.items.FlowerPainter;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CaulorflowerBlock extends Block implements ModEntityBlock, BonemealableBlock{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public static final BooleanProperty HAS_COLOR = BooleanProperty.create("has_color");
    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");

    public CaulorflowerBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(FLIPPED, true).setValue(SHEARED, false).setValue(HAS_COLOR, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, FLIPPED, SHEARED, HAS_COLOR);
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
        return pLevel.getBlockState(blockPos).is(this) || blockState.isFaceSturdy(pLevel, blockPos, Direction.UP);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getMainHandItem();
        if(stack.is(Items.SHEARS)) {
            if(getHeighestPos(pLevel, pPos).isPresent()) {
                BlockPos blockPos = getHeighestPos(pLevel, pPos).get();
                BlockState blockState = pLevel.getBlockState(blockPos);
                pLevel.setBlockAndUpdate(blockPos, blockState.setValue(SHEARED, true));
                pLevel.playSound(pPlayer, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        grow(pLevel, pPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return getHeighestPos(pLevel, pPos).isPresent() && pLevel.getBlockState(getHeighestPos(pLevel, pPos).get().above()).is(Blocks.AIR);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        grow(pLevel, pPos);
    }

    protected void grow(ServerLevel pLevel, BlockPos clickePos) {
        getHeighestPos(pLevel, clickePos).ifPresent(highestPos -> {
            if(!pLevel.getBlockState(highestPos).getValue(SHEARED)) {
                BlockPos placedBlock = highestPos.above();
                BlockState blockState = pLevel.getBlockState(highestPos);
                pLevel.setBlockAndUpdate(placedBlock, blockState.setValue(FLIPPED, placedBlock.getY() % 2 == 0));
            }
        });
    }

    private Optional<BlockPos> getHeighestPos(BlockGetter level, BlockPos blockPos) {
        var pos = BlockUtil.getTopConnectedBlock(level, blockPos, this, Direction.UP, Blocks.AIR);
        return pos.map(BlockPos::below);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return NetherVines.getBlocksToGrowWhenBonemealed(pRandom);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CaulorflowerBlockEntity(pPos, pState);
    }
}
