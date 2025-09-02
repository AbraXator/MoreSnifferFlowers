package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.BondripiaBlockEntity;
import net.abraxator.moresnifferflowers.blockentities.MultiBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.AbstractMultiBlock;
import net.abraxator.moresnifferflowers.blocks.multiblock.CorruptableMultiblock;
import net.abraxator.moresnifferflowers.blocks.multiblock.PreviewableMultiblock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BondripiaBlock extends AbstractMultiBlock implements ModEntityBlock, ModCropBlock, Corruptable, PreviewableMultiblock, CorruptableMultiblock {
    public BondripiaBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(defaultBlockState()
                .setValue(ModStateProperties.CENTER, false)
                .setValue(getAgeProperty(), 0)
                .setValue(ModStateProperties.SHEARED, false));
    }
    private static final VoxelShape SHAPE = Block.box(2.0, 13.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape SHAPE_CENTER = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

    @Override
    public Block getCuredBlock() {
        return ModBlocks.BONDRIPIA.get();
    }

    @Override
    public Block getCorruptedBlock() {
        return ModBlocks.ACIDRIPIA.get();
    }

    @Override
    public BlockState getDefaultStateForPreviews(Direction direction) {
        return PreviewableMultiblock.super.getDefaultStateForPreviews(direction).setValue(getAgeProperty(), getMaxAge());
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if (!isCenter(state)) return  RenderShape.INVISIBLE;
        if (getAge(state) == getMaxAge()) return RenderShape.ENTITYBLOCK_ANIMATED;
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ModStateProperties.CENTER, getAgeProperty(), ModStateProperties.SHEARED);
    }

    @Override
    public Stream<BlockPos> fullBlockShape(@Nullable Direction direction, BlockPos center) {
        List<BlockPos> positions = new ArrayList<>();
        positions.add(center.immutable());
        positions.addAll(Direction.Plane.HORIZONTAL.stream().map(direction1 -> center.relative(direction1).immutable()).toList());

        return positions.stream();
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(level);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        spawnBonmeeliaParticles(state, level, pos, random);
    }

    private void spawnBonmeeliaParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean isAcidripia = state.is(ModBlocks.ACIDRIPIA.get());
        if(state.getValue(ModStateProperties.CENTER) && isMaxAge(state) && level.getBlockEntity(pos) instanceof MultiBlockEntity entity && random.nextFloat() < 0.4) {
            BlockPos.withinManhattanStream(entity.getCenter(), 1, 0, 1).forEach(blockPos -> {

                var vec3 = blockPos.getCenter();
                var difference = blockPos.subtract(entity.getCenter());
                boolean isCorner = (Math.abs(difference.getX()) + Math.abs(difference.getZ())) == 2;
                boolean isMid = blockPos.equals(entity.getCenter());
                BlockPos pos2 = blockPos.below(random.nextInt(8));

                SimpleParticleType dripParticles = isAcidripia ? ModParticles.ACIDRIPIA_DRIP.get() : ModParticles.BONDRIPIA_DRIP.get();
                SimpleParticleType fallParticles = isAcidripia ? ModParticles.ACIDRIPIA_FALL.get() :ModParticles.BONDRIPIA_FALL.get();

                if (random.nextFloat() < 0.5) {
                    if (isMid) {
                        level.addParticle(dripParticles, vec3.x + random.nextIntBetweenInclusive(-1, 1) * 0.15, vec3.y - 0.25, vec3.z + random.nextIntBetweenInclusive(-1, 1) * 0.15, 0, 1, 0);

                    } else if (isCorner) {
                        level.addParticle(fallParticles, vec3.x - difference.getX() * 0.05, vec3.y, vec3.z - difference.getZ() * 0.05, 0, 1, 0);

                    } else {
                        level.addParticle(dripParticles, vec3.x - difference.getX() * 0.1, vec3.y, vec3.z - difference.getZ() * 0.1, 0, 1, 0);
                    }
                }
                if (!level.getBlockState(pos2).canBeReplaced() && level.getBlockState(pos2.below()).isAir()){
                    ParticleUtils.spawnParticleBelow(level, pos2, random, dripParticles);
                }
            });
        }
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ModStateProperties.SHEARED)) return;
        if(!isMaxAge(state)) {
            grow(level, pos, state);
        } else if (random.nextDouble() <= 0.33D && level.getBlockEntity(pos) instanceof BondripiaBlockEntity entity) {
            for (BlockPos blockPos : BlockPos.betweenClosed(entity.center.below().north().east(), entity.center.below().south().west())) {
                BlockPos currentPos = blockPos;

                    int y = level.getRandom().nextIntBetweenInclusive(1, 11);
                    currentPos = currentPos.below(y);

                    if (isBondripable(level, currentPos)) {
                        BlockState blockState = level.getBlockState(currentPos);

                        if (blockState.getBlock() instanceof BonemealableBlock bonemealable && bonemealable.isValidBonemealTarget(level, currentPos, blockState)) {
                            bonemealable.performBonemeal(level, random, currentPos, blockState);
                            break;
                        }
                        
                        if (blockState.is(ModTags.ModBlockTags.BONMEELABLE)) {
                            Bonmeelable bonmeelable = (Bonmeelable) GiantCropBlock.getCropMap().get(blockState.getBlock()).getA();
                            if (bonmeelable.canBonmeel(currentPos, blockState, level)) {
                                bonmeelable.performBonmeel(currentPos, blockState, level, null);
                                break;
                            }
                        }


                    } else if (level.getBlockState(currentPos).getBlock() instanceof AbstractCauldronBlock block) {
                        fillCauldron(level, currentPos, level.getBlockState(currentPos));
                    }


            }
        }
    }
    
    public void fillCauldron(Level level, BlockPos blockPos, BlockState blockState) {
        BlockState blockstate = blockState.is(ModBlocks.ACIDRIPIA.get()) ? ModBlocks.ACID_FILLED_CAULDRON.get().defaultBlockState() : ModBlocks.BONMEEL_FILLED_CAULDRON.get().defaultBlockState();
        int fluidLevel = level.getBlockState(blockPos).getOptionalValue(LayeredCauldronBlock.LEVEL).orElse(0);
        if(fluidLevel < 3) {
            level.setBlockAndUpdate(blockPos, blockstate.setValue(LayeredCauldronBlock.LEVEL, fluidLevel + 1));
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockstate));
            level.levelEvent(1047, blockPos, 0);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (shear(player, level, pos, hand)){
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityinside) {
        corruptionHelper(state, level, pos, entityinside);
    }

    public void grow(Level level, BlockPos blockPos, BlockState state) {
        growHelper(level, blockPos, state);
    }
    
    private boolean isBondripable(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).getBlock() instanceof BonemealableBlock || level.getBlockState(blockPos).is(ModTags.ModBlockTags.BONMEELABLE);
    }

    @Override
    public boolean extraSurviveRequirements(LevelReader level, BlockPos pos, BlockState state) {
        return Block.canSupportCenter(level, pos.above(), Direction.DOWN) && !level.isWaterAt(pos);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BondripiaBlockEntity(pos, state);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_2;
    }
    
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        grow(level, pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if(getter.getBlockEntity(pos) instanceof MultiBlockEntity entity) {
            if(state.getValue(ModStateProperties.CENTER)) return SHAPE_CENTER;
            BlockPos center = entity.getCenter();
            var offset = pos.subtract(center);
            return Block.box(Math.min(2.0 - offset.getX()*2, 2), 13.0, Math.min(2.0 - offset.getZ()*2, 2), Math.max(14.0 - offset.getX()*2, 14), 16.0, Math.max(14.0 - offset.getZ()*2, 14));
        }

        return SHAPE;

    }
}
