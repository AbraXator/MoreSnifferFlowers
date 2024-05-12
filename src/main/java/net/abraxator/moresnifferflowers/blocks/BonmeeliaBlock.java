package net.abraxator.moresnifferflowers.blocks;

import com.sun.jna.platform.win32.Variant;
import net.abraxator.moresnifferflowers.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.internal.scripts.JO;

public class BonmeeliaBlock extends BushBlock implements ModEntityBlock, ModCropBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);
    public static final BooleanProperty HAS_BOTTLE = BooleanProperty.create("bottle");
    public static final BooleanProperty SHOW_HINT = BooleanProperty.create("hint");
    public static final BooleanProperty HAS_JAR = BooleanProperty.create("jar");
    public static final int MAX_AGE = AGE
            .getAllValues()
            .map(Property.Value::value)
            .max(Integer::compare)
            .orElse(0);

    public BonmeeliaBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(HAS_BOTTLE, false).setValue(SHOW_HINT, false).setValue(AGE, 0).setValue(HAS_JAR, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, HAS_BOTTLE, SHOW_HINT, HAS_JAR);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return this.mayPlaceOn(pLevel.getBlockState(pPos.below()));
    }

    @Override
    public boolean mayPlaceOn(BlockState pState) {
        return ModCropBlock.super.mayPlaceOn(pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getMainHandItem();
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        
        if (!(blockEntity instanceof BonmeeliaBlockEntity entity)) {
            return InteractionResult.PASS;
        }

        if (itemStack.is(Items.GLASS_BOTTLE) && canInsertBottle(pState)) {
            addBottle(pLevel, pPos, pState, itemStack);
        } else if (pState.getValue(HAS_BOTTLE) && pState.getValue(AGE) >= MAX_AGE) {
            takeJarOfBonmeel(pLevel, pPos, pState, pPlayer);
        } else if (!pState.getValue(HAS_BOTTLE) && getAge(pState) >= 3) {
            entity.displayHint();
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
    
        return InteractionResult.PASS;
    }
        
    private InteractionResult addBottle(Level level, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        level.setBlock(blockPos, blockState.setValue(HAS_BOTTLE, true), 3);
        stack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
    private InteractionResult takeJarOfBonmeel(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        level.setBlock(blockPos, blockState.setValue(AGE, 0).setValue(HAS_BOTTLE, false), 3);
        popResource(level, blockPos, ModItems.JAR_OF_BONMEEL.get().getDefaultInstance());
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return getAge(pState) < 3 || (getAge(pState) >= 3 && pState.getValue(HAS_BOTTLE));
    }

    private boolean canInsertBottle(BlockState blockState) {
        return blockState.getValue(AGE) == 3 && !blockState.getValue(HAS_BOTTLE);
    }
    
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(!isMaxAge(pState)) {
            pLevel.setBlockAndUpdate(pPos, pState
                    .setValue(AGE, getAge(pState) + 1)
                    .setValue(HAS_JAR, (getAge(pState) + 1) == MAX_AGE && pState.getValue(HAS_BOTTLE)));
            var particle = new DustParticleOptions(Vec3.fromRGB24(11162034).toVector3f(), 1F);
            if(getAge(pState) >= 3) {
                for (int i = 0; i <= pRandom.nextIntBetweenInclusive(5, 10); i++) {
                    pLevel.sendParticles(
                            particle,
                            pPos.getX() + pRandom.nextDouble(),
                            pPos.getY() + pRandom.nextDouble(),
                            pPos.getZ() + pRandom.nextDouble(),
                            1, 0, 0, 0, 0.3D);
                }
            }   
        }
    }
    
    public static void displayHint(Level level, BlockPos blockPos, BlockState blockState, boolean show) {
        level.setBlock(blockPos, blockState.setValue(SHOW_HINT, show), 3);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BonmeeliaBlockEntity(pPos, pState);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return getAge(pState) < 3;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        makeGrowOnBonemeal(pLevel, pPos, pState);
    }
}
