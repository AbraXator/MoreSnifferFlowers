package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.blockentities.MultiBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.PreviewableMultiblock;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import vectorwing.farmersdelight.common.block.RiceBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;


public class GiantCropBlock extends Block implements ModEntityBlock, Bonmeelable, PreviewableMultiblock, SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE_POTATO = makeShapePotato();
    public static final VoxelShape SHAPE_CARROT = makeShapeCarrot();
    public static final VoxelShape SHAPE_BEET = makeShapeBeet();
    public static final VoxelShape SHAPE_NETHERWART = makeShapeWart();
    public static final VoxelShape SHAPE_WHEAT = makeShapeWheat();
    public static final VoxelShape SHAPE_ONION = makeShapeOnion();
    public static final VoxelShape SHAPE_TOMATO = makeShapeTomato();
    public static final VoxelShape SHAPE_CABBAGE = makeShapeCabbage();
    public static final VoxelShape SHAPE_RICE = makeShapeRice();

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BlockBehaviour.StatePredicate STATE_PREDICATE = (p_152641_, p_152642_, p_152643_) -> p_152641_.getValue(ModStateProperties.CENTER);

    public GiantCropBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(ModStateProperties.CENTER, false).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public Stream<BlockPos> fullBlockShape(@Nullable Direction direction, BlockPos center) {
        if (this.equals(ModBlocks.GIANT_CABBAGE.get())){
            return BlockPos.betweenClosedStream(
                    center.getX() - 1,
                    center.getY() - 1,
                    center.getZ() - 1,
                    center.getX() + 1,
                    center.getY(),
                    center.getZ() + 1
            );
        }
        return BlockPos.betweenClosedStream(
                center.getX() - 1,
                center.getY() - 1,
                center.getZ() - 1,
                center.getX() + 1,
                center.getY() + 1,
                center.getZ() + 1
        );
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if (isCenter(state)) return RenderShape.ENTITYBLOCK_ANIMATED;
        return RenderShape.INVISIBLE;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
            if(entity.state == 1) {
                entity.canGrow = true;
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSurviveHelper(state, level, pos);
    }

    @Override
    public boolean canPlace(LevelReader level, BlockPos center, BlockState state) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return updateShapeHelper(state, level, pos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public ItemStack pickupBlock(@javax.annotation.Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        return player != null && player.isCreative()
                ? SimpleWaterloggedBlock.super.pickupBlock(player, level, pos, state)
                : ItemStack.EMPTY;
    }

    @Override
    public boolean canPlaceLiquid(@javax.annotation.Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return player != null && player.isCreative() && SimpleWaterloggedBlock.super.canPlaceLiquid(player, level, pos, state, fluid);
    }


    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState pOldState, boolean pMovedByPiston) {
        if(state.getValue(ModStateProperties.CENTER)) {
            level.getBlockTicks().schedule(new ScheduledTick<>(this, pos, level.getGameTime() + 7, level.nextSubTickCount()));
            if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity && entity.state == 0) {
                entity.state = 1;
            }

            if(level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticles.GIANT_CROP.get(), pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ModStateProperties.CENTER, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GiantCropBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(level);
    }

    @Override
    public void performBonmeel(BlockPos blockPos, BlockState blockState, Level level, Player player) {
        if (isRicePanicles(blockState.getBlock())) blockPos = blockPos.below();

        BlockPos finalBlockPos = blockPos;
        this.fullBlockShape(null, blockPos.above()).forEach(pos -> {
            pos = pos.immutable();
            boolean isWaterLogged = level.getFluidState(pos).getType() == Fluids.WATER;

            level.destroyBlock(pos, false);

            BlockState state = getCropMap().get(blockState.getBlock()).getA().defaultBlockState()
                    .setValue(ModStateProperties.CENTER, pos.equals(finalBlockPos.above()))
                    .setValue(WATERLOGGED, isWaterLogged);

            level.setBlockAndUpdate(pos, state);

            if(level.getBlockEntity(pos) instanceof MultiBlockEntity entity) {
                entity.setCenter(finalBlockPos.above());
            }
        });

        if(player != null) {

            if (player instanceof ServerPlayer serverPlayer) {
                ModAdvancementCritters.USED_BONMEEL.get().trigger(serverPlayer);
            }
        }

        level.playLocalSound(blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
    }

    @Override
    public boolean canBonmeel(BlockPos blockPos, BlockState blockState, Level level) {
        Block crop = blockState.getBlock();
        int cropY = blockPos.getY();

        if (isRicePanicles(crop)){
            blockPos = blockPos.below();
        }

        return fullBlockShape(null, blockPos.above()).allMatch(pos -> {
            BlockState state = level.getBlockState(pos);
            var PROPERTY = getCropMap().get(crop).getB().getA();
            int MAX_AGE = getCropMap().get(crop).getB().getB();

            if(pos.getY() == cropY) {
                return state.is(crop) && state.is(ModTags.ModBlockTags.BONMEELABLE) && state.getValue(PROPERTY) == MAX_AGE;
            } else {
                return state.canBeReplaced() || state.is(ModTags.ModBlockTags.GIANT_CROP_REPLACEABLE) || state.is(crop);
            }
        });
    }

    private static boolean isRicePanicles(Block crop) {
        return crop.equals(BuiltInRegistries.BLOCK.get(MoreSnifferFlowers.farmersDelightLoc("rice_panicles")));
    }

    private static Map<Block, Pair<Block, Pair<IntegerProperty, Integer>>> cropMapCompat() {
        return Map.of(
                Blocks.CARROTS, new Pair<>(ModBlocks.GIANT_CARROT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                Blocks.POTATOES, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                Blocks.NETHER_WART, new Pair<>(ModBlocks.GIANT_NETHERWART.get(), new Pair<>(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE)),
                Blocks.BEETROOTS, new Pair<>(ModBlocks.GIANT_BEETROOT.get(), new Pair<>(BeetrootBlock.AGE, BeetrootBlock.MAX_AGE)),
                Blocks.WHEAT, new Pair<>(ModBlocks.GIANT_WHEAT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),

                vectorwing.farmersdelight.common.registry.ModBlocks.ONION_CROP.get(), new Pair<>(ModBlocks.GIANT_ONION.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                vectorwing.farmersdelight.common.registry.ModBlocks.TOMATO_CROP.get(), new Pair<>(ModBlocks.GIANT_TOMATO.get(), new Pair<>(TomatoVineBlock.VINE_AGE, 3)),
                vectorwing.farmersdelight.common.registry.ModBlocks.CABBAGE_CROP.get(), new Pair<>(ModBlocks.GIANT_CABBAGE.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                vectorwing.farmersdelight.common.registry.ModBlocks.RICE_CROP_PANICLES.get(), new Pair<>(ModBlocks.GIANT_RICE.get(), new Pair<>(RiceBlock.AGE, 3))

        );
    }

    private static Map<Block, Pair<Block, Pair<IntegerProperty, Integer>>> cropMapVanilla() {
        return Map.of(
                Blocks.CARROTS, new Pair<>(ModBlocks.GIANT_CARROT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                Blocks.POTATOES, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
                Blocks.NETHER_WART, new Pair<>(ModBlocks.GIANT_NETHERWART.get(), new Pair<>(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE)),
                Blocks.BEETROOTS, new Pair<>(ModBlocks.GIANT_BEETROOT.get(), new Pair<>(BeetrootBlock.AGE, BeetrootBlock.MAX_AGE)),
                Blocks.WHEAT, new Pair<>(ModBlocks.GIANT_WHEAT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE))
        );
    }

    public static Map<Block, Pair<Block, Pair<IntegerProperty, Integer>>> getCropMap() {
        return MoreSnifferFlowers.hasFarmersDelight() ? cropMapCompat() : cropMapVanilla();
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.block();

        if (getXOffset(getter, pos) == 0 && getZOffset(getter, pos) == 0){
            return shape;
        }

        if (this.equals(ModBlocks.GIANT_POTATO.get())) shape = SHAPE_POTATO;
        if (this.equals(ModBlocks.GIANT_CARROT.get())) shape = SHAPE_CARROT;
        if (this.equals(ModBlocks.GIANT_BEETROOT.get())) shape = SHAPE_BEET;
        if (this.equals(ModBlocks.GIANT_NETHERWART.get())) shape = SHAPE_NETHERWART;
        if (this.equals(ModBlocks.GIANT_WHEAT.get())) shape = SHAPE_WHEAT;
        if (this.equals(ModBlocks.GIANT_ONION.get())) shape = SHAPE_ONION;
        if (this.equals(ModBlocks.GIANT_TOMATO.get())) shape = SHAPE_TOMATO;
        if (this.equals(ModBlocks.GIANT_CABBAGE.get())) shape = SHAPE_CABBAGE;
        if (this.equals(ModBlocks.GIANT_RICE.get())) shape = SHAPE_RICE;


        return voxelShapeHelper(state, getter, pos, shape, 0, -1, 0);
    }

    public static VoxelShape makeShapePotato(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.4375, -0.0625, -0.4375, 1.4375, 2.125, 1.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.3125, 2.125, -0.3125, 1.3125, 2.375, 1.3125), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeWheat(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.5625, -0.0625, -0.5625, 1.5625, 2.9375, 1.5625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeCarrot(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.6875, -0.0625, -0.6875, 1.6875, 0.4375, 1.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.4375, 0.375, -0.4375, 1.4375, 1.875, 1.4375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeWart(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.5625, 1.25, -0.5625, 1.5625, 3, 1.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, -0.125, 1.125, 1.25, 1.125), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeBeet(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.375, -0.0625, -0.375, 1.375, 1.75, 1.375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeCabbage(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.5625, 0, -0.5625, 1.5625, 1.5, 1.5625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeOnion(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.33, 0, -0.325, 1.3, 2.0, 1.3), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeTomato(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.437, -0.0625, -0.405, 1.4375, 1.8125, 1.465), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeShapeRice(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 2.5, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.1875, -0.0625, -0.1875, 1.1875, 0.8125, 1.1875), BooleanOp.OR);

        return shape;
    }




}
