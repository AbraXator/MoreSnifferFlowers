package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.entities.SaltProjectile;
import net.abraxator.moresnifferflowers.init.ModEffects;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.atomic.AtomicBoolean;

public class SourPuddleBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(0, 0,  0, 16, 2, 16);

    public SourPuddleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PipeBlock.NORTH, Boolean.FALSE)
                .setValue(PipeBlock.EAST, Boolean.FALSE).setValue(PipeBlock.WEST, Boolean.FALSE)
                .setValue(PipeBlock.SOUTH, Boolean.FALSE).setValue(ModStateProperties.HYDRATED, Boolean.FALSE)
                .setValue(ModStateProperties.FULL, Boolean.FALSE).setValue(ModStateProperties.NATURAL, Boolean.TRUE));

    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return super.getStateForPlacement(context)
                .setValue(PipeBlock.NORTH, this.connectsTo(blockgetter, blockpos, Direction.NORTH))
                .setValue(PipeBlock.EAST, this.connectsTo(blockgetter, blockpos, Direction.EAST))
                .setValue(PipeBlock.SOUTH, this.connectsTo(blockgetter, blockpos, Direction.SOUTH))
                .setValue(PipeBlock.WEST, this.connectsTo(blockgetter, blockpos, Direction.WEST))
                .setValue(ModStateProperties.FULL, this.isFull(blockgetter, blockpos))
                .setValue(ModStateProperties.HYDRATED, false)
                .setValue(ModStateProperties.NATURAL, false);
    }

    public boolean connectsTo(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos pos1 = pos.offset(direction.getNormal());
        BlockState state = level.getBlockState(pos1);
        return state.is(this);
    }

    public boolean isFull(BlockGetter level, BlockPos pos){
        return Direction.Plane.HORIZONTAL.stream().allMatch(direction -> this.connectsTo(level, pos, direction));
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        boolean isThis = facingState.is(this);
        BlockState newState = switch (facing){
            case UP, DOWN -> state;
            case NORTH -> state.setValue(PipeBlock.NORTH, isThis);
            case SOUTH -> state.setValue(PipeBlock.SOUTH, isThis);
            case EAST -> state.setValue(PipeBlock.EAST, isThis);
            case WEST -> state.setValue(PipeBlock.WEST, isThis);
        };
        if (newState.getValue(PipeBlock.WEST) && newState.getValue(PipeBlock.EAST) && newState.getValue(PipeBlock.NORTH) && newState.getValue(PipeBlock.SOUTH))
            return super.updateShape(newState.setValue(ModStateProperties.FULL, true), facing, facingState, level, currentPos, facingPos);

        if (isFree(level.getBlockState(currentPos.below()))  && currentPos.getY() >= level.getMinBuildHeight()){

            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
            SaltProjectile projectile = new SaltProjectile((Level) level);
            projectile.setCorrupted(true);
            projectile.setPos(currentPos.below().getCenter());
            projectile.setXRot(Mth.PI / 90.0F);
            level.addFreshEntity(projectile);

            return Blocks.AIR.defaultBlockState();

        }
        if (!canSurvive(state, level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(newState.setValue(ModStateProperties.FULL, false), facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (!level.isClientSide && entity instanceof Player player){
            player.addEffect(new MobEffectInstance(ModEffects.SLIPPERY, 40, 5));
        }
    }

    public boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PipeBlock.NORTH, PipeBlock.EAST, PipeBlock.WEST, PipeBlock.SOUTH, ModStateProperties.HYDRATED, ModStateProperties.FULL, ModStateProperties.NATURAL);
    }
}
