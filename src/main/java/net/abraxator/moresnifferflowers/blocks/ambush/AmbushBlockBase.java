package net.abraxator.moresnifferflowers.blocks.ambush;

import net.abraxator.moresnifferflowers.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.ModCropBlock;
import net.abraxator.moresnifferflowers.blocks.ModEntityDoubleTallBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import org.jetbrains.annotations.Nullable;

public class AmbushBlockBase extends ModEntityDoubleTallBlock implements ModCropBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    public static final int AGE_TO_GROW_UP = 4;

    public AmbushBlockBase(Properties pProperties) {
        super(pProperties);
    }
    
    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_8;
    }

    @Override   
    public boolean isRandomlyTicking(BlockState pState) {
        return !isMaxAge(pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(isUpper(pState) && pLevel.getBlockEntity(pPos) instanceof AmbushBlockEntity entity && entity.hasGrown) {
            return super.getShape(pState, pLevel, pPos, pContext);
        } else {
            return SHAPE;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return this.mayPlaceOn(pLevel.getBlockState(pPos.below())) && sufficientLight(pLevel, pPos) && super.canSurvive(pState, pLevel, pPos);
    }
    
    @Override
    public boolean mayPlaceOn(BlockState pState) {
        return ModCropBlock.super.mayPlaceOn(pState) || pState.is(ModBlocks.REBREWING_STAND_BOTTOM.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ModStateProperties.AGE_8);
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
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(getAge(pState) == 7 && pRandom.nextInt(100) < 10 && isLower(pState)) {
            double dx = pPos.getX() + pRandom.nextDouble();
            double dy = pPos.getY() + pRandom.nextDouble();
            double dz = pPos.getZ() + pRandom.nextDouble();
            pLevel.addParticle(ModParticles.AMBUSH.get(), dx, dy, dz, 0, 0, 0);
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        float f = getGrowthSpeed(this, pLevel, pPos);
        if(pRandom.nextInt((int) ((25.0F / f) + 1)) == 0) {
            this.grow(pLevel, pState, pPos, 1);
        }
    }

    public void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, int i) {
        int k = Math.min(getAge(pState) + i, getMaxAge());
        if(this.canGrow(pLevel, pPos, pState, k)) {
            pLevel.setBlock(pPos, pState.setValue(getAgeProperty(), k), 2);
            if(k >= AGE_TO_GROW_UP && isLower(pState)) {
                pLevel.setBlock(pPos.above(), ModBlocks.AMBUSH_TOP.get().defaultBlockState().setValue(getAgeProperty(), k), 3);
            }

            if(pLevel.getBlockEntity(getLowerHalf(pLevel, pPos, pState).blockPos().above()) instanceof AmbushBlockEntity entity) {
                entity.growProgress = 0;
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.getBlockEntity(getLowerHalf(pLevel, pPos, pState).blockPos().above()) instanceof AmbushBlockEntity entity && entity.hasGrown) {
            var lowerPos = isLower(pState) ? pPos : pPos.below();
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(ModBlocks.AMBER.get()));
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            for(int i = 0; i <= 1; i++) {
                var halfPos = i == 0 ? lowerPos : lowerPos.above();
                var state = pLevel.getBlockState(halfPos).setValue(getAgeProperty(), 7);
                pLevel.setBlock(halfPos, state, 3);
                pLevel.gameEvent(GameEvent.BLOCK_CHANGE, halfPos, GameEvent.Context.of(pPlayer, state));
            }

            entity.reset();
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        } else {
            return InteractionResult.PASS;
        }
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir() || state.is(ModBlocks.AMBUSH_TOP.get());
    }

    private boolean sufficientLight(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getRawBrightness(pPos, 0) >= 8 || pLevel.canSeeSky(pPos);
    }

    @Override
    public int getMaxAge() {
        return ModCropBlock.super.getMaxAge() - 1;
    }

    private boolean canGrow(LevelReader pLevel, BlockPos pPos, BlockState pState, int k) {
        return !this.isMaxAge(pState) && sufficientLight(pLevel, pPos) && (k < AGE_TO_GROW_UP || canGrowInto(pLevel.getBlockState(pPos.above()))) && isLower(pState);
    }

    private PosAndState getLowerHalf(LevelReader level, BlockPos blockPos, BlockState state) {
        if(isLower(state)) {
            return new PosAndState(blockPos, state);
        } else {
            BlockPos posBelow = blockPos.below();
            BlockState stateBelow = level.getBlockState(posBelow);
            return isLower(stateBelow) ? new PosAndState(posBelow, stateBelow) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean b) {
        PosAndState posAndState = this.getLowerHalf(levelReader, blockPos, blockState);
        return posAndState != null && this.canGrow(levelReader, posAndState.blockPos(), posAndState.state(), getAge(posAndState.state()) + 1);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        PosAndState posAndState = this.getLowerHalf(pLevel, pPos, pState);
        if(posAndState != null && pState.getValue(ModStateProperties.AGE_8) < 8) {
            this.grow(pLevel, posAndState.state(), posAndState.blockPos(), 1);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {}

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ModItems.AMBUSH_SEEDS.get().getDefaultInstance();
    }

    @Override
    public Block getLowerBlock() {
        return ModBlocks.AMBUSH_BOTTOM.get();
    }

    @Override
    public Block getUpperBlock() {
        return ModBlocks.AMBUSH_TOP.get();
    }

    @Override
    public BlockState getPlant(BlockGetter blockGetter, BlockPos blockPos) {
        return blockGetter.getBlockState(blockPos);
    }
}
