package net.abraxator.moresnifferflowers.blocks;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.blocks.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BonmeeliaBlock extends PlantBlock implements ModEntityBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);
    public static final BooleanProperty HAS_BOTTLE = BooleanProperty.of("bottle");
    public static final BooleanProperty SHOW_HINT = BooleanProperty.of("hint");
    public static final BooleanProperty HAS_JAR = BooleanProperty.of("jar");
    public static final MapCodec<BonmeeliaBlock> CODEC = createCodec(BonmeeliaBlock::new);
    public static final int MAX_AGE = AGE
            .stream()
            .map(Property.Value::value)
            .max(Integer::compare)
            .orElse(0);

    public BonmeeliaBlock(Settings pProperties) {
        super(pProperties);
        setDefaultState(this.getDefaultState().with(HAS_BOTTLE, false).with(SHOW_HINT, false).with(AGE, 0).with(HAS_JAR, false));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, HAS_BOTTLE, SHOW_HINT, HAS_JAR);
    }

    @Override
    public boolean canPlaceAt(BlockState pState, WorldView pLevel, BlockPos pPos) {
        pPos = pPos.down();
        return canPlantOnTop(pLevel.getBlockState(pPos), pLevel, pPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState pState, BlockView pLevel, BlockPos pPos) {
        return pState.isOf(Blocks.FARMLAND);
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getMainHandStack();
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if (!(blockEntity instanceof BonmeeliaBlockEntity entity)) {
            return ActionResult.FAIL;
        }

        if(itemStack.isOf(Items.GLASS_BOTTLE) && !pState.get(HAS_BOTTLE)) {
            pLevel.setBlockState(pPos, pState.with(HAS_BOTTLE, true), 3);
            pPlayer.getMainHandStack().decrement(1);
        } else if (pState.get(HAS_BOTTLE) && pState.get(AGE) >= MAX_AGE) {
            pLevel.setBlockState(pPos, pState.with(AGE, 0).with(HAS_BOTTLE, false), 3);
            pPlayer.giveItemStack(ModItems.JAR_OF_BONMEEL.get().getDefaultInstance());
        } else if(!pState.get(HAS_BOTTLE)) {
            entity.displayHint();
        }

        return ActionResult.success(pLevel.isClient);
    }

    @Override
    public boolean hasRandomTicks(BlockState pState) {
        return pState.get(AGE) < MAX_AGE && pState.get(HAS_BOTTLE);
    }

    @Override
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        pLevel.setBlockState(pPos, pState
                .with(AGE, getCurrentAge(pState) + 1)
                .with(HAS_JAR, (getCurrentAge(pState) + 1) == MAX_AGE && pState.get(HAS_BOTTLE)));
        var particle = new DustParticleEffect(Vec3d.unpackRgb(11162034).toVector3f(), 1F);
        for(int i = 0; i <= pRandom.nextBetween(5, 10); i++) {
            pLevel.spawnParticles(
                    particle,
                    pPos.getX() + pRandom.nextDouble(),
                    pPos.getY() + pRandom.nextDouble(),
                    pPos.getZ() + pRandom.nextDouble(),
                    1, 0, 0, 0, 0.3D);
        }
    }

    private int getCurrentAge(BlockState blockState) {
        return blockState.get(AGE);
    }

    public static void displayHint(World level, BlockPos blockPos, BlockState blockState, boolean show) {
        level.setBlockState(blockPos, blockState.with(SHOW_HINT, show), 3);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new BonmeeliaBlockEntity(pPos, pState);
    }
}
