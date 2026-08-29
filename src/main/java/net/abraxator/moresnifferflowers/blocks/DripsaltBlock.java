package net.abraxator.moresnifferflowers.blocks;

import com.google.common.annotations.VisibleForTesting;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class DripsaltBlock extends PointedDripstoneBlock {
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
    private static final int DELAY_BEFORE_FALLING = 2;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
    private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
    private static final float WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
    private static final float LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.05859375F;
    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6D;
    private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
    private static final int STALACTITE_MAX_DAMAGE = 40;
    private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
    private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
    private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
    private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
    private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
    private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public DripsaltBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(MSFItems.SALTY_SPICE.get()) && stack.getCount() >= 5){
            for (int i = 0; i < 5; i++){
                if (level.getBlockState(pos.above(i + 1)).isAir() && level.getBlockState(pos.above(i)).is(this)) {
                    level.setBlock(pos.above(i + 1), this.defaultBlockState(), 3);
                    if (!player.isCreative()) stack.shrink(5);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidPointedDripstonePlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    public BlockState updateShape(BlockState state, Direction p_direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (p_direction != Direction.UP && p_direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction = state.getValue(TIP_DIRECTION);
            if (direction == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (p_direction == direction.getOpposite() && !this.canSurvive(state, level, pos)) {
                if (direction == Direction.DOWN) {
                    level.scheduleTick(pos, this, 2);
                } else {
                    level.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean flag = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness dripstonethickness = calculateDripstoneThickness(level, pos, direction, flag);
                return state.setValue(THICKNESS, dripstonethickness);
            }
        }
    }

    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos blockpos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockpos) && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6D) {
            level.destroyBlock(blockpos, true);
        }

    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }

    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluidAboveStalactite(level, pos, state).filter((p_221848_) -> {
                    return f < 0.02F || canFillCauldron(p_221848_.fluid);
                }).ifPresent((p_221881_) -> {
                    spawnDripParticle(level, pos, state, p_221881_.fluid);
                });
            }
        }
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            spawnFallingStalactite(state, level, pos);
        }

    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        maybeTransferFluid(state, level, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isStalactiteStartPos(state, level, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
        }

    }

    @VisibleForTesting
    public static void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randChance) {
        if (!(randChance > 0.17578125F) || !(randChance > 0.05859375F)) {
            if (isStalactiteStartPos(state, level, pos)) {
                Optional<DripsaltBlock.FluidInfo> optional = getFluidAboveStalactite(level, pos, state);
                if (!optional.isEmpty()) {
                    Fluid fluid = (optional.get()).fluid;
                    float f;
                    if (fluid == Fluids.WATER) {
                        f = 0.17578125F;
                    } else {
                        if (fluid != Fluids.LAVA) {
                            return;
                        }

                        f = 0.05859375F;
                    }

                    if (!(randChance >= f)) {
                        BlockPos blockpos = findTip(state, level, pos, 11, false);
                        if (blockpos != null) {
                            if ((optional.get()).sourceState.is(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState blockstate1 = Blocks.CLAY.defaultBlockState();
                                level.setBlockAndUpdate((optional.get()).pos, blockstate1);
                                Block.pushEntitiesUp((optional.get()).sourceState, blockstate1, level, (optional.get()).pos);
                                level.gameEvent(GameEvent.BLOCK_CHANGE, (optional.get()).pos, GameEvent.Context.of(blockstate1));
                                level.levelEvent(1504, blockpos, 0);
                            } else {
                                BlockPos blockpos1 =  findFillableCauldronBelowStalactiteTip(level, blockpos, fluid);
                                if (blockpos1 != null) {
                                    level.levelEvent(1504, blockpos, 0);
                                    int i = blockpos.getY() - blockpos1.getY();
                                    int j = 50 + i;
                                    BlockState blockstate = level.getBlockState(blockpos1);
                                    level.scheduleTick(blockpos1, blockstate.getBlock(), j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getNearestLookingVerticalDirection().getOpposite();
        Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
        if (direction1 == null) {
            return null;
        } else {
            boolean flag = !context.isSecondaryUseActive();
            DripstoneThickness dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
            return dripstonethickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction1).setValue(THICKNESS, dripstonethickness).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        DripstoneThickness dripstonethickness = state.getValue(THICKNESS);
        VoxelShape voxelshape;
        if (dripstonethickness == DripstoneThickness.TIP_MERGE) {
            voxelshape = TIP_MERGE_SHAPE;
        } else if (dripstonethickness == DripstoneThickness.TIP) {
            if (state.getValue(TIP_DIRECTION) == Direction.DOWN) {
                voxelshape = TIP_SHAPE_DOWN;
            } else {
                voxelshape = TIP_SHAPE_UP;
            }
        } else if (dripstonethickness == DripstoneThickness.FRUSTUM) {
            voxelshape = FRUSTUM_SHAPE;
        } else if (dripstonethickness == DripstoneThickness.MIDDLE) {
            voxelshape = MIDDLE_SHAPE;
        } else {
            voxelshape = BASE_SHAPE;
        }

        Vec3 vec3 = state.getOffset(level, pos);
        return voxelshape.move(vec3.x, 0.0D, vec3.z);
    }

    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    public float getMaxHorizontalOffset() {
        return 0.125F;
    }

    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        if (!fallingBlock.isSilent()) {
            level.levelEvent(1045, pos, 0);
        }

    }

    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    private static void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(BlockState blockstate = state; isStalactite(blockstate); blockstate = level.getBlockState(blockpos$mutableblockpos)) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, blockpos$mutableblockpos, blockstate);
            if (isTip(blockstate, true)) {
                int i = Math.max(1 + pos.getY() - blockpos$mutableblockpos.getY(), 6);
                float f = 1.0F * (float)i;
                fallingblockentity.setHurtsEntities(f, 40);
                break;
            }

            blockpos$mutableblockpos.move(Direction.DOWN);
        }

    }

    @VisibleForTesting
    public static void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState blockstate = level.getBlockState(pos.above(1));
        BlockState blockstate1 = level.getBlockState(pos.above(2));
        if (canGrow(blockstate, blockstate1)) {
            BlockPos blockpos = findTip(state, level, pos, 7, false);
            if (blockpos != null) {
                BlockState blockstate2 = level.getBlockState(blockpos);
                if (canDrip(blockstate2) && canTipGrow(blockstate2, level, blockpos)) {
                    if (random.nextBoolean()) {
                        grow(level, blockpos, Direction.DOWN);
                    } else {
                        growStalagmiteBelow(level, blockpos);
                    }

                }
            }
        }
    }

    private static void growStalagmiteBelow(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(int i = 0; i < 10; ++i) {
            blockpos$mutableblockpos.move(Direction.DOWN);
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (!blockstate.getFluidState().isEmpty()) {
                return;
            }

            if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, level, blockpos$mutableblockpos)) {
                grow(level, blockpos$mutableblockpos, Direction.UP);
                return;
            }

            if (isValidPointedDripstonePlacement(level, blockpos$mutableblockpos, Direction.UP) && !level.isWaterAt(blockpos$mutableblockpos.below())) {
                grow(level, blockpos$mutableblockpos.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(level, blockpos$mutableblockpos, blockstate)) {
                return;
            }
        }

    }

    private static void grow(ServerLevel server, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        BlockState blockstate = server.getBlockState(blockpos);
        if (isUnmergedTipWithDirection(blockstate, direction.getOpposite())) {
            createMergedTips(blockstate, server, blockpos);
        } else if (blockstate.isAir() || blockstate.is(Blocks.WATER)) {
            createDripstone(server, blockpos, direction, DripstoneThickness.TIP);
        }

    }

    private static void createDripstone(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState blockstate = MSFBlocks.DRIPSALT.get().defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
        level.setBlock(pos, blockstate, 3);
    }

    private static void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
        BlockPos blockpos;
        BlockPos blockpos1;
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            blockpos1 = pos;
            blockpos = pos.above();
        } else {
            blockpos = pos;
            blockpos1 = pos.below();
        }

        createDripstone(level, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createDripstone(level, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void spawnDripParticle(Level level, BlockPos pos, BlockState state) {
        getFluidAboveStalactite(level, pos, state).ifPresent((p_221856_) -> {
            spawnDripParticle(level, pos, state, p_221856_.fluid);
        });
    }

    private static void spawnDripParticle(Level level, BlockPos pos, BlockState state, Fluid p_fluid) {
        Vec3 vec3 = state.getOffset(level, pos);
        double d0 = 0.0625D;
        double d1 = (double)pos.getX() + 0.5D + vec3.x;
        double d2 = (double)((float)(pos.getY() + 1) - 0.6875F) - 0.0625D;
        double d3 = (double)pos.getZ() + 0.5D + vec3.z;
        Fluid fluid = getDripFluid(level, p_fluid);
        ParticleOptions particleoptions = fluid.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        level.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxIterations, boolean isTipMerge) {
        if (isTip(state, isTipMerge)) {
            return pos;
        } else {
            Direction direction = state.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> {
                return p_202024_.is(MSFBlocks.DRIPSALT.get()) && p_202024_.getValue(TIP_DIRECTION) == direction;
            };
            return findBlockVertical(level, pos, direction.getAxisDirection(), bipredicate, (p_154168_) -> {
                return isTip(p_154168_, isTipMerge);
            }, maxIterations).orElse((BlockPos)null);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction dir) {
        Direction direction;
        if (isValidPointedDripstonePlacement(level, pos, dir)) {
            direction = dir;
        } else {
            if (!isValidPointedDripstonePlacement(level, pos, dir.getOpposite())) {
                return null;
            }

            direction = dir.getOpposite();
        }

        return direction;
    }

    private static DripstoneThickness calculateDripstoneThickness(LevelReader level, BlockPos pos, Direction dir, boolean isTipMerge) {
        Direction direction = dir.getOpposite();
        BlockState blockstate = level.getBlockState(pos.relative(dir));
        if (isPointedDripstoneWithDirection(blockstate, direction)) {
            return !isTipMerge && blockstate.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isPointedDripstoneWithDirection(blockstate, dir)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness dripstonethickness = blockstate.getValue(THICKNESS);
            if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockstate1 = level.getBlockState(pos.relative(direction));
                return !isPointedDripstoneWithDirection(blockstate1, dir) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
    }

    private static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BlockPos blockpos = pos.relative(direction);
        BlockState blockstate = level.getBlockState(blockpos);
        if (!blockstate.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockstate.isAir() ? true : isUnmergedTipWithDirection(blockstate, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> {
            return p_202016_.is(MSFBlocks.DRIPSALT.get()) && p_202016_.getValue(TIP_DIRECTION) == direction;
        };
        return findBlockVertical(level, pos, direction.getOpposite().getAxisDirection(), bipredicate, (p_154245_) -> {
            return !p_154245_.is(MSFBlocks.DRIPSALT.get());
        }, maxIterations);
    }

    private static boolean isValidPointedDripstonePlacement(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos blockpos = pos.relative(dir.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, dir) || isPointedDripstoneWithDirection(blockstate, dir);
    }

    private static boolean isTip(BlockState state, boolean isTipMerge) {
        if (!state.is(MSFBlocks.DRIPSALT.get())) {
            return false;
        } else {
            DripstoneThickness dripstonethickness = state.getValue(THICKNESS);
            return dripstonethickness == DripstoneThickness.TIP || isTipMerge && dripstonethickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState state, Direction dir) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == dir;
    }

    private static boolean isStalactite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.UP);
    }

    private static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(MSFBlocks.DRIPSALT.get());
    }

    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    private static boolean isPointedDripstoneWithDirection(BlockState state, Direction dir) {
        return state.is(MSFBlocks.DRIPSALT.get()) && state.getValue(TIP_DIRECTION) == dir;
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos pos, Fluid fluid) {
        Predicate<BlockState> predicate = (p_154162_) -> {
            return p_154162_.getBlock() instanceof AbstractCauldronBlock;
        };
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202034_, p_202035_) -> {
            return canDripThrough(level, p_202034_, p_202035_);
        };
        return findBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse((BlockPos)null);
    }

    @Nullable
    public static BlockPos findStalactiteTipAboveCauldron(Level level, BlockPos pos) {
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202030_, p_202031_) -> {
            return canDripThrough(level, p_202030_, p_202031_);
        };
        return findBlockVertical(level, pos, Direction.UP.getAxisDirection(), bipredicate, PointedDripstoneBlock::canDrip, 11).orElse((BlockPos)null);
    }

    public static Fluid getCauldronFillFluidType(ServerLevel level, BlockPos pos) {
        return getFluidAboveStalactite(level, pos, level.getBlockState(pos)).map((p_221858_) -> {
            return p_221858_.fluid;
        }).filter(DripsaltBlock::canFillCauldron).orElse(Fluids.EMPTY);
    }

    private static Optional<DripsaltBlock.FluidInfo> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map((p_221876_) -> {
            BlockPos blockpos = p_221876_.above();
            BlockState blockstate = level.getBlockState(blockpos);
            Fluid fluid;
            if (blockstate.is(Blocks.MUD) && !level.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = level.getFluidState(blockpos).getType();
            }

            return new DripsaltBlock.FluidInfo(blockpos, fluid, blockstate);
        });
    }

    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState dripstoneState, BlockState state) {
        return dripstoneState.is(Blocks.DRIPSTONE_BLOCK) && state.is(Blocks.WATER) && state.getFluidState().isSource();
    }

    private static Fluid getDripFluid(Level level, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return level.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axis, BiPredicate<BlockPos, BlockState> positionalStatePredicate, Predicate<BlockState> statePredicate, int maxIterations) {
        Direction direction = Direction.get(axis, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(int i = 1; i < maxIterations; ++i) {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (statePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (level.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !positionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isSolidRender(level, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = state.getCollisionShape(level, pos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    static record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
