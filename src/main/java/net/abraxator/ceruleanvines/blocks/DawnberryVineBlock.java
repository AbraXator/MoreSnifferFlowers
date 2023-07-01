package net.abraxator.ceruleanvines.blocks;

import net.abraxator.ceruleanvines.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

public class DawnberryVineBlock extends MultifaceBlock implements BonemealableBlock{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public DawnberryVineBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos);
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
        return !this.isMaxAge(pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(this.isMaxAge(pState)) {
            RandomSource randomSource = pLevel.getRandom();
            final ItemStack DAWNBERRY = new ItemStack(ModItems.DAWNBERRY.get(), randomSource.nextIntBetweenInclusive(1, 4));
            final ItemStack SEEDS = new ItemStack(ModItems.DAWNBERRY_VINE_SEEDS.get(), randomSource.nextIntBetweenInclusive(0, 1));
            popResource(pLevel, pPos, DAWNBERRY);
            popResource(pLevel, pPos, SEEDS);
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            pLevel.setBlock(pPos, pState.setValue(AGE, 2), 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, pState));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
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
    public MultifaceSpreader getSpreader() {
        return spreader;
    }

    protected static float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = pLevel.getBlockState(blockpos.offset(i, 0, j));

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = pLevel.getBlockState(blockpos3).is(pBlock) || pLevel.getBlockState(blockpos4).is(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).is(pBlock) || pLevel.getBlockState(blockpos2).is(pBlock);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).is(pBlock) || pLevel.getBlockState(blockpos4.north()).is(pBlock) || pLevel.getBlockState(blockpos4.south()).is(pBlock) || pLevel.getBlockState(blockpos3.south()).is(pBlock);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    private void canSpread(){

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
        if(pRandom.nextFloat() >= 0.3F) {
            boolean canSpread = Direction.stream().anyMatch((p_153316_) -> this.spreader.canSpreadInAnyDirection(pState, pLevel, pPos, p_153316_.getOpposite()));
            if(pRandom.nextFloat() >= 0.3F && canSpread) {
                this.getSpreader().spreadFromRandomFaceTowardRandomDirection(pState, pLevel, pPos, pRandom);
                this.getSpreader().spreadFromRandomFaceTowardRandomDirection(pState, pLevel, pPos, pRandom);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }
}
