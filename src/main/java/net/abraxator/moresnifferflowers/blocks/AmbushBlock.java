package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AmbushBlock extends DoublePlantBlock implements BonemealableBlock, ModEntityBlock, ModCropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);
    public static final int MAX_AGE = 7;
    public static final int AGE_TO_GROW_UP = 4;

    public AmbushBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    private boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !isMaxAge(pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if(this.isHalf(pState, DoubleBlockHalf.UPPER)) {
            BlockState blockState = pLevel.getBlockState(pPos.below());
            boolean b = blockState.is(this);
            return b && isHalf(blockState, DoubleBlockHalf.LOWER);
        } else {
            return this.mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos.below()) && sufficientLight(pLevel, pPos) && pState.getValue(AGE) < AGE_TO_GROW_UP || isHalf(pLevel.getBlockState(pPos.above()), DoubleBlockHalf.UPPER);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.FARMLAND);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if(pEntity instanceof Ravager && pLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            pLevel.destroyBlock(pPos, true, pEntity);
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return false;
    }


    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        float f = getGrowthSpeed(this, pLevel, pPos);
        if(pRandom.nextInt((int) ((25.0F / f) + 1)) == 0) {
            this.grow(pLevel, pState, pPos, 1);
        }
    }

    private void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, int i) {
        int k = Math.min(pState.getValue(AGE) + i, MAX_AGE);
        if(this.canGrow(pLevel, pPos, pState, k)) {
            pLevel.setBlock(pPos, pState.setValue(AGE, k), 2);
            if(k >= AGE_TO_GROW_UP) {
                pLevel.setBlock(pPos.above(), this.defaultBlockState().setValue(AGE, k).setValue(HALF, DoubleBlockHalf.UPPER), 3);
            }

            if(pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity) {
                entity.growProgress = 0;
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack usedStack = pPlayer.getItemInHand(pHand);
        if(usedStack.is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if(pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity && entity.hasAmber) {
            popResource(pLevel, pPos, new ItemStack(ModBlocks.AMBER.get()));

            BlockPos lowerPos = isHalf(pState, DoubleBlockHalf.LOWER) ? pPos : pPos.below();
            BlockPos upperPos = isHalf(pState, DoubleBlockHalf.UPPER) ? pPos : pPos.above();
            BlockState lowerState = pLevel.getBlockState(lowerPos);
            BlockState upperState = pLevel.getBlockState(upperPos);
            pLevel.setBlock(lowerPos, lowerState, 3);
            pLevel.setBlock(upperPos, upperState, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, lowerPos, GameEvent.Context.of(pPlayer, lowerState));
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, upperPos, GameEvent.Context.of(pPlayer, upperState));

            entity.reset(pPos, pState, pLevel);
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir() || state.is(ModBlocks.AMBUSH.get());
    }

    private boolean sufficientLight(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getRawBrightness(pPos, 0) >= 8 || pLevel.canSeeSky(pPos);
    }

    private boolean isHalf(BlockState state, DoubleBlockHalf half) {
        return state.is(ModBlocks.AMBUSH.get()) && state.getValue(HALF) == half;
    }

    private boolean canGrow(LevelReader pLevel, BlockPos pPos, BlockState pState, int k) {
        return !this.isMaxAge(pState) && sufficientLight(pLevel, pPos) && (k < AGE_TO_GROW_UP || canGrowInto(pLevel.getBlockState(pPos.above())));
    }

    private PosAndState getLowerHalf(LevelReader level, BlockPos blockPos, BlockState state) {
        if(isHalf(state, DoubleBlockHalf.LOWER)) {
            return new PosAndState(blockPos, state);
        } else {
            BlockPos posBelow = blockPos.below();
            BlockState stateBelow = level.getBlockState(posBelow);
            return isHalf(stateBelow, DoubleBlockHalf.LOWER) ? new PosAndState(posBelow, stateBelow) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        PosAndState posAndState = this.getLowerHalf(pLevel, pPos, pState);
        return posAndState != null && this.canGrow(pLevel, posAndState.blockPos(), posAndState.state(), posAndState.state().getValue(AGE) + 1);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        PosAndState posAndState = this.getLowerHalf(pLevel, pPos, pState);
        if(posAndState != null) {
            this.grow(pLevel, posAndState.state(), posAndState.blockPos(), 1);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {}

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return ModItems.AMBUSH_SEEDS.get().getDefaultInstance();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AmbushBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.AMBUSH.get(), pLevel.isClientSide ? null : AmbushBlockEntity::tick);
    }
}
