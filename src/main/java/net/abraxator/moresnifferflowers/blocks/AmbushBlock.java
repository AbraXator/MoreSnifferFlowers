package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class AmbushBlock extends TallPlantBlock implements Fertilizable, ModEntityBlock, ModCropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final int MAX_AGE = 7;
    public static final int AGE_TO_GROW_UP = 4;

    public AmbushBlock(Settings pProperties) {
        super(pProperties);
        setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    private boolean isMaxAge(BlockState state) {
        return state.get(AGE) >= MAX_AGE;
    }

    @Override
    public boolean hasRandomTicks(BlockState pState) {
        return !isMaxAge(pState);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext pContext) {
        return this.getDefaultState();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState pState, Direction pFacing, BlockState pFacingState, WorldAccess pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(!pState.canPlaceAt(pLevel, pCurrentPos) && pLevel.getBlockEntity(pCurrentPos) instanceof AmbushBlockEntity entity && isHalf(pState, DoubleBlockHalf.UPPER) && entity.growProgress >= 1) {
            dropStack((World) pLevel, pCurrentPos, new ItemStack(ModBlocks.AMBER));
        }
        return !pState.canPlaceAt(pLevel, pCurrentPos) ? Blocks.AIR.getDefaultState() : pState;
    }

    @Override
    public boolean canPlaceAt(BlockState pState, WorldView pLevel, BlockPos pPos) {
        if(this.isHalf(pState, DoubleBlockHalf.UPPER)) {
            BlockState blockState = pLevel.getBlockState(pPos.down());
            boolean b = blockState.isOf(this);
            return b && isHalf(blockState, DoubleBlockHalf.LOWER);
        } else {
            return this.canPlantOnTop(pLevel.getBlockState(pPos.down()), pLevel, pPos.down()) && sufficientLight(pLevel, pPos) && pState.get(AGE) < AGE_TO_GROW_UP || isHalf(pLevel.getBlockState(pPos.up()), DoubleBlockHalf.UPPER);
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState pState, BlockView pLevel, BlockPos pPos) {
        return pState.isOf(Blocks.FARMLAND);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
        super.appendProperties(pBuilder);
    }

    @Override
    public void onEntityCollision(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
        if(pEntity instanceof RavagerEntity && pLevel.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            pLevel.breakBlock(pPos, true, pEntity);
        }

        super.onEntityCollision(pState, pLevel, pPos, pEntity);
    }

    @Override
    public boolean canReplace(BlockState pState, ItemPlacementContext pUseContext) {
        return false;
    }

    private boolean hasAmber(World level, BlockPos blockPos) {
        if(level.getBlockEntity(blockPos) instanceof AmbushBlockEntity entity) {
            return entity.hasGrown;
        }

        return false;
    }

    @Override
    public void randomDisplayTick(BlockState pState, World pLevel, BlockPos pPos, Random pRandom) {
        if(pState.get(AGE) <= MAX_AGE && pRandom.nextInt(100) < 10 && isHalf(pState, DoubleBlockHalf.LOWER)) {
            double dx = pPos.getX() + pRandom.nextDouble();
            double dy = pPos.getY() + pRandom.nextDouble();
            double dz = pPos.getZ() + pRandom.nextDouble();
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        float f = getGrowthSpeed(this, pLevel, pPos);
        if(pRandom.nextInt((int) ((25.0F / f) + 1)) == 0) {
            this.grow(pLevel, pState, pPos, 1);
        }
    }

    private void grow(ServerWorld pLevel, BlockState pState, BlockPos pPos, int i) {
        int k = Math.min(pState.get(AGE) + i, MAX_AGE);
        if(this.canGrow(pLevel, pPos, pState, k)) {
            pLevel.setBlockState(pPos, pState.with(AGE, k), 2);
            if(k >= AGE_TO_GROW_UP) {
                pLevel.setBlockState(pPos.up(), this.getDefaultState().with(AGE, k).with(HALF, DoubleBlockHalf.UPPER), 3);
            }

            if(pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity) {
                entity.growProgress = 0;
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        ItemStack usedStack = pPlayer.getStackInHand(pHand);
        if(usedStack.isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if(pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity && entity.hasGrown && isHalf(pState, DoubleBlockHalf.UPPER)) {
            dropStack(pLevel, pPos, new ItemStack(ModBlocks.AMBER));

            BlockPos lowerPos = isHalf(pState, DoubleBlockHalf.LOWER) ? pPos : pPos.down();
            BlockPos upperPos = isHalf(pState, DoubleBlockHalf.UPPER) ? pPos : pPos.up();
            BlockState lowerState = pLevel.getBlockState(lowerPos).with(AGE, 7);
            BlockState upperState = pLevel.getBlockState(upperPos).with(AGE, 7);
            pLevel.setBlockState(lowerPos, lowerState, 3);
            pLevel.setBlockState(upperPos, upperState, 3);
            pLevel.emitGameEvent(GameEvent.BLOCK_CHANGE, lowerPos, GameEvent.Emitter.of(pPlayer, lowerState));
            pLevel.emitGameEvent(GameEvent.BLOCK_CHANGE, upperPos, GameEvent.Emitter.of(pPlayer, upperState));

            entity.reset(pPos, pState, pLevel);
            return ActionResult.success(pLevel.isClient());
        } else {
            return super.onUse(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir() || state.isIn((RegistryEntryList<Block>) ModBlocks.AMBUSH);
    }

    private boolean sufficientLight(WorldView pLevel, BlockPos pPos) {
        return pLevel.getBaseLightLevel(pPos, 0) >= 8 || pLevel.isSkyVisible(pPos);
    }

    private boolean isHalf(BlockState state, DoubleBlockHalf half) {
        return state.isIn((RegistryEntryList<Block>) ModBlocks.AMBUSH) && state.get(HALF) == half;
    }

    private boolean canGrow(WorldView pLevel, BlockPos pPos, BlockState pState, int k) {
        return !this.isMaxAge(pState) && sufficientLight(pLevel, pPos) && (k < AGE_TO_GROW_UP || canGrowInto(pLevel.getBlockState(pPos.up())));
    }

    private PosAndState getLowerHalf(WorldView level, BlockPos blockPos, BlockState state) {
        if(isHalf(state, DoubleBlockHalf.LOWER)) {
            return new PosAndState(blockPos, state);
        } else {
            BlockPos posBelow = blockPos.down();
            BlockState stateBelow = level.getBlockState(posBelow);
            return isHalf(stateBelow, DoubleBlockHalf.LOWER) ? new PosAndState(posBelow, stateBelow) : null;
        }
    }

    @Override
    public boolean isFertilizable(WorldView pLevel, BlockPos pPos, BlockState pState) {
        PosAndState posAndState = this.getLowerHalf(pLevel, pPos, pState);
        return posAndState != null && this.canGrow(pLevel, posAndState.blockPos(), posAndState.state(), posAndState.state().get(AGE) + 1);
    }

    @Override
    public boolean canGrow(World pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void grow(ServerWorld pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        PosAndState posAndState = this.getLowerHalf(pLevel, pPos, pState);
        if(posAndState != null) {
            this.grow(pLevel, posAndState.state(), posAndState.blockPos(), 1);
        }
    }

    @Override
    public void onPlaced(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {}

//    @Override
//    public ItemStack getCloneItemStack(BlockState state, HitResult target, WorldView level, BlockPos pos, PlayerEntity player) {
//        return ModItems.AMBUSH_SEEDS.getDefaultStack();
//    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new AmbushBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }
}
