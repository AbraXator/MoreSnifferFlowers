package net.abraxator.moresnifferflowers.blocks;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

public class DawnberryVineBlock extends MultifaceGrowthBlock implements Fertilizable, ModCropBlock {
    public static final IntProperty AGE = Properties.AGE_4;
    public static final BooleanProperty IS_SHEARED = BooleanProperty.of("is_sheared");
    public static final MapCodec<DawnberryVineBlock> CODEC = createCodec(DawnberryVineBlock::new);
    private final LichenGrower spreader = new LichenGrower(this);

    public DawnberryVineBlock(Settings pProperties) {
        super(pProperties);
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(IS_SHEARED, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        super.appendProperties(pBuilder);
        pBuilder.add(AGE, IS_SHEARED);
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getAge(BlockState pState) {
        return pState.get(this.getAgeProperty());
    }

    public int getMaxAge() {
        return AGE.getValues().stream().toList().get(AGE.getValues().size() - 1);
    }

    public final boolean isMaxAge(BlockState pState) {
        return this.getAge(pState) >= this.getMaxAge();
    }

    @Override
    public boolean hasRandomTicks(BlockState pState) {
        return !this.isMaxAge(pState) && !pState.get(IS_SHEARED);
    }

    @Override
    @SuppressWarnings("deprecated")
    public @NotNull ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getStackInHand(pHand);

        if(pLevel.isClient()) return ActionResult.PASS;

        if(itemStack.isOf(Items.SHEARS) && !(pState.get(AGE) >= 4)) {
            return shearAction(pState, pLevel, pPos, pPlayer, pHand, itemStack);
        } else if(itemStack.isOf(Items.BONE_MEAL)) {
            grow(((ServerWorld) pLevel), pLevel.getRandom(), pPos, pState);
        } else if(this.isMaxAge(pState)) {
            return dropMaxAgeLoot(pState, pLevel, pPos, pPlayer);
        } else if(pState.get(AGE) == 3) {
            return dropAgeThreeLoot(pState, pLevel, pPos, pPlayer);
        }

        return super.onUse(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private ActionResult shearAction(BlockState blockState, World level, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if(player instanceof ServerPlayerEntity serverPlayer) {
            Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
        }

        level.playSound(player, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS);
        BlockState state = blockState.with(IS_SHEARED, !blockState.get(IS_SHEARED));
        level.setBlockState(pos, state);
        level.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
        stack.damage(1, player, o -> o.sendToolBreakStatus(hand));
        return ActionResult.success(level.isClient);
    }

    private ActionResult dropMaxAgeLoot(BlockState blockState, World level, BlockPos pos, PlayerEntity player) {
        Random randomSource = level.getRandom();
        final ItemStack DAWNBERRY = new ItemStack(ModItems.DAWNBERRY, randomSource.nextBetween(1, 2));
        final ItemStack SEEDS = new ItemStack(ModItems.DAWNBERRY_VINE_SEEDS, randomSource.nextBetween(0, 1));

        dropStack(level, pos, DAWNBERRY);
        dropStack(level, pos, SEEDS);
        level.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        BlockState state = blockState.with(AGE, 2);
        level.setBlockState(pos, state, 2);
        level.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
        return ActionResult.success(level.isClient);
    }

    private ActionResult dropAgeThreeLoot(BlockState blockState, World level, BlockPos pos, PlayerEntity player) {
        final ItemStack DAWNBERRY = new ItemStack(ModItems.DAWNBERRY);
        dropStack(level, pos, DAWNBERRY);
        level.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        BlockState state = blockState.with(AGE, 2);
        level.setBlockState(pos, state, 2);
        level.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
        return ActionResult.success(level.isClient);
    }

    @Override
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        var grow = canCropGrow(this, pLevel, pPos, pState.get(AGE), getMaxAge());
        if(grow.getLeft()) {
            pLevel.setBlockState(pPos, pState.with(AGE, grow.getRight() + 1));
        }
    }

    @Override
    public LichenGrower getGrower() {
        return spreader;
    }

    @Override
    public boolean isFertilizable(WorldView pLevel, BlockPos pPos, BlockState pState) {
        return !isMaxAge(pState);
    }

    @Override
    public boolean canGrow(World pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void grow(ServerWorld pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        //grow(pState, pLevel, pPos, pRandom);
        int age = getAge(pState);
        pLevel.setBlockState(pPos, pState.with(AGE, age >= 4 ? age : age + 1), 2);
        boolean canSpread = Direction.stream().anyMatch((p_153316_) -> this.spreader.canGrow(pState, pLevel, pPos, p_153316_.getOpposite()));
        if(pRandom.nextFloat() >= 0.3F && pRandom.nextFloat() >= 0.3F && canSpread) {
            this.getGrower().grow(pState, pLevel, pPos, pRandom);
            this.getGrower().grow(pState, pLevel, pPos, pRandom);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockView pLevel, BlockPos pPos, ShapeContext pContext) {
        return VoxelShapes.empty();
    }
}
