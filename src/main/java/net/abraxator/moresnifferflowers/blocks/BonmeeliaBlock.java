package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import javax.lang.model.util.ElementScanner6;
import java.awt.*;

public class BonmeeliaBlock extends Block implements ModEntityBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    public static final BooleanProperty HAS_BOTTLE = BooleanProperty.create("bottle");
    public static final BooleanProperty SHOW_HINT = BooleanProperty.create("hint");
    public static final int MAX_AGE = AGE
            .getAllValues()
            .map(Property.Value::value)
            .max(Integer::compare)
            .orElse(0);

    public BonmeeliaBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(HAS_BOTTLE, false).setValue(SHOW_HINT, false).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, HAS_BOTTLE, SHOW_HINT);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getMainHandItem();
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if (!(blockEntity instanceof BonmeeliaBlockEntity entity)) {
            return InteractionResult.FAIL;
        }

        if(itemStack.is(Items.GLASS_BOTTLE) && !pState.getValue(HAS_BOTTLE)) {
            //entity.giveBottle(itemStack);
            pLevel.setBlock(pPos, pState.setValue(HAS_BOTTLE, true), 3);
        } else if (pState.getValue(HAS_BOTTLE) && pState.getValue(AGE) >= MAX_AGE) {
            pLevel.setBlock(pPos, pState.setValue(AGE, 0).setValue(HAS_BOTTLE, false), 3);
            pPlayer.addItem(ModItems.JAR_OF_BONMEEL.get().getDefaultInstance());
        } else if(!pState.getValue(HAS_BOTTLE)) {
            entity.displayHint();
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < MAX_AGE && pState.getValue(HAS_BOTTLE);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, getCurrentAge(pState) + 1));
        var particle = new DustParticleOptions(Vec3.fromRGB24(11162034).toVector3f(), 0.5F);
        Vec3 center = pPos.getCenter();
        double r = (double)(0.4F - (pRandom.nextFloat() + pRandom.nextFloat()) * 0.4F);
        double x = center.x * r;
        double y = center.y * r;
        double z = center.z * r;
        pLevel.sendParticles(particle, x, y, z, 10, pRandom.nextGaussian() * 0.5, pRandom.nextGaussian() * 0.5, pRandom.nextGaussian() * 0.5, 0.5D);
    }

    private int getCurrentAge(BlockState blockState) {
        return blockState.getValue(AGE);
    }

    public static void displayHint(Level level, BlockPos blockPos, BlockState blockState, boolean show) {
        level.setBlock(blockPos, blockState.setValue(SHOW_HINT, show), 3);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel, pState, pBlockEntityType);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BonmeeliaBlockEntity(pPos, pState);
    }
}
