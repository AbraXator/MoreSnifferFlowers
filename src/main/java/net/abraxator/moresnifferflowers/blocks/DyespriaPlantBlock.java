package net.abraxator.moresnifferflowers.blocks;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DyespriaPlantBlock extends BushBlock implements ModCropBlock, ModEntityBlock, Corruptable {
    public static final MapCodec<DyespriaPlantBlock> CODEC = simpleCodec(DyespriaPlantBlock::new);
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public DyespriaPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(getAgeProperty(), 0)
                .setValue(ModStateProperties.SHEARED, false)
                .setValue(ModStateProperties.COLOR, DyeColor.WHITE));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getAgeProperty()).add(ModStateProperties.COLOR).add(ModStateProperties.SHEARED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity pPlacer, ItemStack stack) {
        if(pPlacer instanceof ServerPlayer serverPlayer) {
            ModAdvancementCritters.PLACED_DYESPRIA_PLANT.get().trigger(serverPlayer);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(shear(player, level, pos, hand)){
            return ItemInteractionResult.SUCCESS;
        }
        if(stack.is(Items.BONE_MEAL)) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        } else if(isMaxAge(state) && level.getBlockEntity(pos) instanceof DyespriaPlantBlockEntity entity) {
            if(stack.getItem() instanceof DyeItem) {
                return addDye(stack, player, level, entity);

            } else if(shear(player, level, pos, hand)) {
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(isMaxAge(state) && level.getBlockEntity(pos) instanceof DyespriaPlantBlockEntity entity) {
            player.addItem(Dye.stackFromDye(entity.removeDye()));

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private ItemInteractionResult addDye(ItemStack dye, Player player, Level level, DyespriaPlantBlockEntity entity) {
        if(!level.isClientSide) {
            var stack = dye.copy();
            dye.setCount(-1);
            player.addItem(entity.add(null, entity.dye, stack));
        }
        
        level.playSound(null, entity.getBlockPos(), SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, (float) (1.0F + level.random.nextFloat() * 0.2));
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return canSurvive(state, level, pCurrentPos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return mayPlaceOn(level.getBlockState(pos.below()));
    }
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState pNewState, boolean pMovedByPiston) {
        if(!pNewState.is(ModBlocks.DYESCRAPIA_PLANT.get()) && !state.is(pNewState.getBlock()) && level.getBlockEntity(pos) instanceof DyespriaPlantBlockEntity entity && isMaxAge(state)) {
            ItemStack dyespria = ModItems.DYESPRIA.get().getDefaultInstance();

            dyespria.set(ModDataComponents.AMOUNT, entity.dye.amount());
            dyespria.set(ModDataComponents.COLOR_ID, entity.dye.colorId());

            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), dyespria);
        }

        super.onRemove(state, level, pos, pNewState, pMovedByPiston);
    }


    @Override
    public void onCorrupt(Level level, BlockPos pos, BlockState oldState, Block corruptedBlock) {
        if(level.getBlockEntity(pos) instanceof DyespriaPlantBlockEntity entity && isMaxAge(oldState)) {
            var dye = new ItemStack(DyeItem.byColor(entity.dye.color()), entity.dye.amount());

            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), dye);
        }
        
        Corruptable.super.onCorrupt(level, pos, oldState, corruptedBlock);
    }
    
    @Override
    public boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) && !(state.getBlock() instanceof FarmBlock);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_3;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return isMaxAge(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        makeGrowOnTick(state, level, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        makeGrowOnBonemeal(level, pos, state);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        if(player.isShiftKeyDown() && isMaxAge(state) && level.getBlockEntity(pos) instanceof DyespriaPlantBlockEntity entity) {
            var stack =  ModItems.DYESPRIA.toStack();
            stack.set(ModDataComponents.DYE, entity.dye);
            return stack;
        }
        
        return ModItems.DYESPRIA_SEEDS.toStack();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyespriaPlantBlockEntity(pos, state);
    }
}
