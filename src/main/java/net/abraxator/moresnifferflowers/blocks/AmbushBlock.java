package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AmbushBlock extends DoublePlantBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final BooleanProperty IS_SHEARED = BooleanProperty.create("is_sheared");
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public AmbushBlock(Properties pProperties) {
        super(pProperties);
    }
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.FARMLAND);
    }
    protected ItemLike getBaseSeedId() {
        return Items.WHEAT_SEEDS;
    }

    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(this.getBaseSeedId());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE, IS_SHEARED);
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty());
    }

    public int getMaxAge() {
        return AGE.getPossibleValues().stream().toList().get(AGE.getPossibleValues().size() - 1);
    }

    public final boolean isMaxAge(BlockState pState) {
        return this.getAge(pState) >= this.getMaxAge();
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !this.isMaxAge(pState) && !pState.getValue(IS_SHEARED);
    }

    @Override
    @SuppressWarnings("deprecated")
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if(itemStack.is(Items.SHEARS)) {
            if(!(pState.getValue(AGE) >= 4)) {
                if(pPlayer instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pPos, itemStack);
                }

                pLevel.playSound(pPlayer, pPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS);
                BlockState state = pState.setValue(IS_SHEARED, !pState.getValue(IS_SHEARED));
                pLevel.setBlockAndUpdate(pPos, state);
                pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, state));
                itemStack.hurtAndBreak(1, pPlayer, player -> player.broadcastBreakEvent(pHand));
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    protected static float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();
        return f;
    }

    protected void grow(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom){
        if (!isMaxAge(pState)) {
            float f = getGrowthSpeed(this, pLevel, pPos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                pLevel.setBlock(pPos, pState.setValue(AGE, (pState.getValue(AGE) + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            grow(pState, pLevel, pPos, pRandom);
        }
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !isMaxAge(pState);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        grow(pState, pLevel, pPos, pRandom);
        pLevel.setBlock(pPos, pState.setValue(AGE, (pState.getValue(AGE) + 1)), 2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }
}
