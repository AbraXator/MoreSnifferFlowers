package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.BondripiaBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.ICorruptableMultiblock;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.nikdo53.tinymultiblocklib.block.AbstractMultiBlock;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import net.nikdo53.tinymultiblocklib.block.IPreviewableMultiblock;
import net.nikdo53.tinymultiblocklib.block.logic.MultiblockLogic;
import net.nikdo53.tinymultiblocklib.block.shape.ShapeContext;
import net.nikdo53.tinymultiblocklib.blockentities.AbstractMultiBlockEntity;
import net.nikdo53.tinymultiblocklib.blockentities.IMultiBlockEntity;
import net.nikdo53.tinymultiblocklib.components.SharedStatePropertiesBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BondripiaBlock extends AbstractMultiBlock implements EntityBlock, ModCropBlock, Corruptable, IPreviewableMultiblock, ICorruptableMultiblock {
    public BondripiaBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(defaultBlockState()
                .setValue(ModStateProperties.CENTER, false)
                .setValue(getAgeProperty(), 0)
                .setValue(ModStateProperties.SHEARED, false));
    }
    private static final VoxelShape SHAPE = makeShape();

    @Override
    public RenderShape getMultiblockRenderShape(BlockState blockState, boolean isCenter) {
        if (!isCenter) return  RenderShape.INVISIBLE;
        if (getAge(blockState) == getMaxAge()) return RenderShape.ENTITYBLOCK_ANIMATED;
        return RenderShape.MODEL;
    }


    @Override
    public void createSharedBlockStates(SharedStatePropertiesBuilder builder) {
        super.createSharedBlockStates(builder);
        builder.add(ModStateProperties.SHEARED);
        builder.add(getAgeProperty());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getAgeProperty(), ModStateProperties.SHEARED);
    }

    @Override
    public BlockState getDefaultStateForPreviews(BlockState state, BlockPlaceContext blockPlaceContext) {
        return IPreviewableMultiblock.super.getDefaultStateForPreviews(state, blockPlaceContext).setValue(getAgeProperty(), getMaxAge());
    }

    @Override
    public List<BlockPos> makeFullBlockShape(Level level, BlockPos center, BlockState blockState, @Nullable BlockEntity blockEntity, @Nullable Direction direction) {
        List<BlockPos> positions = new ArrayList<>();
        positions.add(center.immutable());
        positions.addAll(Direction.Plane.HORIZONTAL.stream().map(direction1 -> center.relative(direction1).immutable()).toList());

        return positions;
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        spawnBonmeeliaParticles(state, level, pos, random);
    }

    private void spawnBonmeeliaParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean isAcidripia = state.is(ModBlocks.ACIDRIPIA.get());
        if(state.getValue(ModStateProperties.CENTER) && isMaxAge(state) && level.getBlockEntity(pos) instanceof IMultiBlockEntity entity && random.nextFloat() < 0.4) {
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
                if (level.getBlockState(pos2).isSolid() && level.getBlockState(pos2.below()).isAir()){
                    ParticleUtils.spawnParticleBelow(level, pos2, random, dripParticles);
                }
            });
        }
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ModStateProperties.SHEARED)) return;
        if(!isMaxAge(state) && IMultiBlock.isCenter(state)) {
            grow(level, pos, state);
        } else if (random.nextDouble() <= 0.33D && level.getBlockEntity(pos) instanceof BondripiaBlockEntity entity) {
            for (BlockPos blockPos : BlockPos.betweenClosed(entity.getCenter().below().north().east(), entity.getCenter().below().south().west())) {
                BlockPos currentPos = blockPos;
                BlockState blockState = level.getBlockState(currentPos);

                int y = level.getRandom().nextIntBetweenInclusive(1, 11);
                currentPos = currentPos.below(y);

                if (level.getBlockState(currentPos).getBlock() instanceof AbstractCauldronBlock block) {
                    fillCauldron(level, currentPos, blockState);
                    break;

                }

                if (blockState.is(ModTags.ModBlockTags.BONMEELABLE)) {

                    Bonmeelable bonmeelable = (Bonmeelable) GiantCropBlock.getCropMap().get(blockState.getBlock()).getA();
                    if (bonmeelable.canBonmeel(currentPos, blockState, level, null)) {
                        bonmeelable.performBonmeel(currentPos, blockState, level, null);
                        break;
                    }

                }

               if (Items.BONE_MEAL instanceof BoneMealItem boneMealItem ) {
                   FakePlayer fakePlayer = FakePlayerFactory.getMinecraft(level);
                   boneMealItem.useOn(new UseOnContext(level, fakePlayer, InteractionHand.MAIN_HAND, Items.BONE_MEAL.getDefaultInstance(),
                           new BlockHitResult(currentPos.getCenter(), Direction.UP, currentPos, false)));
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (shear(player, level, pos, hand)){
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityinside) {
        corruptionHelper(state, level, pos, entityinside);
    }

    public void grow(Level level, BlockPos blockPos, BlockState state) {
       if (IMultiBlock.isCenter(state))
           makeGrowOnBonemeal(level, blockPos, state);
    }
    
    private boolean isBondripable(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).getBlock() instanceof BonemealableBlock || level.getBlockState(blockPos).is(ModTags.ModBlockTags.BONMEELABLE);
    }

    @Override
    public boolean extraSurviveRequirements(LevelReader level, BlockPos pos, BlockState state, BlockPos centerOffset) {
        return Block.canSupportCenter(level, pos.above(), Direction.DOWN) && !level.isWaterAt(pos);
    }

    @Override
    public AbstractMultiBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BondripiaBlockEntity(pos, state);
    }

    @Override
    public boolean hasCustomBE() {
        return true;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_2;
    }
    
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean pIsClient) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        makeGrowOnBonemeal(level, pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return voxelShapeHelper(state, getter, pos, SHAPE);

    }

    @Override
    public Block getCuredBlock() {
        return ModBlocks.BONDRIPIA.get();
    }

    @Override
    public Block getCorruptedBlock() {
        return ModBlocks.ACIDRIPIA.get();
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.6875, 0.875, 0, 1.6875, 1.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.875, -0.6875, 1, 1.0625, 1.6875), BooleanOp.OR);

        return shape;
    }

}
