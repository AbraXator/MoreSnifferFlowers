package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.SaltemoneBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.AbstractMultiBlock;
import net.abraxator.moresnifferflowers.blocks.multiblock.CorruptableMultiblock;
import net.abraxator.moresnifferflowers.blocks.multiblock.MultiBlock;
import net.abraxator.moresnifferflowers.blocks.multiblock.PreviewableMultiblock;
import net.abraxator.moresnifferflowers.entities.SaltBubbleProjectile;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.networking.toClient.SaltemoneParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SaltemoneBlock extends AbstractMultiBlock implements ModEntityBlock, Corruptable, ModCropBlock, PreviewableMultiblock, CorruptableMultiblock {
    public SaltemoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState()
                .setValue(ModStateProperties.CENTER, false)
                .setValue(getAgeProperty(), 0)
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(ModStateProperties.SHEARED, false));
    }
    protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING, ModStateProperties.CENTER, getAgeProperty(), ModStateProperties.SHEARED);
    }

    @Override
    public boolean extraSurviveRequirements(LevelReader level, BlockPos pos, BlockState state) {
        return !level.isWaterAt(pos) && level.isWaterAt(pos.below());
    }

    @Override
    public @Nullable DirectionProperty getDirectionProperty() {
        return HorizontalDirectionalBlock.FACING;
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
    public Stream<BlockPos> fullBlockShape(Direction direction, BlockPos center) {
        BlockPos relative = center.relative(direction).relative(direction.getClockWise());
        return BlockPos.betweenClosedStream(new AABB(center.getCenter(), relative.getCenter()));
    }


    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_2;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return !isMaxAge(blockState);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        growHelper(level, pos, state);
    }

    public boolean isCorrupted(){
        return false;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (shear(player, level, pos, hand)){
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ModStateProperties.SHEARED)) return;
        if (level.getBlockEntity(pos) instanceof SaltemoneBlockEntity entity && pos.equals(entity.center)) {
            if (isMaxAge(state)) {
                Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
                Vec3 vec3 = entity.center.getCenter().relative(direction, 0.5D).relative(direction.getClockWise(), 0.5D).relative(Direction.UP, 0.0);
                float speed = 0.2F;

                SaltBubbleProjectile projectile = new SaltBubbleProjectile(vec3.x, vec3.y, vec3.z, level);

                projectile.setNoGravity(true);
                projectile.setCorrupted(isCorrupted());
                projectile.setState(0);
                projectile.setDeltaMovement((random.nextFloat() - 0.5)*speed,1*speed, (random.nextFloat() - 0.5)*speed);

                level.addFreshEntity(projectile);

                PacketDistributor.sendToAllPlayers( new SaltemoneParticlePacket(vec3.toVector3f()));

            } else {
                growHelper(level, pos, state);
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityinside) {
        corruptionHelper(state, level, pos, entityinside);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SaltemoneBlockEntity(blockPos, blockState);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    public Block getCuredBlock() {
        return ModBlocks.SALTEMONE.get();
    }

    @Override
    public Block getCorruptedBlock() {
        return ModBlocks.SOURLEMONE.get();
    }
}
