package net.abraxator.moresnifferflowers.blocks.corrupted;

import net.abraxator.moresnifferflowers.entities.CorruptedProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
  
public class CorruptedSlimeLayerBlock extends SnowLayerBlock {
    public CorruptedSlimeLayerBlock(Properties p_56585_) {
        super(p_56585_);
    }
    
    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, Math.max((state.getValue(LAYERS) - 3) * 2, 0), 16);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState pOldState, boolean pIsMoving) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            spawnProjectile(state, level, pos);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
        //return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pos, BlockPos pFacingPos) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            spawnProjectile(state, level, pos);
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, pFacing, pFacingState, level, pos, pFacingPos);
    }

    private static void spawnProjectile(BlockState state, LevelAccessor level, BlockPos pos) {
        for (int i = 0; i < state.getValue(LAYERS); i++) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            CorruptedProjectile projectile = new CorruptedProjectile((Level) level);
            projectile.setPos(pos.below().getCenter());
            projectile.setXRot(Mth.PI / 90.0F);
            level.addFreshEntity(projectile);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity pEntity, float pFallDistance) {
        pEntity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        showParticles(pEntity, 10);

        if (pEntity.causeFallDamage(pFallDistance, 0.2F, level.damageSources().fall())) {
            pEntity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity pEntity) {
        double d0 = Math.abs(pEntity.getDeltaMovement().y);
        if (d0 < 0.1 && !pEntity.isSteppingCarefully()) {
            double d1 = (double) 1 / (state.getValue(LAYERS)+1) + d0 * 0.2;
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(d1, 1.0, d1));
        }
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int layers = state.getValue(LAYERS);
        if(layers == 1) {
            level.destroyBlock(pos, false);
        } else {
            level.setBlock(pos, state.setValue(LAYERS, layers - 1), 3);
        }
    }
    
    private void showParticles(Entity pEntity, int pParticleCount) {
        if (pEntity.level().isClientSide) {
            for (int i = 0; i < pParticleCount; i++) {
                pEntity.level()
                        .addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.defaultBlockState()), pEntity.getX(), pEntity.getY(), pEntity.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return (adjacentBlockState.is(this) && (adjacentBlockState.getValue(SnowLayerBlock.LAYERS)>=(state.getValue(SnowLayerBlock.LAYERS)) || side.getAxis().equals(Direction.Axis.Y))) || super.skipRendering(state, adjacentBlockState, side);
    }
}
