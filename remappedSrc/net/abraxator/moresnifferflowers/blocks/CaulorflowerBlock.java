package net.abraxator.moresnifferflowers.blocks;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CaulorflowerBlock extends Block implements Fertilizable, ModCropBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty FLIPPED = BooleanProperty.of("flipped");
    public static final BooleanProperty HAS_COLOR = BooleanProperty.of("has_color");
    public static final BooleanProperty SHEARED = BooleanProperty.of("sheared");
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);

    public CaulorflowerBlock(Settings pProperties) {
        super(pProperties);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(FLIPPED, true).with(SHEARED, false).with(COLOR, DyeColor.WHITE).with(HAS_COLOR, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        super.appendProperties(pBuilder);
        pBuilder.add(FACING, FLIPPED, SHEARED, HAS_COLOR, COLOR);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState pState, Direction pFacing, BlockState pFacingState, WorldAccess pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(canPlaceAt(pState, pLevel, pCurrentPos)) {
            return pState.with(FLIPPED, pCurrentPos.getY() % 2 == 0);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext pContext) {
        BlockState state = pContext.getWorld().getBlockState(pContext.getBlockPos().down());
        if(state.isOf(this)) {
            return state.with(FLIPPED, pContext.getBlockPos().getY() % 2 == 0);
        }
        return super.getPlacementState(pContext).with(FLIPPED, pContext.getBlockPos().getY() % 2 == 0).with(FACING, pContext.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public boolean canPlaceAt(BlockState pState, WorldView pLevel, BlockPos pPos) {
        BlockPos blockPos = pPos.down();
        BlockState blockState = pLevel.getBlockState(blockPos);
        BlockPos wallPos = pPos.offset(pState.get(FACING).getOpposite());
        BlockState wallState = pLevel.getBlockState(wallPos);
        return pLevel.getBlockState(blockPos).isOf(this) || blockState.isSideSolidFullSquare(pLevel, blockPos, Direction.UP) || wallState.isSideSolidFullSquare(pLevel, wallPos, pState.get(FACING));
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getMainHandStack();
        if(stack.isOf(Items.SHEARS)) {
            if(getHeighestPos(pLevel, pPos).isPresent()) {
                BlockPos blockPos = getHeighestPos(pLevel, pPos).get();
                BlockState blockState = pLevel.getBlockState(blockPos);
                pLevel.setBlockState(blockPos, blockState.with(SHEARED, true));
                pLevel.playSound(pPlayer, pPos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS);
                return ActionResult.success(pLevel.isClient);
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        getHeighestPos(pLevel, pPos).ifPresent(blockPos -> {
            float f = getGrowthSpeed(this, pLevel, blockPos);
            if(pRandom.nextFloat() < 0.15) {
                grow(pLevel, pPos);
            }
        });
    }

    @Override
    public boolean isFertilizable(WorldView pLevel, BlockPos pPos, BlockState pState) {
        return getHeighestPos(pLevel, pPos).isPresent() && pLevel.getBlockState(getHeighestPos(pLevel, pPos).get().up()).isOf(Blocks.AIR);
    }

    @Override
    public boolean canGrow(World pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void grow(ServerWorld pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        grow(pLevel, pPos);
    }

    protected void grow(ServerWorld pLevel, BlockPos clickePos) {
        getHeighestPos(pLevel, clickePos).ifPresent(highestPos -> {
            if(!pLevel.getBlockState(highestPos).get(SHEARED)) {
                BlockPos placedBlock = highestPos.up();
                BlockState blockState = pLevel.getBlockState(highestPos);
                pLevel.setBlockState(placedBlock, blockState.with(FLIPPED, placedBlock.getY() % 2 == 0));
            }
        });
    }

    private Optional<BlockPos> getHeighestPos(BlockView level, BlockPos blockPos) {
        var pos = BlockLocating.findColumnEnd(level, blockPos, this, Direction.UP, Blocks.AIR);
        return pos.map(BlockPos::down);
    }

    @Override
    public BlockState mirror(BlockState pState, BlockMirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.get(FACING)));
    }

    @Override
    public BlockState getPlant(BlockView level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() != this) return getDefaultState();
        return state;
    }
}
